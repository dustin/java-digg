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

	CommentImpl(Node n) {
		super(n);
		down=Integer.parseInt(BaseParser.getAttr(n, "down"));
		up=Integer.parseInt(BaseParser.getAttr(n, "up"));
		final String s=BaseParser.getAttr(n, "replyto");
		if(s != null && s.length() > 0) {
			replyId=new Integer(s);
		} else {
			replyId=null;
		}
		final Node c = n.getFirstChild();
		assert c.getNodeType() == Node.CDATA_SECTION_NODE
			: "Expected CDATA, got " + c;
		comment=c.getNodeValue();
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

	@Override
	public String toString() {
		return "{Comment by " + getUser() + "}";
	}
}
