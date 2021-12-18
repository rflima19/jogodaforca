package lima.jogodaforca.exceptions;

public class ViewException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7901295728220868266L;

	public ViewException() {
		super();
	}

	public ViewException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ViewException(String message, Throwable cause) {
		super(message, cause);
	}

	public ViewException(String message) {
		super(message);
	}

	public ViewException(Throwable cause) {
		super(cause);
	}

}
