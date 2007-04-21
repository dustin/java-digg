package net.spy.digg.parsers;

import java.io.IOException;
import java.io.InputStream;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import net.spy.digg.Event;

/**
 * Parse events.
 */
public class EventsParser extends TimePagedItemParser<Event> {

	/**
	 * Get an event parser.
	 */
	public EventsParser(InputStream is) throws SAXException, IOException {
		Document doc = getDocument(is, "events");

		parseCommonFields(doc);

		NodeList nl=doc.getFirstChild().getChildNodes();
		for(int i=0; i<nl.getLength(); i++) {
			Node n=nl.item(i);
			String nm=n.getNodeName();
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
