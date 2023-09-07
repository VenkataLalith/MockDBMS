package exceptions;

public class LoginCredentialsAuthorizationException extends Exception{

	private String errorMessage;
	
	public LoginCredentialsAuthorizationException(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	
}
