package net.spy.digg.parsers;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * Paged item parser for items that also include date information.
 */
public abstract class TimePagedItemParser<T> extends PagedItemParser<T> {

	private long minDate;

	@Override
	protected void parseCommonFields(Document doc) {
		super.parseCommonFields(doc);
		final Node root=doc.getFirstChild();
		final String s = getAttr(root, "min_date");
		if(s != null) {
			minDate=1000*Long.parseLong(s);
		}
	}

	/**
	 * Get the minimum date represented by these results.
	 */
	public long getMinDate() {
		return minDate;
	}

}