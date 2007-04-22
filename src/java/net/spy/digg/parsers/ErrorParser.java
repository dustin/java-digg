package net.spy.digg.parsers;

import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * Parse a single error.
 */
public class ErrorParser extends BaseParser {

	private int errorId;
	private String errorMessage;

	@Override
	protected String getRootElementName() {
		return "error";
	}

	@Override
	protected void handleDocument(Document doc)
		throws SAXException, IOException {
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
