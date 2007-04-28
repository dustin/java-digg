package net.spy.digg.parsers;

import java.io.Serializable;

import net.spy.digg.Topic;

/**
 * A topic within digg.
 */
public class TopicImpl implements Topic, Serializable {

	private String name=null;
	private String shortName=null;

	private String containerName=null;

	/**
	 * Construct a topic.
	 */
	public TopicImpl(String n, String sn) {
		super();
		name=n;
		shortName=sn;
	}

	/* (non-Javadoc)
	 * @see net.spy.digg.Topic#getContainerName()
	 */
	public String getContainerName() {
		return containerName;
	}

	/* (non-Javadoc)
	 * @see net.spy.digg.Topic#getName()
	 */
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see net.spy.digg.Topic#getShortName()
	 */
	public String getShortName() {
		return shortName;
	}

	@Override
	public String toString() {
		return "{Topic name=" + name + "}";
	}

	/**
	 * Set the name of this container.
	 */
	public void setContainerName(String to) {
		containerName=to;
	}

}
