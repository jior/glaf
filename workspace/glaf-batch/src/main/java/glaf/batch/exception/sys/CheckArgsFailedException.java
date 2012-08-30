package glaf.batch.exception.sys;


public class CheckArgsFailedException extends SysException {

	public CheckArgsFailedException() {
		super();
	}

	public CheckArgsFailedException(String message, Throwable cause) {
		super(message, cause);
	}

	public CheckArgsFailedException(String message) {
		super(message);
	}

	public CheckArgsFailedException(Throwable cause) {
		super(cause);
	}

}
