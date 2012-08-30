package glaf.batch.exception.sys;


public class BatchIsLockedException extends SysException {

	public BatchIsLockedException() {
		super();
	}

	public BatchIsLockedException(String message, Throwable cause) {
		super(message, cause);
	}

	public BatchIsLockedException(String message) {
		super(message);
	}

	public BatchIsLockedException(Throwable cause) {
		super(cause);
	}

}
