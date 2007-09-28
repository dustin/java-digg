package net.spy.digg;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;

import net.spy.digg.parsers.BaseParser;
import net.spy.digg.parsers.ErrorParser;
import net.spy.digg.parsers.ErrorsParser;
import net.spy.digg.parsers.EventsParser;
import net.spy.digg.parsers.StoriesParser;
import net.spy.digg.parsers.TopicsParser;
import net.spy.digg.parsers.UsersParser;

/**
 * Interface to Digg.
 */
public class Digg {

	private static final String BASE_URL="http://services.digg.com/";

	private HttpClientFactory clientFactory=new DefaultHttpClientFactory();

	private Map<Integer, String> errors=null;
	private Map<String, TopicContainer> containers=null;

	private final String appKey;

	/**
	 * Construct a Digg interface with the given app key.
	 *
	 * @throws IllegalArgumentException if k is not a valid URL
	 */
	public Digg(String k) {
		super();
		try {
			final URI u=new URI(k);
			if(u.getScheme() == null) {
				throw new IllegalArgumentException("Invalid URI (no scheme): "
					+ k);
			}
		} catch(final URISyntaxException e) {
			throw new IllegalArgumentException("Invalid URI:  " + k, e);
		}
		appKey=k;
	}

	/**
	 * Set the http client factory that will be used to construct HttpClient
	 * instances for digg service requests.
	 */
	public void setHttpClientFactory(HttpClientFactory to) {
		if(to == null) {
			throw new NullPointerException("HttpClientFactory may not be null");
		}
		clientFactory=to;
	}

	/**
	 * Get all known errors.
	 */
	public Map<Integer, String> getErrors() throws DiggException {
		if(errors == null) {
			final ErrorsParser ep=
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
			final TopicsParser tp=fetchParsed(TopicsParser.class, "topics");
			containers=tp.getContainers();
		}
		return containers;
	}

