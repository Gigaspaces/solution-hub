# Using Grafana for Monitoring and Analytics in Kubernetes

This package contains the InsightEdge Metrics demo, which is used to show how Grafana can be integrated with InsightEdge in order to provide monitoring and analytics capabilities based on metrics generated by the system and collected by an InfluxDB database. This readme describes the general steps that need to be performed in order to complete the integration and view sample dashboards based on InsightEdge metrics.

For detailed instructions on how to integrate Grafana with InsightEdge in a Kubernetes environment, see the Solution Hub (https://docs.gigaspaces.com/solution-hub/grafana-influxdb-ie-kubernetes.html) on the GigaSpaces documentation website.

**To use Grafana for monitoring and analytics of InsightEdge data in Kubernetes:**

1. Clone this InsightEdge metrics demo to your local machine.
1. Download (https://portal.influxdata.com/downloads/) and install (https://docs.influxdata.com/influxdb/v1.7/introduction/installation/) InfluxDB on your local machine and in Kubernetes.
1. Create an InfluxDB database locally, and set up port forwarding in Kubernetes.
1. Install Grafana in Kubernetes and set up port forwarding.
1. Create a custom Docker image and upload it to your Docker Hub account.
1. Install InsightEdge in Kubernetes using the custom Docker image.
1. In the Grafana client, configure the data source and provide the URL for the GigaSpaces metrics reporter.
1. Import the two sample dashboards from the InsightEdge metrics demo software package.