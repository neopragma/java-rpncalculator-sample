package calc.sample;

public class WrongNumberOfArgumentsException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String message;
	public WrongNumberOfArgumentsException(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
}
