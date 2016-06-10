package exception;

public class WrongEmailException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WrongEmailException() {
		// TODO Auto-generated constructor stub
	}

	public WrongEmailException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public WrongEmailException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public WrongEmailException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
