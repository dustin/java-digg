package net.spy.digg.parsers;

import java.io.Serializable;

import net.spy.digg.Comment;

import org.w3c.dom.Node;

/**
 * Comment implementation.
 */
public class CommentImpl extends EventImpl implements Comment, Serializable {

	private final String comment;
	private final int down;
	private final int up;
	private final Integer replyId;
	private final Integer root;
	private final Integer level;

	CommentImpl(Node n) {
		super(n);
		down=Integer.parseInt(BaseParser.getAttr(n, "down"));
		up=Integer.parseInt(BaseParser.getAttr(n, "up"));
		replyId=getIntegerAttr(n, "replyto");
		root=getIntegerAttr(n, "root");
		level=getIntegerAttr(n, "level");
		final Node c = n.getFirstChild();
		assert c.getNodeType() == Node.CDATA_SECTION_NODE
			: "Expected CDATA, got " + c;
		comment=c.getNodeValue();
	}

	private Integer getIntegerAttr(Node n, String attr) {
		final String s=BaseParser.getAttr(n, attr);
		final Integer rv;
		if(s != null && s.length() > 0) {
			rv=new Integer(s);
		} else {
			rv=null;
		}
		return rv;
	}

	public String getComment() {
		return comment;
	}

	public int getDiggsDown() {
		return down;
	}

	public int getDiggsUp() {
		return up;
	}

	public Integer getReplyId() {
		return replyId;
	}

	public Integer getLevel() {
		return level;
	}

	public Integer getRoot() {
		return root;
	}

	@Override
	public String toString() {
		return "{Comment by " + getUser() + "}";
	}
}
