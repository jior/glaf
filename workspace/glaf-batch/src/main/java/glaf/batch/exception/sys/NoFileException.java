package glaf.batch.exception.sys;


public class NoFileException  extends SysException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoFileException() {
		super();
	}

	public NoFileException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoFileException(String message) {
		super(message);
	}

	public NoFileException(Throwable cause) {
		super(cause);
	}
}
