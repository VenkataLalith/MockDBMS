package databaseprototype;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Pattern;

import exceptions.LoginCredentialsAuthorizationException;
import exceptions.QueryExecutionFailedException;
import exceptions.UserSignupException;

public class DriverClass {

	public static BufferedReader userInputReader;
	public static int userResponseInMainMenu;
	public static User dbmsUser;
	public static Logger log;
	public static boolean isUserLoggedIn = Constants.USER_LOGIN_STATUS_FALSE;
	public static Query query;
	
	/**

	* Displays the main menu to the user and takes input from the user to navigate to the appropriate page.
	* @throws IOException If an I/O error occurs while reading user input.
	* @throws LoginCredentialsAuthorizationException If the login credentials entered by the user are incorrect.
	* @throws UserSignupException If there is an error while signing up the user.
	*/
	public static void displayMainMenu() throws IOException, LoginCredentialsAuthorizationException, UserSignupException {
		System.out.println(Constants.MAIN_MENU_WELCOME_STRING);
		String userInput = userInputReader.readLine();
		int userInputValue =  Integer.parseInt(userInput);
		if(userInputValue == Constants.DIGIT_ONE) {
			displaySignupPage();
		}else if(userInputValue == Constants.DIGIT_TWO) {
			displayLoginMenu();
		}else {
			throw new NumberFormatException();
		}
	}
	
	/**

	* Displays the login menu, asks for user credentials, and logs the user in if credentials are correct.
	* If user enters '0', the main menu is displayed.
	* @throws IOException If an I/O error occurs while reading user input.
	* @throws LoginCredentialsAuthorizationException If the user enters invalid login credentials.
	* @throws UserSignupException If there is an error during the user signup process.
	*/

	public static void displayLoginMenu() throws IOException, LoginCredentialsAuthorizationException, UserSignupException {
		try {
			System.out.println(Constants.GO_BACK_TO_MAIN_MENU_MESSAGE);
			System.out.println(Constants.LOGIN_PAGE_HEADER_TEXT);
			String inputLine1 = userInputReader.readLine();
			String inputLine2="";
			String inputLine3="";
			User loggedUser=null;
			if(inputLine1.equals("0")) {
				displayMainMenu();
			}else {
				inputLine2 = userInputReader.readLine();
				loggedUser = Authorization.autorizeUserLoginCredentials(inputLine1, inputLine2);
				if(loggedUser!=null) {
					System.out.println(Constants.SECURITY_QUESTION);
					inputLine3 = userInputReader.readLine();
					if(inputLine3.equals(loggedUser.getSecurityQuestionAnswer())) {
						System.out.println("User Logged in successfully");
						loggedUser.setdBName(loggedUser.getdBName().substring(0,loggedUser.getdBName().length()-1));
						dbmsUser = loggedUser;
						isUserLoggedIn = Constants.USER_LOGIN_STATUS_TRUE;
					    log = new Logger(dbmsUser);
					    log.createLogFile();
					    log.logInfo(Constants.USER_LOGIN_SUCCESS_MESSAGE);
					}
					else {
						log.logInfo(Constants.MULTIFACTOR_AUTHENTICATION_FAILURE_MESSAGE);
						throw new LoginCredentialsAuthorizationException(Constants.MULTIFACTOR_AUTHENTICATION_FAILURE_MESSAGE);
						
					}
				}
			}
			
		}catch(LoginCredentialsAuthorizationException e) {
			System.out.println(e.getErrorMessage());
			displayLoginMenu();
		}
	}
	
	/**

	* Displays the login menu to the user and handles the user input for login.
	* @throws IOException if there is an error reading user input.
	* @throws LoginCredentialsAuthorizationException if the user credentials cannot be authorized.
	* @throws UserSignupException if there is an error with user signup.
	*/
	public static void createUserDirectory(User user) {
		String rootPath = "src/main/resources/database/"+user.getUserName();
		String logsPath= rootPath+"/logs";
		File userDirectory = new File(rootPath);
		File userLogsDirectory = new File(logsPath);
		if(!userDirectory.exists()) {
			 userDirectory.mkdirs();
			 userLogsDirectory.mkdirs();
		}
	}
	
