package net.spy.digg;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;

import net.spy.digg.parsers.BaseParser;
import net.spy.digg.parsers.ErrorsParser;
import net.spy.digg.parsers.UsersParser;

/**
 * Interface to Digg.
 */
public class Digg {

	private static final String BASE_URL="http://services.digg.com/";

	Map<Integer, String> errors=null;

	private String appKey;

	/**
	 * Construct a Digg interface with the given app key.
	 */
	public Digg(String k) {
		super();
		appKey=k;
	}

	/**
	 * Get all known errors.
	 */
	public Map<Integer, String> getErrors() throws DiggException {
		if(errors == null) {
			ErrorsParser ep=
				fetchParsed(ErrorsParser.class, "errors");
			errors=ep.getErrors();
		}
		return errors;
	}

	/**
	 * Get an error string for the given ID.
	 */
	public String getError(int id) throws DiggException {
		return getErrors().get(id);
	}

	private PagedItems<User> getUsers(String root, UserParameters p)
		throws DiggException {
		Map<String, String> m=new HashMap<String, String>();
		if(p != null) {
			m.put("sort", p.getSort());
			m.put("offset", String.valueOf(p.getOffset()));
			m.put("count", String.valueOf(p.getCount()));
		}
		UsersParser up=fetchParsed(UsersParser.class, root, m);
		return new PagedItems<User>(up);
	}

	/**
	 * Get users.
	 *
	 * @param p an optional UserParameters object indicating additional fetch
	 *    stuff
	 */
	public PagedItems<User> getUsers(UserParameters p) throws DiggException {
		return getUsers("users", p);
	}

	/**
	 * Get the friends of the given user.
	 */
	public PagedItems<User> getFriends(String u, UserParameters p)
		throws DiggException {
		return getUsers("user/" + u + "/friends", p);
	}

	/**
	 * Get the fans of the given user.
	 */
	public PagedItems<User> getFans(String u, UserParameters p)
		throws DiggException {
		return getUsers("user/" + u + "/fans", p);
	}

	/**
	 * Get the specific user.
	 */
	public User getUser(String name) throws DiggException {
		UsersParser up=fetchParsed(UsersParser.class, "user/" + name);
		Map<String, User> users = up.getUsers();
		assert users.size() < 2 : "Too many users returned.";
		User rv=null;
		if(!users.isEmpty()) {
			rv=users.values().iterator().next();
		}
		return rv;
	}

	private <T extends BaseParser> T fetchParsed(Class<T> cls, String u)
		throws DiggException {
		Map<String, String> m=new HashMap<String, String>();
		return fetchParsed(cls, u, m);
	}

	private <T extends BaseParser> T fetchParsed(Class<T> cls, String u,
			Map<String, String> params)
		throws DiggException {
		T rv=null;
		HttpClient client=new HttpClient();
		client.getParams().setParameter("http.useragent","SPY digg client");
		GetMethod method=new GetMethod(BASE_URL + u);
		params.put("appkey", appKey);
		method.setQueryString(makeQueryString(params));
		
		InputStream is=null;
		try {
			System.out.println("Executing " + method.getURI());
			int rc=client.executeMethod(method);
			if(rc != HttpStatus.SC_OK) {
				throw new DiggException("Bad HTTP status: " + rc);
			}
			Constructor<T> cons=cls.getConstructor(InputStream.class);
			is=method.getResponseBodyAsStream();
			rv=cons.newInstance(is);
		} catch (Exception e) {
			throw new DiggException(e);
		} finally {
			method.releaseConnection();
			if(is != null) {
				try {
					is.close();
				} catch (IOException e) {
					throw new DiggException(e);
				}
			}
		}
		return rv;
	}

	private NameValuePair[] makeQueryString(Map<String, String> params) {
		NameValuePair[] rv=new NameValuePair[params.size()];
		int i=0;
		for(Map.Entry<String, String> me : params.entrySet()) {
			rv[i++]=new NameValuePair(me.getKey(), me.getValue());
		}
		return rv;
	}

}
