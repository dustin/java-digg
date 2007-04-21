package net.spy.digg;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;

import net.spy.digg.parsers.BaseParser;
import net.spy.digg.parsers.ErrorsParser;
import net.spy.digg.parsers.EventsParser;
import net.spy.digg.parsers.TopicsParser;
import net.spy.digg.parsers.UsersParser;

/**
 * Interface to Digg.
 */
public class Digg {

	private static final String BASE_URL="http://services.digg.com/";

	private Map<Integer, String> errors=null;
	private Map<String, TopicContainer> containers=null;

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

	/**
	 * Get all topics and topic containers.
	 */
	public Map<String, TopicContainer> getTopics() throws DiggException {
		if(containers == null) {
			TopicsParser tp=fetchParsed(TopicsParser.class, "topics");
			containers=tp.getContainers();
		}
		return containers;
	}

	/**
	 * Get the named topic.
	 */
	public Topic getTopic(String top) throws DiggException {
		Topic rv=null;
		Map<String, TopicContainer> topics = getTopics();
		for(Map.Entry<String, TopicContainer> me : topics.entrySet()) {
			for(Topic t : me.getValue()) {
				if(t.getName().equals(top) || t.getShortName().equals(top)) {
					assert rv == null : "Found duplicate topic match";
					rv=t;
				}
			}
		}
		return rv;
	}

	private PagedItems<User> getUsers(String root, UserParameters p)
		throws DiggException {
		Map<String, String> m=new HashMap<String, String>();
		if(p != null) {
			applyPagingParams(p, m);
		}
		UsersParser up=fetchParsed(UsersParser.class, root, m);
		return new PagedItems<User>(up);
	}

	private void applyPagingParams(PagingParameters p, Map<String, String> m) {
		if(p.getSort() != null) {
			m.put("sort", p.getSort());
		}
		if(p.getOffset() != null) {
			m.put("offset", String.valueOf(p.getOffset()));
		}
		if(p.getCount() !=  null) {
			m.put("count", String.valueOf(p.getCount()));
		}
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

	private PagedItems<Event> getEvents(String root, EventParameters p)
		throws DiggException {
		Map<String, String> m=new HashMap<String, String>();
		if(p != null) {
			applyPagingParams(p, m);
			if(p.getMaxDate() != null) {
				m.put("max_date", String.valueOf(p.getMaxDate() / 1000));
			}
			if(p.getMinDate() != null) {
				m.put("min_date", String.valueOf(p.getMinDate() / 1000));
			}
		}
		EventsParser up=fetchParsed(EventsParser.class, root, m);
		return new PagedItems<Event>(up);
	}

	/**
	 * Get all digg events.
	 */
	public PagedItems<Event> getDiggs(EventParameters p) throws DiggException {
		return getEvents("stories/diggs", p);
	}

	/**
	 * Get digg events on popular stories.
	 */
	public PagedItems<Event> getPopularDiggs(EventParameters p)
		throws DiggException {
		return getEvents("stories/popular/diggs", p);
	}

	/**
	 * Get digg events on popular stories.
	 */
	public PagedItems<Event> getUpcomingDiggs(EventParameters p)
		throws DiggException {
		return getEvents("stories/upcoming/diggs", p);
	}

	/**
	 * Get digg events for the given story id.
	 */
	public PagedItems<Event> getStoryDiggs(int storyId, EventParameters p)
		throws DiggException {
		return getEvents("story/" + storyId + "/diggs", p);
	}

	/**
	 * Get digg events for the given story id.
	 */
	public PagedItems<Event> getStoryDiggs(Collection<Integer> stories,
			EventParameters p) throws DiggException {
		return getEvents("stories/" + join(",", stories) + "/diggs", p);
	}

	/**
	 * Get the diggs from the given user.
	 */
	public PagedItems<Event> getUserDiggs(String u, EventParameters p)
		throws DiggException {
		return getEvents("user/" + u + "/diggs", p);
	}

	/**
	 * Get the diggs from the given users.
	 */
	public PagedItems<Event> getUserDiggs(Collection<String> users,
			EventParameters p) throws DiggException {
		return getEvents("users/" + join(",", users) + "/diggs", p);
	}

	/**
	 * Get all comment events.
	 */
	public PagedItems<Event> getComments(EventParameters p)
		throws DiggException {
		// XXX:  This doesn't return events at all.
		return getEvents("stories/comments", p);
	}

	/**
	 * Get comments on popular stories.
	 */
	public PagedItems<Event> getPopularComments(EventParameters p)
		throws DiggException {
		// XXX:  Another comment variation.
		return getEvents("stories/popular/comments", p);
	}

	/**
	 * Get comments on upcoming stories.
	 */
	public PagedItems<Event> getUpcomingComments(EventParameters p)
		throws DiggException {
		// XXX:  Another comment variation.
		return getEvents("stories/upcoming/comments", p);
	}

	/**
	 * Get comments for the given stories.
	 */
	public PagedItems<Event> getComments(Collection<Integer> stories,
			EventParameters p) throws DiggException {
		// XXX:  Another comment variation
		return getEvents("stories/" + join(",", stories) + "/comments", p);
	}

	/**
	 * Get comments for the given story.
	 */
	public PagedItems<Event> getComments(int story, EventParameters p)
		throws DiggException {
		// XXX:  Another comment variation
		return getEvents("story/" + story + "/comments", p);
	}

	/**
	 * Get comments for the given user.
	 */
	public PagedItems<Event> getUserComments(String user, EventParameters p)
		throws DiggException {
		// XXX:  Another comment variation.
		return getEvents("user/" + user + "/comments", null);
	}

	/**
	 * Get comments for the given user.
	 */
	public PagedItems<Event> getUserComments(Collection<String> users,
			EventParameters p) throws DiggException {
		// XXX:  Another comment variation.
		return getEvents("users/" + join(",", users) + "/comments", null);
	}

	/**
	 * Get the replies to the given comment on the given story.
	 */
	public PagedItems<Event> getCommentReplies(int storyId, int commentId,
			EventParameters p) throws DiggException {
		// XXX:  More comment stuff
		return getEvents("story/" + storyId + "/comment/"
				+ commentId + "/replies", p);
	}

	private String join(String j, Collection<?> c) {
		boolean first=true;
		StringBuilder sb=new StringBuilder();
		for(Object o : c) {
			if(first) {
				first=false;
			} else {
				sb.append(j);
			}
			sb.append(String.valueOf(o));
		}
		return sb.toString();
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
