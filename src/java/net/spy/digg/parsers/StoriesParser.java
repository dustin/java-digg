package net.spy.digg.parsers;

import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import net.spy.digg.Story;

/**
 * Parse a stories response.
 */
public class StoriesParser extends TimePagedItemParser<Story> {

	@Override
	protected String getRootElementName() {
		return "stories";
	}

	@Override
	protected void handleDocument(Document doc)
		throws SAXException, IOException {
		parseCommonFields(doc);

		final NodeList nl=doc.getElementsByTagName("story");
		for(int i=0; i<nl.getLength(); i++) {
			addItem(new StoryImpl(nl.item(i)));
		}
	}
}
