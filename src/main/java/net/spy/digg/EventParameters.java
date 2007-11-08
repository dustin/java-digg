package net.spy.digg;

/**
 * Parameters for event queries.
 */
public class EventParameters extends PagingParameters {

	private Long minDate;
	private Long maxDate;

	/**
	 * Get the maximum timestamp.
	 */
	public Long getMaxDate() {
		return maxDate;
	}

	/**
	 * Set the maximum timestamp.
	 */
	public void setMaxDate(Long to) {
		maxDate = to;
	}

	/**
	 * Get the minimum timestamp.
	 */
	public Long getMinDate() {
		return minDate;
	}

	/**
	 * Set the minimum timestamp.
	 */
	public void setMinDate(Long to) {
		minDate = to;
	}

}
