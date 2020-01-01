# Flight Delay Prediction Model

### Getting Started
* Install [InsightEdge](https://www.gigaspaces.com/downloads/)
* Install and run [Kafka](https://kafka.apache.org/quickstart)
* Copy the demo notebooks on the Zeppelin notebooks folder under the InsightEdge installation
```sh
$ cp -R ./zeppelin_notebooks/* <InsightEdge Install Dir>/insightedge/zeppelin/notebook/
```
* Set the kafka server url for the demo:
```sh
$ export KAFKA_URL=192.168.99.102:29092
```
* Run the InsightEdge platform with 5 GSCs:
```sh
$ ./gs.sh host run-agent --auto --gsc=5
```
* Start a in-memory 'space' with 4 partitions named 'flights_space'
```sh
$ ./gs.sh space deploy --partitions=4 flights_space
```
* Download & unzip the two data files
 
```sh
$ wget https://insightedge-gettingstarted.s3.amazonaws.com/flightdelays20172018.csv.zip
$ unzip flightdelays20172018.csv.zip
$ wget https://insightedge-gettingstarted.s3.amazonaws.com/weather2017_8.csv.zip
$ unzip weather2017_8.csv.zip
```
* Access Zeppelin Notebook (Zeppelin comes within the InsightEdge Platform) from a web browser:
(http://<InsightEdge IP>:9092/#/notebook/INSIGHTEDGE-GETTING-STARTED)
* Update the notebook to reference the local 'flightdelays20172018.csv' file that we just extracted.
* Go to the Interpreter setting (from the top right drop down menu) and change the insightedge_jdbc default.url from 'demo' to 'flights_space', save changes and return to the notebook,
* Run the first notebook - See the data populated and wait until all the paragraphs run was completed
* Move to the second notebook (link is found at the end of the first notebook)
* Update the notebook to reference the local 'weather2017_8.csv.zip' file that we just extracted.
##### Start streaming flights data
* Deploy the Feeder unit which will feed 2019 flights data via Kafka. This requires the configuration of a few properties:
    * kafka.bootstrapServer: the IP of the zookeeper used by Kafka
    * feeder.flights.path: full path that will be used to save the data file we use. (File will fetched from the web if it is not available). Folder must pre-exist.

```sh
$ <InsightEdge_Dir>/bin/gs.sh pu deploy --property=kafka.bootstrapServer=127.0.0.1 --property=feeder.flights.path=/tmp/data.csv feeder ./kafkaFeederPU/target/kafka-pers-feeder.jar
```

* Once the feeder is running, run the second notebook


