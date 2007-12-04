package net.spy.digg.parsers;

import java.io.Serializable;

import net.spy.digg.Thumbnail;

/**
 * Implementation of a thumbnail.
 */
class ThumbnailImpl implements Thumbnail, Serializable {

	private final int height;
	private final int width;
	private final String contentType;
	private final int tnWidth;
	private final int tnHeight;
	private final String url;

	public ThumbnailImpl(String url, String contentType, int width, int height,
			int tnWidth, int tnHeight) {
		super();
		this.url = url;
		this.contentType = contentType;
		this.height = height;
		this.width = width;
		this.tnWidth = tnWidth;
		this.tnHeight = tnHeight;
	}

	public int getHeight() {
		return height;
	}
	public int getWidth() {
		return width;
	}
	public String getContentType() {
		return contentType;
	}
	public int getTnWidth() {
		return tnWidth;
	}
	public int getTnHeight() {
		return tnHeight;
	}
	public String getURL() {
		return url;
	}

}
