# Flight Delay Prediction Model

The flight delay prediction model can be used either as a cloud-based application or as a local installation on your machine. If you opt to install it locally, follow the instructions below to install the necessary software and configure the environment so you can run the model.

## Prerequisites

### Hardware

Your local machine should have a minimum of 1 GB free space to install the necessary software to run the Flight Delay prediction model, and 5 GB of RAM to run it.

### Software

* InsightEdge release 15.0 or higher
* Kafka server 0.82 or higher 
* [Flight Delay AWS package 1 - flightdelays20172018](https://insightedge-gettingstarted.s3.amazonaws.com/flightdelays20172018.csv.zip)
* [Flight Delay AWS package 2 - weather2017_8](https://insightedge-gettingstarted.s3.amazonaws.com/weather2017_8.csv.zip)
 
## Setting Up the Environment

Installing and configuring the prediction model involves the following steps:

1. Installing the software.
1. Starting and configuring InsightEdge and the data grid.
1. Setting up Apache Zeppelin.
1. Configuring Kafka as the producer.
1. Running the model.

### Installing the Required Software

1. Download and install [InsightEdge](https://www.gigaspaces.com/downloads/).
1. Download, install, and run Kafka as described in the [Kafka documentation](https://kafka.apache.org/quickstart).

#### Configuring Apache Zeppelin

InsightEdge provides Apache Zeppelin as part of its standard software package. To use the flight delay prediction model, you need the  INSIGHTEDGE-GETTING-STARTED web notebook, along with the INSIGHTEDGE-GETTING-STARTED-2 notebook. Configure the following:

1. Copy the demo notebooks from the Zeppelin notebook folder in the InsightEdge installation directory:
   ```sh
   $ cp -R ./zeppelin_notebooks/* <InsightEdge Install Dir>/insightedge/zeppelin/notebook/
   ```

1. Set the kafka server URL for the prediction model:
   ```sh
   $ export KAFKA_URL=192.168.99.102:29092
   ```

### Preparing the Data Files

In order to use the flight delay prediction model, you need to feed data to InsightEdge and then query it to create the predictions. This data is contained in the following files that need to be downloaded and extracted.

1. Download the following two  files from AWS: 
   ```sh
   $ wget https://insightedge-gettingstarted.s3.amazonaws.com/flightdelays20172018.csv.zip
   $ wget https://insightedge-gettingstarted.s3.amazonaws.com/weather2017_8.csv.zip
   ```

1. Unzip the files, and export the file path:
   ```sh
   $ unzip flightdelays20172018.csv.zip
   $ export flight_delay_path=<data file path>
   
   $ unzip weather2017_8.csv.zip
   $ export weather_info=<data file path>
   ```

### Setting Up InsightEdge

You need to run InsightEdge with the following configuration to support the prediction model.

1. Start InsightEdge  with 5 GSCs:
   ```sh
   $ ./gs.sh host run-agent --auto --gsc=5
   ```

1. Start an in-memory space called 'flights_space' with 4 partitions:
   ```sh
   $ ./gs.sh space deploy --partitions=4 flights_space
   ```

### Setting up the Apache Zeppelin Notebook

After the InsightEdge platform has been started, you need to set up the INSIGHTEDGE-GETTING-STARTED web notebook to point to the prediction model data.

1. Launch the Apache Zeppelin notebook in your web browser using the following URL: `http://<InsightEdge IP>:9090/#/notebook/INSIGHTEDGE-GETTING-STARTED`
1. From the top right dropdown menu, select **Interpreter** and change the insightedge_jdbc default.url value from 'demo' to 'flights_space'.
1. Save your changes and return to the notebook.
1. Run the notebook (you will see the data populated) and wait until all the paragraphs are run.

### Starting a Kafka Producer


This model simulates a flight data feeder component. Kafka needs to be deployed as a producer (feeder unit) so it can stream the 2019 flight data to InsightEdge.This requires configuring the following properties:

* kafka.bootstrapServer: The IP of the Apache Zookeeper module used by Kafka.
* feeder.flights.path: The full path that is used to save the data file. 

Build the Feeder pu jar by running:

```sh
$ mvn clean package -f ./kafkaFeederPU/pom.xml
```
Copy the feeder data file from ./data/data.csv to /tmp/data.csv

Use the following command to set up the Kafka producer:

```sh
$ <InsightEdge_Dir>/bin/gs.sh pu deploy --property=kafka.bootstrapServer=127.0.0.1 --property=feeder.flights.path=/tmp/data.csv feeder ./kafkaFeederPU/target/kafka-pers-feeder.jar
```

### Running the Prediction Simulation

After the Kafka producer is started, you can run the INSIGHTEDGE-GETTING-STARTED-2 notebook to run the paragraphs that contain the prediction models.



