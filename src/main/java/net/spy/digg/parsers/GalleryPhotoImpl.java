package net.spy.digg.parsers;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.spy.digg.GalleryPhoto;
import net.spy.digg.User;

public class GalleryPhotoImpl implements GalleryPhoto, Serializable {

	private final int id;
	private final URL imgHref;
	private final URL imgSrc;
	private final int numComments;
	private final long submitDate;
	private final String title;
	private final User user;

	GalleryPhotoImpl(Node n) throws IOException {
		super();

		id=Integer.parseInt(BaseParser.getAttr(n, "id"));
		imgHref=new URL(BaseParser.getAttr(n, "href"));
		imgSrc=new URL(BaseParser.getAttr(n, "src"));
		submitDate=1000*Long.parseLong(BaseParser.getAttr(n, "submit_date"));
		numComments=Integer.parseInt(BaseParser.getAttr(n, "comments"));

		String t=null;
		User u=null;

		NodeList nl=n.getChildNodes();
		for(int i=0; i<nl.getLength(); i++) {
			Node cn=nl.item(i);
			String nm=cn.getNodeName();
			if(nm.equals("title")) {
				t=cn.getFirstChild().getNodeValue();
			} else if(nm.equals("user")) {
				u=new UserImpl(cn);
			} else if(cn.getNodeType() == Node.TEXT_NODE) {
				// skipping random text node
			} else {
				assert false : "Unexpected node: " + cn;
			}
		}

		user=u;
		title=t;

		assert user != null;
		assert title != null;
	}

	public int getId() {
		return id;
	}

	public URL getImgHref() {
		return imgHref;
	}

	public URL getImgSrc() {
		return imgSrc;
	}

	public int getNumComments() {
		return numComments;
	}

	public long getSubmitTimestamp() {
		return submitDate;
	}

	public String getTitle() {
		return title;
	}

	public User getUser() {
		return user;
	}

}
