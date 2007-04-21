package net.spy.digg.parsers;

import net.spy.digg.Event;

/**
 * An event.
 */
public class EventImpl implements Event {

	private long date;
	private int storyId;
	private int eventId;
	private String user;
	private String status;

	/**
	 * Construct an Event.
	 */
	EventImpl(long d, int sId, int eId, String u, String s) {
		super();
		date = d;
		storyId = sId;
		eventId = eId;
		user = u;
		status = s;
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