	/**
	 * Get the named topic.
	 */
	public Topic getTopic(String top) throws DiggException {
		Topic rv=null;
		final Map<String, TopicContainer> topics = getTopics();
		for(final Map.Entry<String, TopicContainer> me : topics.entrySet()) {
			for(final Topic t : me.getValue()) {
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
		final Map<String, String> m=new HashMap<String, String>();
		if(p != null) {
			applyPagingParams(p, m);
		}
		final UsersParser up=fetchParsed(UsersParser.class, root, m);
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
		final UsersParser up=fetchParsed(UsersParser.class, "user/" + name);
		final Map<String, User> users = up.getUsers();
		assert users.size() < 2 : "Too many users returned.";
		User rv=null;
		if(!users.isEmpty()) {
			rv=users.values().iterator().next();
		}
		return rv;
	}


	private void applyDateParam(Map<String, String> m, String k, Long d) {
		if(d != null) {
			m.put(k, String.valueOf(d/1000));
		}
	}

	private PagedItems<Event> getEvents(String root, EventParameters p)
		throws DiggException {
		final Map<String, String> m=new HashMap<String, String>();
		if(p != null) {
			applyPagingParams(p, m);
			applyDateParam(m, "max_date", p.getMaxDate());
			applyDateParam(m, "min_date", p.getMinDate());
		}
		final EventsParser up=fetchParsed(EventsParser.class, root, m);
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

	private PagedItems<Comment> getComments(String root, EventParameters p)
		throws DiggException {
		final PagedItems<Comment> rv=new PagedItems<Comment>();
		for(final Event e : getEvents(root, p)) {
			assert e instanceof Comment : "Expected a comment, got " + e;
			rv.add((Comment)e);
		}
		return rv;
	}

	/**
	 * Get all comment events.
	 */
	public PagedItems<Comment> getComments(EventParameters p)
		throws DiggException {
		return getComments("stories/comments", p);
	}

	/**
	 * Get comments on popular stories.
	 */
	public PagedItems<Comment> getPopularComments(EventParameters p)
		throws DiggException {
		return getComments("stories/popular/comments", p);
	}

	/**
	 * Get comments on upcoming stories.
	 */
	public PagedItems<Comment> getUpcomingComments(EventParameters p)
		throws DiggException {
		return getComments("stories/upcoming/comments", p);
	}

	/**
	 * Get comments for the given stories.
	 */
	public PagedItems<Comment> getComments(Collection<Integer> stories,
			EventParameters p) throws DiggException {
		return getComments("stories/" + join(",", stories) + "/comments", p);
	}

	/**
	 * Get comments for the given story.
	 */
	public PagedItems<Comment> getComments(int story, EventParameters p)
		throws DiggException {
		return getComments("story/" + story + "/comments", p);
	}

	/**
	 * Get comments for the given user.
	 */
	public PagedItems<Comment> getUserComments(String user, EventParameters p)
		throws DiggException {
		return getComments("user/" + user + "/comments", p);
	}

	/**
	 * Get comments for the given user.
	 */
	public PagedItems<Comment> getUserComments(Collection<String> users,
			EventParameters p) throws DiggException {
		return getComments("users/" + join(",", users) + "/comments", p);
	}

	/**
	 * Get the replies to the given comment on the given story.
	 */
	public PagedItems<Comment> getCommentReplies(int storyId, int commentId,
			EventParameters p) throws DiggException {
		return getComments("story/" + storyId + "/comment/"
				+ commentId + "/replies", p);
	}

	private PagedItems<Story> getStories(String root, StoryParameters p)
		throws DiggException {
		final Map<String, String> m=new HashMap<String, String>();
		if(p != null) {
			applyPagingParams(p, m);
			applyDateParam(m, "max_promote_date", p.getMaxPromoteDate());
			applyDateParam(m, "min_promote_date", p.getMinPromoteDate());
			applyDateParam(m, "max_submit_date", p.getMaxSubmitDate());
			applyDateParam(m, "min_submit_date", p.getMinSubmitDate());
			if(p.getDomain() != null) {
				m.put("domain", p.getDomain());
			}
			if(p.getLink() != null) {
				m.put("link", p.getLink());
			}
		}
		final StoriesParser up=fetchParsed(StoriesParser.class, root, m);
		return new PagedItems<Story>(up);
	}

	/**
	 * Get all stories.
	 */
	public PagedItems<Story> getStories(StoryParameters p)
		throws DiggException {
		return getStories("stories", p);
	}

	/**
	 * Get the popular stories.
	 */
	public PagedItems<Story> getPopularStories(StoryParameters p)
		throws DiggException {
		return getStories("stories/popular", p);
	}

	/**
	 * Get the upcoming stories.
	 */
	public PagedItems<Story> getUpcomingStories(StoryParameters p)
		throws DiggException {
		return getStories("stories/upcoming", p);
	}

	/**
	 * Get the stories in the given container.
	 */
	public PagedItems<Story> getStories(TopicContainer container,
			StoryParameters p) throws DiggException {
		return getStories("stories/container/" + container.getShortName(), p);
	}

	/**
	 * Get the popular stories in the given container.
	 */
	public PagedItems<Story> getPopularStories(TopicContainer container,
			StoryParameters p) throws DiggException {
		return getStories("stories/container/" + container.getShortName()
				+ "/popular", p);
	}

	/**
	 * Get the upcoming stories in the given container.
	 */
	public PagedItems<Story> getUpcomingStories(TopicContainer container,
			StoryParameters p) throws DiggException {
		return getStories("stories/container/" + container.getShortName()
				+ "/upcoming", p);
	}

	/**
	 * Get all of the stories within the given topic.
	 */
	public PagedItems<Story> getStories(Topic topic, StoryParameters p)
		throws DiggException {
		return getStories("stories/topic/" + topic.getShortName(), p);
	}

	/**
	 * Get all of the popular stories within the given topic.
	 */
	public PagedItems<Story> getPopularStories(Topic topic, StoryParameters p)
		throws DiggException {
		return getStories("stories/topic/" + topic.getShortName()
				+ "/popular", p);
	}

	/**
	 * Get all of the upcoming stories within the given topic.
	 */
	public PagedItems<Story> getUpcomingStories(Topic topic, StoryParameters p)
		throws DiggException {
		return getStories("stories/topic/" + topic.getShortName()
				+ "/upcoming", p);
	}

	/**
	 * Get the specified stories.
	 */
	public PagedItems<Story> getStories(Collection<Integer> ids,
			StoryParameters p) throws DiggException {
		return getStories("stories/" + join(",", ids), p);
	}

	/**
	 * Get the story with the given id.
	 */
	public Story getStory(int id) throws DiggException {
		final PagedItems<Story> c=getStories("story/" + id, null);
		assert c.size() < 2 : "Too many results for " + id;
		Story rv=null;
		if(!c.isEmpty()) {
			rv=c.iterator().next();
		}
		return rv;
	}

	/**
	 * Get the story with the given clean URL.
	 */
	public Story getStory(String cleanUrl) throws DiggException {
		final PagedItems<Story> c=getStories("story/" + cleanUrl, null);
		assert c.size() < 2 : "Too many results for " + cleanUrl;
		Story rv=null;
		if(!c.isEmpty()) {
			rv=c.iterator().next();
		}
		return rv;
	}

	/**
	 * Get the stories submitted by the given user.
	 */
	public PagedItems<Story> getUserStories(String u, StoryParameters p)
		throws DiggException {
		return getStories("user/" + u + "/submissions", p);
	}

	private String join(String j, Collection<?> c) {
		boolean first=true;
		final StringBuilder sb=new StringBuilder();
		for(final Object o : c) {
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
		final Map<String, String> m=new HashMap<String, String>();
		return fetchParsed(cls, u, m);
	}

	private <T extends BaseParser> T fetchParsed(Class<T> cls, String u,
			Map<String, String> params)
		throws DiggException {
		T rv=null;
		final HttpClient client = clientFactory.getHttpClient();
		client.getParams().setParameter("http.useragent","SPY digg client");
		final GetMethod method=new GetMethod(BASE_URL + u);
		params.put("appkey", appKey);
		method.setQueryString(makeQueryString(params));

		InputStream is=null;
		try {
			System.out.println("Executing " + method.getURI());
			final int rc=client.executeMethod(method);
			is=method.getResponseBodyAsStream();
			if(rc != HttpStatus.SC_OK) {
				final ErrorParser ep=new ErrorParser();
				ep.parse(is);
				throw new DiggException(ep.getErrorMessage(),
					ep.getErrorId());
			}
			rv=cls.newInstance();
			rv.parse(is);
		} catch(final DiggException e) {
			throw e;
		} catch (final Exception e) {
			throw new DiggException(e);
		} finally {
			method.releaseConnection();
			if(is != null) {
				try {
					is.close();
				} catch (final IOException e) {
					throw new DiggException(e);
				}
			}
		}
		return rv;
	}

	private NameValuePair[] makeQueryString(Map<String, String> params) {
		final NameValuePair[] rv=new NameValuePair[params.size()];
		int i=0;
		for(final Map.Entry<String, String> me : params.entrySet()) {
			rv[i++]=new NameValuePair(me.getKey(), me.getValue());
		}
		return rv;
	}

	@Override
	public String toString() {
		return "{Digg app=" + appKey + "}";
	}
}
