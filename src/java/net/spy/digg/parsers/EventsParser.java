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

		NodeList nl=doc.getElementsByTagName("digg");
		for(int i=0; i<nl.getLength(); i++) {
			Node n=nl.item(i);
			addItem(new EventImpl(
					1000*Long.parseLong(getAttr(n, "date")),
					Integer.parseInt(getAttr(n, "story")),
					Integer.parseInt(getAttr(n, "id")),
					getAttr(n, "user"), getAttr(n, "status")));
		}
	}

}
