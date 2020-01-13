package com.gigaspaces.sbp

import com.gigaspaces.GsI10nSuite
import scala.util.Random
import org.scalatest.ConfigMap
import com.j_spaces.core.client.SQLQuery
import com.gigaspaces.sbp.clientonly.BrokenWatchOwner
import org.springframework.context.support.ClassPathXmlApplicationContext
import org.openspaces.core.GigaSpace
import org.slf4j.{Logger, LoggerFactory}
import org.specs2.matcher.ShouldMatchers
import java.util

/** Created by IntelliJ IDEA.
  * User: jason
  * Date: 4/27/14
  * Time: 5:30 AM
  * NOTE: In order for this test to run with two partitions, an appropriate gslicense.xml file
  * should be installed at src/test/resources/gslicense.xml
  */
class WatchRepairSuite extends GsI10nSuite with ShouldMatchers{

  val logger:Logger = LoggerFactory.getLogger(getClass)

  // SETUP STUFF

  val rand = new Random(System.currentTimeMillis())
  val maxNumPartsPerWatch = 3
  val numPartitions = 2
  val numTestWatches = 4
  val spaceName = classOf[WatchRepairSuite].getSimpleName

  defaults = Map[String, Any](
    schemaProperty -> "partitioned-sync2backup"
    , numInstancesProperty -> int2Integer(numPartitions)
    , numBackupsProperty -> int2Integer(0)
    , instanceIdProperty -> int2Integer(1)
    , spaceUrlProperty -> s"jini:/*/*/$spaceName?locators=localhost:4174&groups=watches"
    , spaceModeProperty -> SpaceMode.Remote
    , configLocationProperty -> "classpath*:/META-INF/Spring/pu.xml"
    , localViewQueryListProperty -> List[SQLQuery[_]]()
  )

  val defaultConfigMap = new ConfigMap(defaults)
  val clientXmlContextResourceLocation = "classpath*:/com/gigaspaces/sbp/WatchRepairClient.xml"
  val remoteProxyBeanName = "brokenWatchOwner"
  val clusteredProxyBeanName = "gigaSpace"

  // fields initialized for test
  var brokenWatchOwner: BrokenWatchOwner = null
  var testWatches: Seq[Watch] = null
  var clusteredProxy: GigaSpace = null

  // initialization methods

  override def beforeAll(cm: ConfigMap): Unit = {
    setupWith(defaultConfigMap)
    val ctxt = loadContext(clientXmlContextResourceLocation)
    brokenWatchOwner = ctxt.getBean(remoteProxyBeanName).asInstanceOf[BrokenWatchOwner]
    clusteredProxy = ctxt.getBean(clusteredProxyBeanName).asInstanceOf[GigaSpace]
  }

  override def beforeEach(): Unit = {
    testWatches = writeTestWatches()
  }

  def loadContext(descriptor: String) : ClassPathXmlApplicationContext = {
    new ClassPathXmlApplicationContext(descriptor)
  }

  def writeTestWatches(): List[Watch] = {
    val testWatches = {
      var list = List[Watch]()
      for (i <- 1 to numTestWatches + 1) list = list :+ makeTestWatch(i)
      list
    }
    testWatches.foreach {
      w => w.setSpaceId(clusteredProxy.write(w).getUID)
    }
    testWatches.foreach{
      w => logger.trace(s"Wrote $w .")
    }
    testWatches
  }

  // TESTS

  test("switchGears changes gears") {

    val b4Update = readFromGigaSpace
    val oldGears = b4Update.getGears
    val newGears = makeTestGears()
    assume(oldGears != newGears)

    brokenWatchOwner.sendRequestWithParts(b4Update, newGears)

    val afterGears = readFromGigaSpace.getGears

    gearsMatch(afterGears, newGears)
  }

  test("switchGears changes gears (with projection)") {

    val b4Update = readFromGigaSpaceWithProjection
    val oldGears = b4Update.getGears
    val newGears = makeTestGears()
    assume(oldGears != newGears)

    brokenWatchOwner.sendRequestWithParts(b4Update, newGears)

    val afterGears = readFromGigaSpace.getGears
    gearsMatch(afterGears, newGears)

  }

  def gearsMatch(afterGears: util.List[Gear], newGears: util.List[Gear]): Unit = {
    val ag = List(afterGears)
    val ng = List(newGears)
    ag.foreach{ gear =>
      ng should contain (gear)
    }
  }

  def readFromGigaSpaceWithProjection: Watch = {

    val query = new SQLQuery[Watch](classOf[Watch], "spaceId = ?", aTestWatch.getSpaceId)
      .setProjections("spaceId", "partitionId", "name", "weight")
    val watch = clusteredProxy.read[Watch](query)

    assume(watch != null, "Test watch not returned from GigaSpace")

    watch

  }

  def aTestWatch: Watch = {
    val watch = testWatches.filter(w => w.getName == "Watch 2").head
    assume(watch != null, "Test watch was not written as expected.")
    watch
  }

  def readFromGigaSpace: Watch = {

    val watch = clusteredProxy.readById(classOf[Watch], aTestWatch.getSpaceId)
    assume(watch != null, "Test watch not returned from GigaSpace")
    watch

  }

  // TEST DATA GEN STUFF

  def randUpper = math.abs(rand.nextInt(maxNumPartsPerWatch)) + 1

  def setMass(part: WatchPart): WatchPart = {
    part.setWeight(Math.abs(rand.nextFloat()))
    part
  }

  def makeTestGears(): java.util.List[Gear] = {
    def makeTestGear(): Gear = {
      val gear = setMass(new Gear).asInstanceOf[Gear]
      gear.setNumber(rand.nextInt(10))
      gear
    }
    val list = new java.util.ArrayList[Gear]()
    for (i <- 0 to randUpper) list.add(makeTestGear())
    list
  }

  def makeTestWatch(num: Int): Watch = {
    def makeTestSprings(): java.util.List[Spring] = {
      def makeTestSpring(): Spring = {
        setMass(new Spring).asInstanceOf[Spring]
      }
      val list = new java.util.ArrayList[Spring]()
      for (i <- 0 to randUpper) list.add(makeTestSpring())
      list
    }
    val w = new Watch
    w.setName(s"Watch $num")
    w.setPartitionId(num % numPartitions)
    w.setGears(makeTestGears())
    w.setSprings(makeTestSprings())
    w
  }

}