package net.spy.digg;

/*
<thumbnail originalwidth="1000" originalheight="750"
	contentType="image/jpeg"
	src="http://digg.com/hardware/The_World_s_1st_Web_Server_Photo/t.jpg"
	width="80" height="80"/>
 */
public interface Thumbnail {

	/**
	 * Get the original width of the image.
	 */
	int getWidth();

	/**
	 * Get the original height of the image.
	 */
	int getHeight();

	/**
	 * Get the width of the thumbnail of the image.
	 */
	int getTnWidth();

	/**
	 * Get the height of the thumbnail of the image.
	 */
	int getTnHeight();

	/**
	 * Get the content type of the link.
	 */
	String getContentType();

	/**
	 * Get the URL of the thumbnail.
	 */
	String getURL();
}
