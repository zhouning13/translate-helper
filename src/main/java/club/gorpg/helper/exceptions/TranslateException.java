package club.gorpg.helper.exceptions;

public class TranslateException extends Exception {
	private static final long serialVersionUID = 2372815388572107147L;
	private String message;

	public TranslateException() {
		super();
	}

	public TranslateException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
