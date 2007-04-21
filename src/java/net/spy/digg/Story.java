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
	 * Get the status of this story.
	 */
	String getStatus();

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

}