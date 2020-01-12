package com.gigaspaces.droolsintegration.util.xml;

import com.thoughtworks.xstream.XStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.io.*;

/**
 * This class reads Drools XML files.
 */
@Component
public class XmlReader {

    private final Log log = LogFactory.getLog(XmlReader.class);

    /**
     * Access to XML library.
     */
    private XStream xstream;

    @PostConstruct
    public void init() {
        xstream = XStreamInitializer.getInitializedXStream();
    }

    /**
     * Read a file with the given name.
     *
     * @param fileName Name of the file to read.
     * @return The XMl object tree.
     */
    public XmlChangeSet readDroolsRuleResourceFromXml(final String fileName) {
        InputStream is = this.getClass().getResourceAsStream("/" + fileName);

        if(is == null) {
            log.error("Resource '" + fileName + "' not found! Nothing to read.");
        }else {

            InputStreamReader isr;
            BufferedReader br;

            try {
                isr = new InputStreamReader(is, "UTF-8");
                br = new BufferedReader(isr);

                return (XmlChangeSet) xstream.fromXML(br);
            }catch(UnsupportedEncodingException e) {
                log.error("Error reading file", e);
            }
        }

        return null;
    }

    /**
     * Class that properly initializes the XStream library.
     */
    private static final class XStreamInitializer {

        private XStreamInitializer() {
        }

        /**
         * Creates xstream object and sets some default-names.
         *
         * @return Initialized xstream object.
         */
        public static XStream getInitializedXStream() {
            XStream xstream = new XStream();
            xstream.setMode(XStream.NO_REFERENCES);

            initInputStructure(xstream);

            return xstream;
        }

        private static void initInputStructure(final XStream xstream) {
            initClass(xstream, XmlAdd.class);
            initClass(xstream, XmlChangeSet.class);
            initClass(xstream, XmlResource.class);
        }

        @SuppressWarnings("rawtypes")
        private static void initClass(final XStream xstream, final Class clazz) {
            xstream.processAnnotations(clazz);
        }
    }

}