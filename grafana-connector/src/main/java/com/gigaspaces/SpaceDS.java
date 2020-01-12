package com.gigaspaces;

import com.gigaspaces.document.SpaceDocument;
import com.gigaspaces.query.QueryResultType;
import com.j_spaces.core.client.SQLQuery;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.SpaceProxyConfigurer;

import java.util.*;
import java.util.logging.Logger;

/*Space Data source - holds proxy for the space,
 do all operation against the space
 TBD - consider using localview instead of remote access to the space, depends on usecase and amount of data
 */
public class SpaceDS {
    private static final Logger logger = Logger.getLogger(SpaceDS.class.getName());
    private static GigaSpace gs;
    private static TimeSeriesTypes  metaData;

    static public void setProperties(Properties spaceProperties){
        String spaceName = spaceProperties.getProperty("XAP_SPACE_NAME","demo");
        SpaceProxyConfigurer configurer = new SpaceProxyConfigurer(spaceName);
        String group = spaceProperties.getProperty("XAP_LOOKUP_GROUPS",System.getenv("XAP_LOOKUP_GROUPS"));
        if (group!= null)
            configurer.lookupGroups(group);
        String locator = spaceProperties.getProperty("XAP_LOOKUP_LOCATORS",System.getenv("XAP_LOOKUP_LOCATORS"));
        if (locator!= null)
            configurer.lookupGroups(spaceProperties.getProperty("XAP_LOOKUP_LOCATORS"));

        gs = new GigaSpaceConfigurer(configurer).create();
    }


    public static GigaSpace getGs() {
        return gs;
    }

    public static void setGs(GigaSpace gigaSpace) {
        gs = gigaSpace;
    }

    public static TimeSeriesTypes getMetaData() {
        return metaData;
    }

    public static void setMetaData(TimeSeriesTypes setmetaData) {
        metaData = setmetaData;
    }


    static public Set<String> getTimeSeriesTypes(){
        logger.info("getTimeSeriesTypes was called");
        return metaData.getDisplayTypeNames();
    }



    static public TimeSeriesResults[] getResultsAsTimeSeries(String typeName,Range range){
        logger.info("getResultsAsTimeSeries was called for type:" +typeName + " range:" +range);
        TimeSeriesType type = metaData.get(typeName.trim());
        try {
            if (type == null) {
                System.out.println("metadata:" +  metaData);
                throw new Exception("Could not find type data for table: " + typeName);
            }
            SQLQuery query = new SQLQuery<SpaceDocument>(type.getFullTypeName(), type.getDateFieldName() + ">= ? and " + type.getDateFieldName() + " <=? ORDER BY " + type.getDateFieldName() , QueryResultType.DOCUMENT, range.getStart(), range.getEnd())
                    .setProjections(type.getDateFieldName(),type.getSeriesFieldName(),type.getValuesFieldName());
            //toDo move to SpaceIterator (problen is that it dosn't support ordering yet)
            //SpaceIterator<SpaceDocument> itr  = gs.iterator(query);
            SpaceDocument[] results = (SpaceDocument[]) gs.readMultiple(query);
            HashMap<String, List> map = new HashMap<>();
            for (int k = 0; k < results.length; k++) {
            //while (itr.hasNext()){
                SpaceDocument doc = results[k];
                //SpaceDocument doc = itr.next();
                String target = doc.getProperty(type.getSeriesFieldName());
                Object[] tuple = new Object[2];
                tuple[0] = doc.getProperty(type.getValuesFieldName());
                tuple[1] = doc.getProperty(type.getDateFieldName());
                List targetResults = map.get(target);
                if (targetResults == null) {
                    targetResults = new ArrayList();
                }
                targetResults.add(tuple);
                map.put(target, targetResults);
            }
            TimeSeriesResults[] ts = new TimeSeriesResults[map.keySet().size()];

            Iterator iterator = map.keySet().iterator();
            int index = 0;
            while (iterator.hasNext()) {
                String target = (String) iterator.next();
                List targetData = map.get(target);
                TimeSeriesResults targetSeries = new TimeSeriesResults(target, targetData);
                ts[index] = targetSeries;
                index++;
            }

            return ts;
        }
        catch (Throwable t){
            logger.severe("getResultsAsTimeSeries for type:" +typeName+ " failed :"+ t);
            System.out.println("Got Exception :" +t);
            t.printStackTrace();

        }
        return null;
    }


    /*Expected structure for Grafana for table result
     {
    "columns":[
      {"text":"Time","type":"time"},
      {"text":"Country","type":"string"},
      {"text":"Number","type":"number"}
    ],
    "rows":[
      [1234567,"SE",123],
      [1234567,"DE",231],
      [1234567,"US",321]
    ],
    "type":"table"
  }
      */

    static public TableResults getTableResults(String typeName, Range range){
        logger.info("getTableResults was called for type:" +typeName + " range:" +range);
        try {
            TimeSeriesType type = metaData.get(typeName);
            TableResults finalresults = new TableResults();
            TableResults.Column[] columns = new TableResults.Column[3];
            columns[0] = new TableResults.Column(type.getDateFieldName(), "time");
            columns[1] = new TableResults.Column(type.getSeriesFieldName(), "string");
            columns[2] = new TableResults.Column(type.getValuesFieldName(), "number");
            finalresults.setColumns(columns);

            SQLQuery query = new SQLQuery<SpaceDocument>(type.getFullTypeName(), type.getDateFieldName() + ">= ? and " + type.getDateFieldName() + " <=? ORDER BY " + type.getDateFieldName(), QueryResultType.DOCUMENT, range.getStart(), range.getEnd());
            SpaceDocument[] results = (SpaceDocument[]) gs.readMultiple(query);
            Object[][] rows = new Object[results.length][3];
            for (int k = 0; k < results.length; k++) {
                //while (itr.hasNext()){
                SpaceDocument doc = results[k];
                //SpaceDocument doc = itr.next();
                String target = doc.getProperty(type.getSeriesFieldName());
                //Object[] tuple = new Object[doc.getProperties().size()];
                Object[] tuple = new Object[3];
                tuple[0] = doc.getProperty(type.getDateFieldName());
                tuple[1] = doc.getProperty(type.getSeriesFieldName());
                tuple[2] = doc.getProperty(type.getValuesFieldName());
                rows[k] = tuple;
            }
            finalresults.setRows(rows);
            return finalresults;
        }
        catch (Throwable t){
            logger.severe("getTableResults for type:" +typeName+ " failed :"+ t);
            System.out.println("Got Exception :" +t);
            t.printStackTrace();
        }
        return null;
    }











}
