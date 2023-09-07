package exceptions;

public class UserSignupException extends Exception{

	private String errorMessage;
	
	public UserSignupException(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	
}
