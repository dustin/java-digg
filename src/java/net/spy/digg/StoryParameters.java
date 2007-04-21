package net.spy.digg;

/**
 * Parameters for story requests.
 */
public class StoryParameters extends PagingParameters {

	private Long minSubmitDate;
	private Long maxSubmitDate;
	private Long minPromoteDate;
	private Long maxPromoteDate;
	private String domain;
	private String link;

	/**
	 * Get the domain parameter.
	 */
	public String getDomain() {
		return domain;
	}
	/**
	 * Set the domain parameter.
	 */
	public void setDomain(String to) {
		domain = to;
	}
	/**
	 * Get the link parameter.
	 */
	public String getLink() {
		return link;
	}
	/**
	 * Set the link parameter.
	 */
	public void setLink(String to) {
		link = to;
	}
	/**
	 * Get the max promotion date.
	 */
	public Long getMaxPromoteDate() {
		return maxPromoteDate;
	}
	/**
	 * Set the maximum promotion date.
	 */
	public void setMaxPromoteDate(Long to) {
		maxPromoteDate = to;
	}
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
	 * Get the minimum promotion date.
	 */
	public Long getMinPromoteDate() {
		return minPromoteDate;
	}
	/**
	 * Set the minimum promotion date.
	 */
	public void setMinPromoteDate(Long to) {
		minPromoteDate = to;
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
