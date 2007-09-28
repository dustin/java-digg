package net.spy.digg.parsers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Parse a digg errors response.
 */
public class ErrorsParser extends BaseParser {

	private Map<Integer, String> errors=null;

	@Override
	protected String getRootElementName() {
		return "errors";
	}

	@Override
	protected void handleDocument(Document doc)
		throws SAXException, IOException {

        errors=new HashMap<Integer, String>();
        final NodeList nl=doc.getDocumentElement().getElementsByTagName("error");
        for(int i=0; i<nl.getLength(); i++) {
        	final Node n=nl.item(i);
        	errors.put(Integer.parseInt(getAttr(n, "code")),
        			getAttr(n, "message"));
        }
	}

	/**
	 * Get the map of error IDs to names.
	 */
	public Map<Integer, String> getErrors() {
		return errors;
	}
}
