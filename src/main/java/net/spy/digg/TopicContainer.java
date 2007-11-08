package net.spy.digg;

import java.util.Collection;

/**
 * A container of topics.
 */
public interface TopicContainer extends Collection<Topic> {

	/**
	 * Get the name of this topic container.
	 */
	String getName();

	/**
	 * Get the short name of this topic container.
	 */
	String getShortName();

}