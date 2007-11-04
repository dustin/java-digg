package net.spy.digg;

import java.net.URL;

/**
 * A gallery.
 */
public interface GalleryPhoto {

	/**
	 * Get the ID of this image.
	 */
	int getId();

	/**
	 * Get the date this image was submitted.
	 */
	long getSubmitTimestamp();

	/**
	 * Get the number of comments on this image.
	 */
	int getNumComments();

	/**
	 * Get the URL to the image itself.
	 */
	URL getImgSrc();

	/**
	 * Get the URL to the page containing this image.
	 */
	URL getImgHref();

	/**
	 * Get the title of this image.
	 */
	String getTitle();

	/**
	 * Get the User who created this image.
	 */
	User getUser();
}
