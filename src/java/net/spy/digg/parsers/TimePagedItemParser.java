package net.spy.digg.parsers;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * Paged item parser for items that also include date information.
 */
public abstract class TimePagedItemParser extends PagedItemParser {

	private long minDate;

	@Override
	protected void parseCommonFields(Document doc) {
		super.parseCommonFields(doc);
		Node root=doc.getFirstChild();
		minDate=1000*Long.parseLong(getAttr(root, "min_date"));
	}

	/**
	 * Get the minimum date represented by these results.
	 */
	public long getMinDate() {
		return minDate;
	}

}