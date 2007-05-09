package net.spy.digg;

import java.net.MalformedURLException;

import junit.framework.TestCase;

/**
 * Tests of the Digg interface itself.
 */
public class DiggTest extends TestCase {

	public void testGoodConstruction() throws Exception {
		new Digg("http://bleu.west.spy.net/~dustin/?unused=%20true");
	}

	public void testBadConstruction() throws Exception {
		try {
			Digg d=new Digg("noturl");
			fail("Should've rejected bad URL, but gave me " + d);
		} catch(IllegalArgumentException e) {
			assertSame(MalformedURLException.class, e.getCause().getClass());
		}
	}
}
