package net.spy.digg.parsers;

import java.io.Serializable;

import org.w3c.dom.Node;

import net.spy.digg.Event;

/**
 * An event.
 */
public class EventImpl implements Event, Serializable {

	private long date;
	private int storyId;
	private int eventId;
	private String user;
	private String status;

	/**
	 * Construct an Event.
	 */
	EventImpl(Node n) {
		super();
		date=1000*Long.parseLong(BaseParser.getAttr(n, "date"));
		storyId=Integer.parseInt(BaseParser.getAttr(n, "story"));
		eventId=Integer.parseInt(BaseParser.getAttr(n, "id"));
		user=BaseParser.getAttr(n, "user");
		status=BaseParser.getAttr(n, "status");
	}

	/* (non-Javadoc)
	 * @see net.spy.digg.Event#getDateStamp()
	 */
	public long getTimestamp() {
		return date;
	}

	/* (non-Javadoc)
	 * @see net.spy.digg.Event#getEventId()
	 */
	public int getEventId() {
		return eventId;
	}

	/* (non-Javadoc)
	 * @see net.spy.digg.Event#getStatus()
	 */
	public String getStatus() {
		return status;
	}

	/* (non-Javadoc)
	 * @see net.spy.digg.Event#getStoryId()
	 */
	public int getStoryId() {
		return storyId;
	}

	/* (non-Javadoc)
	 * @see net.spy.digg.Event#getUser()
	 */
	public String getUser() {
		return user;
	}

	@Override
	public String toString() {
		return "{Event id=" + eventId + ", sid=" + storyId
			+ ", user=" + user + ", status=" + status + "}";
	}
}
