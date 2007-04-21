package net.spy.digg;

import java.util.Date;

/**
 * An event.
 */
public interface Event {

	/**
	 * Get the timestamp at which this event occurred.
	 */
	long getTimestamp();

	/**
	 * Get the ID of this event.
	 */
	int getEventId();

	/**
	 * Get the status of this event.
	 */
	String getStatus();

	/**
	 * Get the ID of the story this event references.
	 */
	int getStoryId();

	/**
	 * Get the name of the user referenced by this event.
	 */
	String getUser();

}