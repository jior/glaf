package glaf.batch.exception.sys;


public class LockBatchFailedException extends SysException {

	public LockBatchFailedException() {
		super();
	}

	public LockBatchFailedException(String message, Throwable cause) {
		super(message, cause);
	}

	public LockBatchFailedException(String message) {
		super(message);
	}

	public LockBatchFailedException(Throwable cause) {
		super(cause);
	}

}
