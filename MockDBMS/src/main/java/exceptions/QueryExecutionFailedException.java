package exceptions;

public class QueryExecutionFailedException extends Exception{

private String errorMessage;
	
	public QueryExecutionFailedException(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	
	
}
