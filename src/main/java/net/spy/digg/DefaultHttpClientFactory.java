package net.spy.digg;

import org.apache.commons.httpclient.HttpClient;

/**
 * Default client factory that just creates a new client each time.
 */
class DefaultHttpClientFactory implements HttpClientFactory {

	public HttpClient getHttpClient() {
		return new HttpClient();
	}

}
