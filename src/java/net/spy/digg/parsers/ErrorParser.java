package net.spy.digg.parsers;

import java.io.IOException;
import java.io.InputStream;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * Parse a single error.
 */
public class ErrorParser extends BaseParser {

	private int errorId;
	private String errorMessage;

	/**
	 * Parse an error list response.
	 */
	public ErrorParser(InputStream is) throws SAXException, IOException {
        Document doc = getDocument(is, "error");

        Node n=doc.getFirstChild();
        	errorId=Integer.parseInt(getAttr(n, "code"));
        	errorMessage=getAttr(n, "message");
	}

	/**
	 * Get the error ID.
	 */
	public int getErrorId() {
		return errorId;
	}

	/**
	 * Get the error message.
	 */
	public String getErrorMessage() {
		return errorMessage;
	}
	
}
