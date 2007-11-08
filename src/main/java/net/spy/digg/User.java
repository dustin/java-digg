package net.spy.digg;

/**
 * A digg user.
 */
public interface User {

	/**
	 * Get the URL to this user's icon.
	 */
	String getIcon();

	/**
	 * Get the name of this user.
	 */
	String getName();

	/**
	 * Get the number of profile views for this user.
	 */
	int getProfileviews();

	/**
	 * Get the date this user registered.
	 */
	long getRegistered();

}