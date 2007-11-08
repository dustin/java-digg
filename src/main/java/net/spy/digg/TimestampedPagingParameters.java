package net.spy.digg;

/**
 * Abstract paging parameters for items that have time ranges by submission.
 */
abstract class TimestampedPagingParameters extends PagingParameters {

	private Long minSubmitDate;
	private Long maxSubmitDate;

	/**
	 * Get the maximum submission date.
	 */
	public Long getMaxSubmitDate() {
		return maxSubmitDate;
	}

	/**
	 * Set the maximum submission date.
	 */
	public void setMaxSubmitDate(Long to) {
		maxSubmitDate = to;
	}

	/**
	 * Get the minimum submission date.
	 */
	public Long getMinSubmitDate() {
		return minSubmitDate;
	}

	/**
	 * Set the minimum submission date.
	 */
	public void setMinSubmitDate(Long to) {
		minSubmitDate = to;
	}

}