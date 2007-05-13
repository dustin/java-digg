package net.spy.digg;

import org.apache.commons.httpclient.HttpClient;

/**
 * Provide http clients to the Digg client.
 */
public interface HttpClientFactory {

	/**
	 * Get an HttpClient instance for the Digg API to use.
	 */
	HttpClient getHttpClient();
}
