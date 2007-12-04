package net.spy.digg;

/**
 * Thumbnail as may appear on a story.
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
