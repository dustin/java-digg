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
			final DocumentBuilder docbuilder =
				DocumentBuilderFactory.newInstance().newDocumentBuilder();
		    final Document doc=docbuilder.parse(is);

	        assert doc.getFirstChild().getNodeName().equals(rootEl);

			return doc;
		} catch (final ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Get the name of the expected root element.
	 */
	protected abstract String getRootElementName();

	/**
	 * Parse from the given input stream.
	 */
	public void parse(InputStream is) throws SAXException, IOException {
		final Document doc=getDocument(is, getRootElementName());
		handleDocument(doc);
	}

	/**
	 * Delegated document parsing.
	 */
	protected abstract void handleDocument(Document doc)
		throws SAXException, IOException;

	/**
	 * Get the named attribute for the given node.
	 */
	public static String getAttr(Node n, String s) {
		String rv=null;
		assert n !=  null : "Null node";
		assert s != null : "Null attr name";
		assert n.getAttributes() != null : "No attributes on " + n;
		if(n.getAttributes().getNamedItem(s) != null) {
			rv=n.getAttributes().getNamedItem(s).getNodeValue();
		}
		return rv;
	}

	/**
	 * Get the named attribute for the given node, or a default.
	 */
	public static String getAttr(Node n, String s, String def) {
		String rv=getAttr(n, s);
		return rv == null ? def : rv;
	}
}