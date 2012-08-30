/**
 * 本类主要用于抛出邮件发送出错时的异常
 * MailSendException.java（类名）
 * 1.0.1.0（版本）
 * 作成者：ISC)yx
 * 作成时间：2008-07-25
 * 
 */
package baseSrc.common.mail;

/**
 * 
 * 本类主要用于抛出邮件发送出错时的异常
 * 
 */
public class MailSendException extends RuntimeException {
	static final long serialVersionUID = 1L;

	public MailSendException() {
		super();
	}

	public MailSendException(String message) {
		super(message);
	}

	public MailSendException(String message, Throwable cause) {
		super(message, cause);
	}

	public MailSendException(Throwable cause) {
		super(cause);
	}
}