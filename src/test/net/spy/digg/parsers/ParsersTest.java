package net.spy.digg.parsers;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Constructor;

import junit.framework.TestCase;

import net.spy.digg.Event;
import net.spy.digg.Story;
import net.spy.digg.Topic;
import net.spy.digg.TopicContainer;
import net.spy.digg.User;

public class ParsersTest extends TestCase {

	/**
	 * Get the sample file with the given name.
	 */
	private String getSampleFile(String path) {
		String basedir=System.getProperty("basedir");
		assert basedir != null : "No basedir";
		return basedir + "/src/test/data/" + path;
	}

	/**
	 * Parse a file with the given parser and return an instance of it.
	 */
	private <T> T doParse(String fn, Class<T> parser) throws Exception {
		FileInputStream fis=new FileInputStream(getSampleFile(fn));
		try {
			Constructor<T> cons = parser.getConstructor(InputStream.class);
			T rv=cons.newInstance(fis);
			return rv;
		} finally {
			fis.close();
		}
	}

	public void testErrorsWithSample() throws Exception {
		ErrorsParser ep=doParse("errors.xml", ErrorsParser.class);
		assertEquals(30, ep.getErrors().size());
		assertEquals("Unknown container or topic", ep.getErrors().get(1014));
	}

	public void testTopicsParser() throws Exception {
		TopicsParser tp=doParse("topics.xml", TopicsParser.class);
		assertEquals(7, tp.getContainers().size());
		assertEquals(4, tp.getContainers().get("science").size());
		Topic t=tp.getContainers().get("science").iterator().next();

		assertEquals("Space", t.getName());
		assertEquals("space", t.getShortName());
		assertEquals("{Topic name=Space}", String.valueOf(t));
	}

	public void testEventsParser() throws Exception {
		EventsParser ep=doParse("events.xml", EventsParser.class);
		assertEquals(10, ep.getItems().size());
		assertEquals(1176998174000L, ep.getTimestamp());
		assertEquals(1176760800000L, ep.getMinDate());
		assertEquals(10, ep.getCount());
		assertEquals(19, ep.getTotal());
		assertEquals(0, ep.getOffset());

		Event e=ep.getItems().iterator().next();
		assertEquals("{Event id=65657478, sid=1776206, "
				+ "user=kevinrose, status=upcoming}", String.valueOf(e));
		assertEquals(65657478, e.getEventId());
		assertEquals(1776206, e.getStoryId());
		assertEquals("kevinrose", e.getUser());
		assertEquals("upcoming", e.getStatus());
		assertEquals(1176935035000L, e.getTimestamp());
	}

	public void testUsersParser() throws Exception {
		UsersParser up=doParse("users.xml", UsersParser.class);
		assertEquals(1, up.getUsers().size());
		assertEquals(1176998598000L, up.getTimestamp());
		assertEquals(1, up.getCount());
		assertEquals(1, up.getTotal());
		assertEquals(0, up.getOffset());

		User u=up.getUsers().get("sbwms");
		assertNotNull(u);
		assertEquals("sbwms", u.getName());
		assertEquals("http://digg.com/img/user-large/user-default.png",
				u.getIcon());
		assertEquals(1135702996000L, u.getRegistered());
		assertEquals(3104, u.getProfileviews());

		assertEquals("{User sbwms}", String.valueOf(u));
	}

	public void testStoriesParsers() throws Exception {
		StoriesParser sp=doParse("stories.xml", StoriesParser.class);
		assertEquals(3, sp.getItems().size());
		Story s=sp.getItems().iterator().next();

		// Validate the parsed user
		User u=s.getUser();
		assertEquals("leoinc", u.getName());
		assertEquals("http://digg.com/img/user-large/user-default.png",
				u.getIcon());
		assertEquals(1160475488000L, u.getRegistered());
		assertEquals(265, u.getProfileviews());

		// Validate the topic and stuff
		Topic t=s.getTopic();
		TopicContainer tc=s.getContainer();
		assertTrue(tc.contains(t));
		assertEquals("Apple", t.getName());
		assertEquals("Technology", tc.getName());
		assertEquals(tc.getShortName(), t.getContainerName());

		// And the rest of the fields within the story.
		assertEquals(1806025, s.getId());
		assertEquals("http://leowebpicks.blogspot.com/2007/04/"
				+ "1passwd-ultimate-password-manager.html", s.getLink());
		assertEquals("http://digg.com/apple/Win_A_Free_Copy_of_1Passwd",
				s.getDiggLink());
		assertEquals(1176997081000L, s.getSubmittedTimestamp());
		assertEquals(1, s.getDiggs());
		assertEquals(0, s.getComments());
		assertEquals("upcoming", s.getStatus());
		assertEquals("Win A Free Copy of 1Passwd", s.getTitle());
		assertEquals("Manage all of your passwords with ease-enter to win a"
				+ " free copy of 1Passwd. Contest Ends April 30th.",
				s.getDescription());

		assertEquals("{Story ``Win A Free Copy of 1Passwd''}",
				String.valueOf(s));
	}
}
