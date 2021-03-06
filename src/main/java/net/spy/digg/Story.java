package net.spy.digg;

/**
 * A Digg story.
 */
public interface Story {

	/**
	 * Get the number of comments on this story.
	 */
	int getComments();

	/**
	 * Get the topic container of this story.
	 * Note that this container will not contain all of the topics.
	 */
	TopicContainer getContainer();

	/**
	 * Get the description of this story.
	 */
	String getDescription();

	/**
	 * Get the link to this story within digg.
	 */
	String getDiggLink();

	/**
	 * Get the number of diggs on this story.
	 */
	int getDiggs();

	/**
	 * Get the ID of this story.
	 */
	int getId();

	/**
	 * Get the link to this story.
	 */
	String getLink();

	/**
	 * Get the short URL for this story.
	 */
	String getShortURL();

	/**
	 * Get the number of times this short URL was viewed.
	 */
	int getShortURLViewCount();

	/**
	 * Get the status of this story.
	 */
	String getStatus();

	/**
	 * Get the timestamp this story was promoted.
	 *
	 * @return the number of milliseconds, or -1 if this story has no promote
	 * date.
	 */
	long getPromoteTimestamp();

	/**
	 * Add the media type of this story.
	 */
	String getMedia();

	/**
	 * Get the timestamp this story was submitted.
	 */
	long getSubmittedTimestamp();

	/**
	 * Get the title of this story.
	 */
	String getTitle();

	/**
	 * Get the topic of this story.
	 */
	Topic getTopic();

	/**
	 * Get the user who submitted this story.
	 */
	User getUser();

	/**
	 * If this story has a thumbnail, return it here.  Otherwise return null.
	 */
	Thumbnail getThumbnail();
}