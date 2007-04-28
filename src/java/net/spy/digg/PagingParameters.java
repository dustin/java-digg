package net.spy.digg;

/**
 * Base class for paging parameters.
 */
public abstract class PagingParameters {

	/**
	 * The minimum value for a count.
	 */
	public static final int MIN_COUNT = 0;
	/**
	 * The maximum value for a count.
	 */
	public static final int MAX_COUNT = 100;
	private String sort = null;
	private Integer count = 0;
	private Integer offset = 0;

	public PagingParameters() {
		super();
	}

	public Integer getCount() {
		return count;
	}

	/**
	 * Set the number of results to return.
	 * @param to an integer between MIN_COUNT and MAX_COUNT (inclusive).
	 */
	public void setCount(int to) {
		if(to < MIN_COUNT || to > MAX_COUNT) {
			throw new IllegalArgumentException("Count out of range.");
		}
		count = to;
	}

	/**
	 * Get the result offset.
	 */
	public Integer getOffset() {
		return offset;
	}

	/**
	 * Set the result offset.
	 * @param to a positive integer
	 */
	public void setOffset(int to) {
		if(to < 0) {
			throw new IllegalArgumentException("Offset must be positive");
		}
		offset = to;
	}

	/**
	 * Get the sort type.
	 */
	public String getSort() {
		return sort;
	}

	/**
	 * Set the sort type.
	 */
	public void setSort(String to) {
		sort = to;
	}

}