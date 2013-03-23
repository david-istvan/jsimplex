package simplex.exceptions;

/**
 * Thrown in the case of an incorrectly formalized LP Program.
 * @author idavid
 *
 */
public class LPFormatException extends Exception {

	private static final long serialVersionUID = 4180013386245204064L;
	private String message;

	public LPFormatException() {
		this.message = "The problem is not formalized correctly.";
	}

	public LPFormatException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return this.message;
	}
}