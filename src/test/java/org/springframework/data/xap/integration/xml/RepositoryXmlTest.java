package org.springframework.data.xap.integration.xml;

import org.junit.runner.RunWith;
import org.springframework.data.xap.integration.BaseRepositoryTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * Test for repository XML configuration.
 *
 * Repository is declared in RepositoryXmlTest-context.xml
 *
 * <xap-data:repositories /> element allows you to simply define a base package that Spring scans for you.
 *
 * @author Anna_Babich
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class RepositoryXmlTest extends BaseRepositoryTest {

}
