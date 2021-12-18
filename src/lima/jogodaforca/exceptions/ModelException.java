package lima.jogodaforca.exceptions;

public class ModelException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3161055991136441238L;

	public ModelException() {
		super();
	}

	public ModelException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ModelException(String message, Throwable cause) {
		super(message, cause);
	}

	public ModelException(String message) {
		super(message);
	}

	public ModelException(Throwable cause) {
		super(cause);
	}

	
}
