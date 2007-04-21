package net.spy.digg.parsers;

import java.io.IOException;
import java.io.InputStream;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import net.spy.digg.Story;

/**
 * Parse a stories response.
 */
public class StoriesParser extends TimePagedItemParser<Story> {

	/**
	 * Get a stories parser.
	 */
	public StoriesParser(InputStream is) throws SAXException, IOException {
		Document doc = getDocument(is, "stories");

		parseCommonFields(doc);

		NodeList nl=doc.getElementsByTagName("story");
		for(int i=0; i<nl.getLength(); i++) {
			addItem(new StoryImpl(nl.item(i)));
		}
	}
}
