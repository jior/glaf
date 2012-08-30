/**
 * batch异常基类
 */
package glaf.batch.exception;

public class BatchException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BatchException() {
		super();
	}

	public BatchException(String message) {
		super(message);
	}

	public BatchException(String message, Throwable cause) {
		super(message, cause);
	}

	public BatchException(Throwable cause) {
		super(cause);
	}
}
