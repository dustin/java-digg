package net.spy.digg.parsers;

import java.io.Serializable;
import java.util.ArrayList;

import net.spy.digg.Topic;
import net.spy.digg.TopicContainer;

/**
 * A TopicContainer contains topics.
 */
public class TopicContainerImpl extends ArrayList<Topic>
	implements TopicContainer, Serializable {

	private String name=null;
	private String shortName=null;

	/**
	 * Construct a TopicContainer with the given name and short name.
	 */
	public TopicContainerImpl(String n, String sn) {
		super();
		name=n;
		shortName=sn;
	}

	/* (non-Javadoc)
	 * @see net.spy.digg.TopicContainer#getName()
	 */
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see net.spy.digg.TopicContainer#getShortName()
	 */
	public String getShortName() {
		return shortName;
	}

}
