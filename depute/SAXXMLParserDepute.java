package depute;


import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.parsers.SAXParserFactory;


public class SAXXMLParserDepute {
	
	private static final Logger log = Logger.getLogger(NiDeputesNiSoumisesServlet.class.getName());
	
    public static List<Depute> parse(InputStream is) {
        List<Depute> deputes = null;
        try {
            XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
            SAXXMLHandlerDepute saxHandler = new SAXXMLHandlerDepute();
            xmlReader.setContentHandler(saxHandler);
            xmlReader.parse(new InputSource(is));
            deputes = saxHandler.getListeDeputes();
        } catch (Exception ex) {
            log.info("XML SAXXMLParser: parse() failed");
        }
        return deputes;
    }
}

