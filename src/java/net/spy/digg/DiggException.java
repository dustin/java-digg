package net.spy.digg;

/**
 * Exceptions getting stuff from digg.
 */
public class DiggException extends Exception {

	private int errorId=-1;

	public DiggException(String msg, Throwable t) {
		super(msg, t);
	}

	public DiggException(String msg) {
		super(msg);
	}

	public DiggException(Throwable t) {
		super(t);
	}

	public DiggException(String msg, int eid) {
		super(msg + " (" + eid + ")");
		errorId=eid;
	}

	/**
	 * Get the error ID.
	 */
	public int getErrorId() {
		return errorId;
	}
}
