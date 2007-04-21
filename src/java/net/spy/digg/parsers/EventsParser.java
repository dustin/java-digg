package net.spy.digg.parsers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * Parse events.
 */
public class EventsParser extends TimePagedItemParser {

	private Collection<EventImpl> events;

	/**
	 * Get an event parser.
	 */
	public EventsParser(InputStream is) throws SAXException, IOException {
		Document doc = getDocument(is, "events");

		parseCommonFields(doc);

		events=new ArrayList<EventImpl>(getCount());

		NodeList nl=doc.getElementsByTagName("digg");
		for(int i=0; i<nl.getLength(); i++) {
			Node n=nl.item(i);
			events.add(new EventImpl(
					1000*Long.parseLong(getAttr(n, "date")),
					Integer.parseInt(getAttr(n, "story")),
					Integer.parseInt(getAttr(n, "id")),
					getAttr(n, "user"), getAttr(n, "status")));
		}
	}

	/**
	 * Get the events.
	 */
	public Collection<EventImpl> getEvents() {
		return events;
	}

}
