package net.spy.digg.parsers;

import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import net.spy.digg.Event;

/**
 * Parse events.
 */
public class EventsParser extends TimePagedItemParser<Event> {

	@Override
	protected String getRootElementName() {
		return "events";
	}

	@Override
	protected void handleDocument(Document doc)
		throws SAXException, IOException {
		parseCommonFields(doc);

		final NodeList nl=doc.getFirstChild().getChildNodes();
		for(int i=0; i<nl.getLength(); i++) {
			final Node n=nl.item(i);
			final String nm=n.getNodeName();
			if(nm.equals("digg")) {
				addItem(new EventImpl(n));
			} else if(nm.equals("comment")) {
				addItem(new CommentImpl(n));
			} else if(n.getNodeType() == Node.TEXT_NODE) {
				// Ignore text
			} else {
				assert false : "Unhandled node: " + n;
			}
		}
	}

}
