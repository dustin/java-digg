package net.spy.digg.parsers;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.spy.digg.Story;
import net.spy.digg.Topic;
import net.spy.digg.TopicContainer;
import net.spy.digg.User;

/**
 * A Digg story.
 */
class StoryImpl implements Story {

	private User user;
	private TopicContainerImpl container;
	private TopicImpl topic;

	private int id;
	private String link;
	private String diggLink;
	private String status;
	private String title;
	private String description;
	private long submitDate;
	private int diggs;
	private int comments;

	StoryImpl(Node n) {
		super();

		id=Integer.parseInt(BaseParser.getAttr(n, "id"));
		link=BaseParser.getAttr(n, "link");
		submitDate=1000*Long.parseLong(BaseParser.getAttr(n, "submit_date"));
		diggs=Integer.parseInt(BaseParser.getAttr(n, "diggs"));
		comments=Integer.parseInt(BaseParser.getAttr(n, "comments"));
		diggLink=BaseParser.getAttr(n, "href");
		status=BaseParser.getAttr(n, "status");

		NodeList nl=n.getChildNodes();
		for(int i=0; i<nl.getLength(); i++) {
			Node cn=nl.item(i);
			String nm=cn.getNodeName();
			if(nm.equals("title")) {
				title=cn.getFirstChild().getNodeValue();
			} else if(nm.equals("description")) {
				description=cn.getFirstChild().getNodeValue();
			} else if(nm.equals("user")) {
				user=new UserImpl(cn);
			} else if(nm.equals("topic")) {
				topic=new TopicImpl(BaseParser.getAttr(cn, "name"),
						BaseParser.getAttr(cn, "short_name"));
			} else if(nm.equals("container")) {
				container=new TopicContainerImpl(BaseParser.getAttr(cn, "name"),
						BaseParser.getAttr(cn, "short_name"));
			} else if(cn.getNodeType() == Node.TEXT_NODE) {
				// skipping random text node
			} else {
				assert false : "Unexpected node: " + cn;
			}
		}
		// Add the topic to the container.
		container.add(topic);
		topic.setContainerName(container.getShortName());
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
