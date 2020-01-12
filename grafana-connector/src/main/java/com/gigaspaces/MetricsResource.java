package com.gigaspaces;

import javax.json.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("metrics")
public class MetricsResource {

    //Used to test that the controller is up.can be modified the ensure the space is accessable
    @GET
    @Produces("application/json")
    @Path("/")
    public Response get() {
        return Response.ok().build();
    }

    @OPTIONS
    @Path("/annotations")
    public Response annotationOptions() {

        return Response.noContent()
                .header("Access-Control-Allow-Headers", "accept, content-type")
                .header("Access-Control-Allow-Methods", "POST")
                .header("Access-Control-Allow-Origin", "*")
                .build();
    }



    // Currently not implemented can add lables according to time as certian events (blackfriday/electionday/closinghour...)
    @POST
    @Path("/annotations")
    @Produces("application/json")
    @Consumes("application/json")
    public JsonArray annotation(JsonObject annotation) {

        String sq = annotation.toString();
        System.out.println(sq);

        JsonArrayBuilder arr = Json.createArrayBuilder();
        JsonObjectBuilder obj = Json.createObjectBuilder();
        JsonObject an = annotation.getJsonObject("annotation");
        obj.add("annotation", an);

        Range range = Range.from(annotation.getJsonObject("range"));

        obj.add("time", (range.getDuration() / 2) + range.getStart());
        obj.add("title", "TestTitle");
        obj.add("tags", Json.createArrayBuilder().add("tag1").add("tag2").add("tag3").build());
        obj.add("text", "someText");

        arr.add(obj);
        return arr.build();
    }

    @OPTIONS
    @Path("/search")
    public Response searchOptions() {

        return Response.noContent()
                .header("Access-Control-Allow-Headers", "accept, content-type")
                .header("Access-Control-Allow-Methods", "POST")
                .header("Access-Control-Allow-Origin", "*")
                .build();
    }

    // Return list of space class we can query in grafana (according to the tabelDef file supplied)
    @POST
    @Path("/search")
    @Produces("application/json")
    public Object search(/*JsonObject query*/) {
        return SpaceDS.getTimeSeriesTypes();
    }

    @OPTIONS
    @Path("/query")
    public Response queryOptions() {

        return Response.noContent()
                .header("Access-Control-Allow-Headers", "accept, content-type")
                .header("Access-Control-Allow-Methods", "POST")
                .header("Access-Control-Allow-Origin", "*")
                .build();
    }



    // Return timeSEries or table by reading from the space the required type in required time range
    @POST
    @Path("/query")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Object query(JsonObject query) {
        Range range = Range.from(query.getJsonObject("range"));

        JsonArray targets = query.getJsonArray("targets");
        if (targets != null) {
            JsonObjectBuilder target = Json.createObjectBuilder();
            String tgt = targets.getJsonObject(0).getString("target");
            String type = targets.getJsonObject(0).getString("type");
            if (type.equals("table")) {
                Object[] arr = new Object[1];
                arr[0] = getTableResults(tgt, range);
                return arr;
            } else {
                return getTimeSeriesResults(tgt, range);
            }
        } else {
            return Json.createArrayBuilder().build();
        }
    }



    /*Return space data as Grafana table result*/
    public Object getTableResults(String type, Range range){
        System.out.println("getTableResults:Query target is:" + type);
        return SpaceDS.getTableResults(type, range);
    }

    /*Return space data as Grafana TimeSeries result*/
    public Object getTimeSeriesResults(String type, Range range){
        System.out.println("getTimeSeriesResults:Query target is:" + type);
        return SpaceDS.getResultsAsTimeSeries(type, range);
    }

    @GET
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    public Object test(){
        System.out.println("Calling test with getTimeSeriesTypes");
        return SpaceDS.getTimeSeriesTypes();
    }

}
