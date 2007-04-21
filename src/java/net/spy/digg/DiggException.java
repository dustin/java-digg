package net.spy.digg;

/**
 * Exceptions getting stuff from digg.
 */
public class DiggException extends Exception {

	public DiggException(String msg, Throwable t) {
		super(msg, t);
	}

	public DiggException(String msg) {
		super(msg);
	}

	public DiggException(Throwable t) {
		super(t);
	}

}
