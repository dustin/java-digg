package net.spy.digg.parsers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * Parse a stories response.
 */
public class StoriesParser extends TimePagedItemParser {

	private Collection<StoryImpl> stories=null;

	/**
	 * Get a stories parser.
	 */
	public StoriesParser(InputStream is) throws SAXException, IOException {
		Document doc = getDocument(is, "stories");

		parseCommonFields(doc);

		stories=new ArrayList<StoryImpl>(getCount());

		NodeList nl=doc.getElementsByTagName("story");
		for(int i=0; i<nl.getLength(); i++) {
			stories.add(new StoryImpl(nl.item(i)));
		}
	}

	/**
	 * Get all of the stories that were found.
	 */
	public Collection<StoryImpl> getStories() {
		return stories;
	}
}