	/**

	* Displays the user signup page and creates a new user account if the user provides valid credentials.
	* @throws IOException If an I/O error occurs while reading user input.
	* @throws UserSignupException If the user provides invalid signup credentials.
	* @throws LoginCredentialsAuthorizationException If an error occurs while authorizing user credentials.
	* @return Nothing.
	*/
	public static void displaySignupPage() throws IOException, UserSignupException, LoginCredentialsAuthorizationException {
		try {
			User newUser;
			System.out.println("Please enter your user name");
			String line1 = userInputReader.readLine();
			System.out.println("Please enter your password");
			String line2 = userInputReader.readLine();
			System.out.println("Re-enter your password...");
			String line3 = userInputReader.readLine();
			String line4="";
			if(!line2.equals(line3)) {
				throw new UserSignupException("Re-entered password does not match with actual password");
			}else {
				String dummy = Authorization.passwordHashing(line2);
				String[] existingData = IOOperations.readDataFromFile(new File("src/main/resources/database/UserCredentials.txt"), line1, dummy);
				if(existingData==null) {
					System.out.println("Multi-factor Authentication :Provide answer to the follwoing security question");
					System.out.println("What is your favourite colour ?");
					line4 = userInputReader.readLine();
					newUser = new User(line1,dummy,line4,null);
					IOOperations.writeDataIntoFile(newUser);
					createUserDirectory(newUser);
					System.out.println(Constants.USER_SIGNUP_SUCCESSFUL_MESSAEG);
					displayLoginMenu();
				}
				else {
					
					throw new UserSignupException("User name already exists.....Try another");
				}
			}
		}catch(UserSignupException e) {
			System.out.println(e.getErrorMessage());
			displaySignupPage();
		}
	}
	
	public static void displayPostLoginPage() throws NumberFormatException, IOException, QueryExecutionFailedException {
		while(isUserLoggedIn) {
			System.out.println("Enter : "+"\n"+"1 - Execute a query"+"\n"+"2 - Logout");
			int userInput = Integer.parseInt(userInputReader.readLine());
			if(userInput ==1) {
				userQueryExecutionPage();
			}else if(userInput==2) {
				log.closeLogFile();
				isUserLoggedIn = false;
				dbmsUser = null;
			}else {
				
			}
		}
	}
	
	/**

	* Displays the post-login page where the user can choose to execute a query or logout.

	* @throws NumberFormatException if the user input is not a valid number.

	* @throws IOException if there is an error reading user input.

	* @throws QueryExecutionFailedException if there is an error executing the query.
	*/
	public static void userQueryExecutionPage() throws IOException, QueryExecutionFailedException {
		try {
			System.out.println("Enter the query");
			String userInput = userInputReader.readLine();
			query.setQuery(userInput);
			if(query.compileQueryString()) {
				query.executeQuery(dbmsUser, log);
				displayPostLoginPage();
				
			}else {
				throw new QueryExecutionFailedException("Syntax incorrect");
			}
		}catch(QueryExecutionFailedException e) {
			System.out.println(e.getErrorMessage());
			log.logInfo(e.getErrorMessage());
			displayPostLoginPage();
		}
	}
	
	/**

	* The main method of the database management system.
	* @param args The arguments passed to the main method.
	* @throws IOException Thrown if there is an error in input/output.
	* @throws QueryExecutionFailedException Thrown if there is an error executing a query.
	* @throws LoginCredentialsAuthorizationException Thrown if there is an error with login credentials.
	* @throws UserSignupException Thrown if there is an error with user signup.
	* @return Nothing.
	*/
	public static void main(String args[]) throws IOException, QueryExecutionFailedException, LoginCredentialsAuthorizationException, UserSignupException {
		try {
			query = new Query();
			userInputReader = new BufferedReader(new InputStreamReader(System.in));
			displayMainMenu();
			displayPostLoginPage();	     
		}
		catch(NumberFormatException e) {
			System.out.println("Enter valid input......expected input 1/2");
			displayMainMenu();
		}
		catch(Exception e) {
			System.out.println(e);
		}
		finally {
			userInputReader.close();
		}		
	}
}
