package glaf.batch.exception.bus;

import glaf.batch.exception.BatchException;

public class BusException extends BatchException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BusException() {
		super();
	}

	public BusException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusException(String message) {
		super(message);
	}

	public BusException(Throwable cause) {
		super(cause);
	}

}
