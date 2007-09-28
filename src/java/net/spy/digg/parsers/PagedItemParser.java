package net.spy.digg.parsers;

import java.util.ArrayList;
import java.util.Collection;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * Base class for paged results.
 */
public abstract class PagedItemParser<T> extends BaseParser {

	private long timestamp;
	private int total;
	private int offset;
	private int count;

	private final Collection<T> items=new ArrayList<T>();

	/**
	 * Extract the common paged stuff from the given document.
	 */
	protected void parseCommonFields(Document doc) {
		final Node root=doc.getFirstChild();
		timestamp=1000*Long.parseLong(getAttr(root, "timestamp"));
		total=Integer.parseInt(getAttr(root, "total"));
		offset=Integer.parseInt(getAttr(root, "offset"));
		count=Integer.parseInt(getAttr(root, "count"));
	}

	/**
	 * Add an item.
	 */
	protected void addItem(T item) {
		items.add(item);
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

	/**
	 * Get the items.
	 */
	public Collection<T> getItems() {
		return items;
	}
}