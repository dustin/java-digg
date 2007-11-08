package net.spy.digg;

import junit.framework.TestCase;

/**
 * Tests of the Digg interface itself.
 */
public class DiggTest extends TestCase {

	public void testAssertions() throws Exception {
		try {
			assert false : "Assertions are enabled.";
			fail("Assertions are not enabled.");
		} catch(AssertionError e) {
			assertEquals("Assertions are enabled.", e.getMessage());
		}
	}

	public void testGoodConstruction() throws Exception {
		new Digg("http://bleu.west.spy.net/~dustin/?unused=%20true");
	}

	public void testGoodConstruction2() throws Exception {
		new Digg("urn:spy.net:this%20is%20a%20test");
	}

	public void testBadConstruction() throws Exception {
		try {
			Digg d=new Digg("noturi");
			fail("Should've rejected bad URL, but gave me " + d);
		} catch(IllegalArgumentException e) {
			assertEquals("Invalid URI (no scheme): noturi", e.getMessage());
		}
	}
}
