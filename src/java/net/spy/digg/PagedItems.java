package net.spy.digg;

import java.util.ArrayList;

import net.spy.digg.parsers.PagedItemParser;

/**
 * A collection of paged items.
 *
 * @param <T> the type of item.
 */
public class PagedItems<T> extends ArrayList<T> {

	private long timestamp;
	private int total;
	private int offset;
	private int count;

	PagedItems() {
		super();
	}

	PagedItems(PagedItemParser<T> p) {
		super(p.getItems());
		timestamp=p.getTimestamp();
		total=p.getTotal();
		offset=p.getOffset();
		count=p.getCount();
	}

	/**
	 * Get the number of items returned.
	 */
	public int getCount() {
		return count;
	}
	/**
	 * Get the offset from which this PagedItems began.
	 */
	public int getOffset() {
		return offset;
	}
	/**
	 * Get the timestamp at which the results were created.
	 */
	public long getTimestamp() {
		return timestamp;
	}
	/**
	 * Get the total number of items for this search.
	 */
	public int getTotal() {
		return total;
	}

	@Override
	public String toString() {
		return "{PagedItems total=" + total + " offset=" + offset + " "
			+ super.toString() + "}";
	}
}
