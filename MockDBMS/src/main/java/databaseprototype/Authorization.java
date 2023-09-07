package databaseprototype;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;

import exceptions.LoginCredentialsAuthorizationException;

public class Authorization {

	/**
	 * Authorizes the user login credentials by matching the entered user name and password
	 * with the existing user data from the user credentials file.
	 *
	 * @param userName the user name entered by the user
	 * @param password the password entered by the user
	 * @return the user object of the logged in user if login credentials are authorized successfully
	 * @throws LoginCredentialsAuthorizationException if login credentials are not authorized due to incorrect user name or password
	 */
	public static User autorizeUserLoginCredentials(String userName,String password) throws LoginCredentialsAuthorizationException {
		File file = new File("src/main/resources/database/UserCredentials.txt");
		User loggedInUser=null;
		try {
			String hashedPass = passwordHashing(password);
			String[] data = IOOperations.readDataFromFile(file,userName,hashedPass);
			if(data!=null) {
				loggedInUser = new User(data[0].substring(1),data[1],data[2],data[3]);
				if(!loggedInUser.getPassword().equals(hashedPass)) {
					throw new LoginCredentialsAuthorizationException("Incorrect Password");
				}
			}
			else {
				throw new LoginCredentialsAuthorizationException("User Name not found");
			}
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return loggedInUser;
	}
	
	/**

	* This method takes a password as input and returns its hashed form using MD5 hashing algorithm.

	* @param password the password to be hashed

	* @return the hashed form of the password
	*/
	public static String passwordHashing(String password) {
		String hashedPass="";
		try {
			MessageDigest messageDigest =  MessageDigest.getInstance("MD5");	
			messageDigest.update(password.getBytes());
			byte[] result = messageDigest.digest();
			StringBuilder temp = new StringBuilder();
			for(byte b : result) {
				temp.append(String.format("%02x",b));
			}
			hashedPass = temp.toString();
		}catch(Exception e) {
			
		}
		return hashedPass;
	}
	
	
	
}
