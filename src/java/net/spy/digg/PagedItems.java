package net.spy.digg;

import java.util.ArrayList;
import java.util.Collection;

import net.spy.digg.parsers.PagedItemParser;

/**
 * A collection of paged items.
 *
 * @param <T> the type of item.
 */
public class PagedItems<T> extends ArrayList<T> {

	private final long timestamp;
	private final int total;
	private final int offset;
	private final int count;

	PagedItems(PagedItemParser<T> p) {
		this(p.getItems(), p.getTimestamp(), p.getTotal(), p.getOffset(),
			p.getCount());
	}

	PagedItems(Collection<T> c) {
		this(c, 0, 0, 0, 0);
	}

	PagedItems(Collection<T> c, long ts, int t, int o, int cnt) {
		super(c);
		timestamp=ts;
		total=t;
		offset=o;
		count=cnt;
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
