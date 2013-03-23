package simplex.exceptions;

/**
 * Thrown in the case of a syntax error in the input file.
 * @author idavid
 *
 */
public class SyntaxErrorException extends Exception {

	private static final long serialVersionUID = 1973314534087054758L;
	private String message;

	public SyntaxErrorException() {
		this.message = "Syntax error in the input file.";
	}

	public SyntaxErrorException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return this.message;
	}
}