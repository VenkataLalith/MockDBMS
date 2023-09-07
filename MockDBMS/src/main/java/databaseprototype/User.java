package databaseprototype;

import java.io.IOException;

public class User {
	private String userName;
	private String password;
	private String securityQuestionAnswer;
	private String dBName;
	

	public User(String userName, String password, String securityQuestionAnswer, String dBName) {
		super();
		this.userName = userName;
		this.password = password;
		this.securityQuestionAnswer = securityQuestionAnswer;
		this.dBName = dBName;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSecurityQuestionAnswer() {
		return securityQuestionAnswer;
	}
	public void setSecurityQuestionAnswer(String securityQuestionAnswer) {
		this.securityQuestionAnswer = securityQuestionAnswer;
	}
	public String getdBName() {
		return dBName;
	}
	public void setdBName(String dBName) {
		this.dBName = dBName;
	}

	public void updateUserDB(String dbName) throws IOException {
		// TODO Auto-generated method stub
		IOOperations.overWriteDataInFile("src/main/resources/database/UserCredentials.txt", this.userName, dbName);
		
	}
	
}
