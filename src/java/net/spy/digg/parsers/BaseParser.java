package net.spy.digg.parsers;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * Base parser class.
 */
public abstract class BaseParser {

	/**
	 * Get a DOM document from the input stream.
	 *
	 * @param is the input stream
	 * @param rootEl the expected root element
	 * @return a DOM document
	 */
	protected Document getDocument(InputStream is, String rootEl)
		throws SAXException, IOException {
		try {
			DocumentBuilder docbuilder =
				DocumentBuilderFactory.newInstance().newDocumentBuilder();
		    Document doc=docbuilder.parse(is);

	        assert doc.getFirstChild().getNodeName().equals(rootEl);

			return doc;
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Get the named attribute for the given node.
	 */
	public static String getAttr(Node n, String s) {
		assert n !=  null : "Null node";
		assert s != null : "Null attr name";
		assert n.getAttributes() != null : "No attributes on " + n;
		assert n.getAttributes().getNamedItem(s) != null
			: "No attribute named " + s + " on " + n;
		assert n.getAttributes().getNamedItem(s).getNodeValue() != null
			: "Null attr value for " + s + " on " + n;
		return n.getAttributes().getNamedItem(s).getNodeValue();
	}
}