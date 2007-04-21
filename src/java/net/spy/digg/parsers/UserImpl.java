package net.spy.digg.parsers;

import org.w3c.dom.Node;

import net.spy.digg.User;

/**
 * A Digg user.
 */
class UserImpl implements User {

	private String name;
	private String icon;
	private long registered;
	private int profileviews;

	public UserImpl(Node n) {
		super();
		name=BaseParser.getAttr(n, "name");
		icon=BaseParser.getAttr(n, "icon");
		registered=1000*Long.parseLong(BaseParser.getAttr(n, "registered"));
		profileviews=Integer.parseInt(BaseParser.getAttr(n, "profileviews"));
	}

	/* (non-Javadoc)
	 * @see net.spy.digg.User#getIcon()
	 */
	public String getIcon() {
		return icon;
	}

	/* (non-Javadoc)
	 * @see net.spy.digg.User#getName()
	 */
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see net.spy.digg.User#getProfileviews()
	 */
	public int getProfileviews() {
		return profileviews;
	}

	/* (non-Javadoc)
	 * @see net.spy.digg.User#getRegistered()
	 */
	public long getRegistered() {
		return registered;
	}

	@Override
	public String toString() {
		return "{User " + name + "}";
	}
}
