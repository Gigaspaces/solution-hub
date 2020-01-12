package com.gigaspaces;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.message.internal.MessageBodyFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main class.
 *
 */
public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static  String BASE_URI = "http://localhost:8082/insightedge/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in com.gigaspaces package
        final ResourceConfig rc = new ResourceConfig().packages("com.gigaspaces");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    //args : properties file name, metaData file name
    public static void main(String[] args) throws Exception {
        Logger logger = Logger.getLogger(ResourceConfig.class.getName());
        logger.setLevel (Level.ALL);
        Logger logger2 = Logger.getLogger(org.glassfish.jersey.message.internal.WriterInterceptorExecutor.class.getName());
        logger2.setLevel(Level.ALL);
        Logger logger3 = Logger.getLogger(MessageBodyFactory.class.getName());
        logger3.setLevel(Level.ALL);
        if (args != null && args.length == 2){
            Properties props = getProperties(args[0]);
            String host = props.getProperty("CONNECTOR_HOST");
            String port = props.getProperty("CONNECTOR_PORT");
            if (host==null || port==null ){
                throw new Exception("Excpecting CONNECTOR_HOST and CONNECTOR_PORT in properties file:" + args[0]);
            }
            BASE_URI = "http://"+host + ":"+ port + "/insightedge/";
            SpaceDS.setMetaData(readMetaData(args[1]));
            SpaceDS.setProperties(props);
        }
        else{
            throw new Exception("Excpecting 2 arguments: Properties file name and meta data file name");
        }
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.stop();
    }

    public static Properties getProperties(String fileIn) throws Exception{
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream(fileIn));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;

    }

    /*
    Excpects file with following row format:
    displayName,fullTypeName, dateFieldName, valuesFieldName, seriesFieldName
    displayName is the name that will appear in grafana, fullTypeName is the type name in the space,
    valuesFieldName is the name of the field in space class that contains the value (double)
    seriesFieldName is the name of the field in space class that categorize series (as type/product name/etc)
     */
    public  static TimeSeriesTypes readMetaData(String fileIn)throws java.io.IOException{
        TimeSeriesTypes metaData = new TimeSeriesTypes();
        String line = null;

        BufferedReader bufferedReader =new BufferedReader(new InputStreamReader(
                new FileInputStream(fileIn), "US-ASCII"));

        while ((line = bufferedReader.readLine()) != null) {
            if (line.contains("#"))
                continue;
            String[] temp = line.split(",");
            if (temp.length < 5) {
                continue;
            }
            String display = temp[0].replace(System.getProperty("line.separator"), "").trim();
            TimeSeriesType typeData = new TimeSeriesType(temp[1], temp[2], temp[3],temp[4]);
            System.out.println("PUT : " + display + " TimeSeriesType:" +typeData);
            metaData.put(display,typeData );

        }
        bufferedReader.close();
        return metaData;
    }

   /* public static byte[] transcodeField(byte[] source, Charset from, Charset to) {
        return new String(source, from).getBytes(to);
    }*/


}

