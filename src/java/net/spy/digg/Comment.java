package net.spy.digg;

/**
 * A comment event.
 */
public interface Comment extends Event {

	// <comment date="1177142419" story="1814797" id="6273341" up="1"
	// down="0" replies="0" replyto="6268248" user="mikesbaker">

	/**
	 * Get the number of diggs up.
	 */
	int getDiggsUp();

	/**
	 * Get the number of diggs down.
	 */
	int getDiggsDown();

	/**
	 * Get the ID of the comment to which this comment is in response.
	 */
	Integer getReplyId();

	/**
	 * Get the comment text.
	 */
	String getComment();
}
