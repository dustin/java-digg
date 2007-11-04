package net.spy.digg.parsers;

import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import net.spy.digg.GalleryPhoto;

public class GalleriesParser extends TimePagedItemParser<GalleryPhoto> {

	@Override
	protected String getRootElementName() {
		return "gallery";
	}

	@Override
	protected void handleDocument(Document doc) throws SAXException,
			IOException {

		parseCommonFields(doc);

		NodeList nl=doc.getElementsByTagName("galleryphoto");
		for(int i=0; i<nl.getLength(); i++) {
			addItem(new GalleryPhotoImpl(nl.item(i)));
		}
	}

}
