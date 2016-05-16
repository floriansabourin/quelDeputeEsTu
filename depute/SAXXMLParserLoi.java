package depute;


import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.parsers.SAXParserFactory;


public class SAXXMLParserLoi {
	
	private static final Logger log = Logger.getLogger(NiDeputesNiSoumisesServlet.class.getName());
	
    public static List<Loi> parse(InputStream is) {
        List<Loi> lois = null;
        try {
            XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
            SAXXMLHandlerLoi saxHandler = new SAXXMLHandlerLoi();
            xmlReader.setContentHandler(saxHandler);
            xmlReader.parse(new InputSource(is));
            lois = saxHandler.getListeLois();
        } catch (Exception ex) {
            log.info("XML SAXXMLParser: parse() failed");
        }
        return lois;
    }
}
