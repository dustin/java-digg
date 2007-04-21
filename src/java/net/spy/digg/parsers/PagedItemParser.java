package net.spy.digg.parsers;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * Base class for paged results.
 */
public abstract class PagedItemParser extends BaseParser {

	private long timestamp;
	private int total;
	private int offset;
	private int count;

	/**
	 * Extract the common paged stuff from the given document.
	 */
	protected void parseCommonFields(Document doc) {
		Node root=doc.getFirstChild();
		timestamp=1000*Long.parseLong(getAttr(root, "timestamp"));
		total=Integer.parseInt(getAttr(root, "total"));
		offset=Integer.parseInt(getAttr(root, "offset"));
		count=Integer.parseInt(getAttr(root, "count"));
	}

	/**
	 * Get the number of items returned.
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Get the offset from which these results began.
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 * Get the timestamp.
	 */
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * Get the total number of matches.
	 */
	public int getTotal() {
		return total;
	}

}