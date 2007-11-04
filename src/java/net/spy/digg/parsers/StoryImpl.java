package net.spy.digg.parsers;

import java.io.Serializable;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.spy.digg.Story;
import net.spy.digg.Topic;
import net.spy.digg.TopicContainer;
import net.spy.digg.User;

/**
 * A Digg story.
 */
public class StoryImpl implements Story, Serializable {

	private final User user;
	private final TopicContainer container;
	private final Topic topic;

	private final int id;
	private final String link;
	private final String diggLink;
	private final String status;
	private final String title;
	private final String description;
	private final long submitDate;
	private final int diggs;
	private final int comments;

	StoryImpl(Node n) {
		super();

		id=Integer.parseInt(BaseParser.getAttr(n, "id"));
		link=BaseParser.getAttr(n, "link");
		submitDate=1000*Long.parseLong(BaseParser.getAttr(n, "submit_date"));
		diggs=Integer.parseInt(BaseParser.getAttr(n, "diggs"));
		comments=Integer.parseInt(BaseParser.getAttr(n, "comments"));
		diggLink=BaseParser.getAttr(n, "href");
		status=BaseParser.getAttr(n, "status");

		String t=null;
		String d=null;
		User u=null;
		Topic top=null;
		TopicContainer tc=null;

		final NodeList nl=n.getChildNodes();
		for(int i=0; i<nl.getLength(); i++) {
			final Node cn=nl.item(i);
			final String nm=cn.getNodeName();
			if(nm.equals("title")) {
				t=cn.getFirstChild().getNodeValue();
			} else if(nm.equals("description")) {
				d=cn.getFirstChild().getNodeValue();
			} else if(nm.equals("user")) {
				u=new UserImpl(cn);
			} else if(nm.equals("topic")) {
				top=new TopicImpl(BaseParser.getAttr(cn, "name"),
						BaseParser.getAttr(cn, "short_name"));
			} else if(nm.equals("container")) {
				tc=new TopicContainerImpl(BaseParser.getAttr(cn, "name"),
						BaseParser.getAttr(cn, "short_name"));
			} else if(cn.getNodeType() == Node.TEXT_NODE) {
				// skipping random text node
			} else {
				assert false : "Unexpected node: " + cn;
			}
		}

		// Set the final fields.
		title=t;
		description=d;
		user=u;
		topic=top;
		container=tc;

		// Add the topic to the container.
		container.add(topic);
		((TopicImpl)topic).setContainerName(container.getShortName());
	}

	/* (non-Javadoc)
	 * @see net.spy.digg.Story#getComments()
	 */
	public int getComments() {
		return comments;
	}

	/* (non-Javadoc)
	 * @see net.spy.digg.Story#getContainer()
	 */
	public TopicContainer getContainer() {
		return container;
	}

	/* (non-Javadoc)
	 * @see net.spy.digg.Story#getDescription()
	 */
	public String getDescription() {
		return description;
	}

	/* (non-Javadoc)
	 * @see net.spy.digg.Story#getDiggLink()
	 */
	public String getDiggLink() {
		return diggLink;
	}

	/* (non-Javadoc)
	 * @see net.spy.digg.Story#getDiggs()
	 */
	public int getDiggs() {
		return diggs;
	}

	/* (non-Javadoc)
	 * @see net.spy.digg.Story#getId()
	 */
	public int getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see net.spy.digg.Story#getLink()
	 */
	public String getLink() {
		return link;
	}

	/* (non-Javadoc)
	 * @see net.spy.digg.Story#getStatus()
	 */
	public String getStatus() {
		return status;
	}

	/* (non-Javadoc)
	 * @see net.spy.digg.Story#getSubmitDate()
	 */
	public long getSubmittedTimestamp() {
		return submitDate;
	}

	/* (non-Javadoc)
	 * @see net.spy.digg.Story#getTitle()
	 */
	public String getTitle() {
		return title;
	}

	/* (non-Javadoc)
	 * @see net.spy.digg.Story#getTopic()
	 */
	public Topic getTopic() {
		return topic;
	}

	/* (non-Javadoc)
	 * @see net.spy.digg.Story#getUser()
	 */
	public User getUser() {
		return user;
	}

	@Override
	public String toString() {
		return "{Story id=" + id + " ``" + title + "''}";
	}
}
