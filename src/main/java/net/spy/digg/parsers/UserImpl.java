package net.spy.digg.parsers;

import java.io.Serializable;

import org.w3c.dom.Node;

import net.spy.digg.User;

/**
 * A Digg user.
 */
public class UserImpl implements User, Serializable {

	private final String name;
	private final String icon;
	private final long registered;
	private final int profileviews;

	public UserImpl(Node n) {
		super();
		name=BaseParser.getAttr(n, "name");
		icon=BaseParser.getAttr(n, "icon");
		long r = 0;
		int p = 0;
		try {
			r=1000*Long.parseLong(BaseParser.getAttr(n, "registered"));
			p=Integer.parseInt(BaseParser.getAttr(n, "profileviews"));
		} catch(NumberFormatException e) {
			// Problem parsing one...
		}
		registered = r;
		profileviews = p;
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
