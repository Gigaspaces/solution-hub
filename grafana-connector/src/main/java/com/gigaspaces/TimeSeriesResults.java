package com.gigaspaces;


import java.util.List;

/*Example Expected Grafana timeSeries Results
[
        {
        "target":"upper_75", // The field being queried for
        "datapoints":[
        [622,1450754160000],  // Metric value as a float , unixtimestamp in milliseconds
        [365,1450754220000]
        ]
        },
        {
        "target":"upper_90",
        "datapoints":[
        [861,1450754160000],
        [767,1450754220000]
        ]
        }
        ]*/
public class TimeSeriesResults {
    String target;
    List datapoints;

    public TimeSeriesResults() {
    }

    public TimeSeriesResults(String target) {
        this.target = target;
    }

    public List getDatapoints() {
        return datapoints;
    }

    public void setDatapoints(List datapoints) {
        this.datapoints = datapoints;
    }

    /*
        Excpects list of arrays of 2 doubles.
         */
    public TimeSeriesResults(String target, List targetData){
        this.target = target;
        this.datapoints = targetData;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }





}
