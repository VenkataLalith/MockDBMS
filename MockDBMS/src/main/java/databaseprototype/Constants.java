package databaseprototype;

public class Constants {

	public static final String USER_SIGNUP_SUCCESSFUL_MESSAEG = "User ID created successfully....";

	public static final String GO_BACK_TO_MAIN_MENU_MESSAGE = "To go back to main menu - enter 0";
	
	public static final String LOGIN_PAGE_HEADER_TEXT = "To login, enter your UserName and Password";
	
	public static final String SECURITY_QUESTION = "What is your favourite colour ?";
	
	public static final boolean USER_LOGIN_STATUS_FALSE = false;
	
	public static final boolean USER_LOGIN_STATUS_TRUE = true;
	
	public static final String NEW_LINE_STRING = "\n";
	
	public static final String MAIN_MENU_WELCOME_STRING = "Welcome User....Enter :"+"\n"+"1 - New User"+"\n"+"2 - Existing User";
	
	public static final String DB_CREATION_SUCCESS_MESSAGE="DataBase Created successfully";
	
	public static final String TABLE_CREATION_SUCCESS_MESSAGE="Table created successfully";
	
	public static final String VALUE_DATA_TYPE_MISMATCH_MESSAGE="Entered value does'nt match with column data type";
	
	public static final String COLUMN_NAME_MISMATCH_MESSAGE="Column Name mismatch with table's column info";
	
	public static final String DB_CREATION_EXCEPTION_MESSAGE= "Issue occured while creating database";
	
	public static final String DB_ALREADY_EXISTS_EXCEPTION_MESSAGE="Already a DB exists";
	
	public static final String USER_LOGIN_SUCCESS_MESSAGE = "User Logged in Successfully";
	
	public static final String MULTIFACTOR_AUTHENTICATION_FAILURE_MESSAGE ="Multifactor Authentication Failed.....recheck credentials and try again";

	public static final String CREATE_QUERY_BEGIN_REGEX= "CREATE[ ]+TABLE[ ]+[a-zA-Z0-9]+[ ]+\\(";
	
	public static final String CREATE_COLUMN_CHECKING_REGEX = "[a-zA-Z0-9]+ (INT|VARCHAR|DOUBLE|int|varchar|double)";

    public static final String CREATE_DATABASE_QUERY_PATTERN_REGEX = "CREATE DATABASE [a-zA-Z0-9]+;";
	
	public static final String INSERT_QUERY_COLUMN_NAME_MATCHING_REGEX = "[a-zA-Z0-9]+";
    
	public static final String INSERT_QUERY_BEGIN_REGEX ="(INSERT|insert) (INTO|into) [a-zA-Z0-9]+ \\(";
	
	public static final String INSERT_QUERY_MIDDLE_REGEX="\\) (VALUES|values) \\(";
	
	public static final String INSERT_QUERY_END_REGEX="\\);";
	
	public static final String INSERT_QUERY_EXCEPTION_COLUMN_VALUES_COUNT_MISMATCH_MESSAGE="Number of Values is not equal to number of column names";
	
	public static final String OPERATION_TYPE_CREATE="CREATE";
	
	public static final String OPERATION_TYPE_INSERT="INSERT";
	
	public static final String OPERATION_TYPE_SELECT ="SELECT" ;
	
	public static final String OPERATION_TYPE_UPDATE = "UPDATE";
	
	public static final String OPERATION_TYPE_DELETE ="DELETE" ;
	
	public static final String CONSTRAINT_WHERE="WHERE";
	
	public static final String DELETE_QUERY_REGEX ="DELETE FROM [a-zA-Z0-9]+ WHERE \\([a-zA-Z0-9]+(=|>|<|>=|<=)[a-zA-Z0-9]+\\);";
	
	public static final String UPDATE_QUERY_REGEX="UPDATE [a-zA-Z0-9]+ SET \\([a-zA-Z0-9]+=[a-zA-Z0-9]+\\) WHERE \\([a-zA-Z0-9]+(=|>|<|>=|<=)[a-zA-Z0-9]+\\);";
	
	public static final String SELECT_QUERY_BEGIN_REGEX="SELECT";
	
	public static final String SELECT_QUERY_COLUMN_NAME_MATCHING_REGEX="(\\*|[a-zA-Z0-9]+)";
	
	public static final String SELECT_QUERY_END_REGEX="FROM [a-zA-Z0-9]+;";
	
	public static final String SELECT_QUERY_WITH_WHERE_CONDITION_END_REGEX_QUERY ="FROM [a-zA-Z0-9]+ WHERE \\([a-zA-Z0-9]+(=|>|<|>=|<=)[a-zA-Z0-9]+\\);" ;
	
	public static final String PIPE_DELIMITER_REGEX="\\|\\|";
	
	public static final String SELECT_QUERY_EXECUTION_SUCCESS_MESSAGE= "Select query executed successfully";
	
	public static final String RECORD_UPDATE_EXECUTION_SUCCESS_MESSAGE="  records updated successfully..";
	
    public static final boolean BOOLEAN_VALUE_TRUE = true;
    
    public static final boolean BOOLEAN_VALUE_FALSE = false;
    
    public static final int DIGIT_ZERO=0;
    public static final int DIGIT_ONE=1;
	public static final int DIGIT_TWO=2;
	public static final int DIGIT_THREE=3;
	public static final int DIGIT_FOUR=4;
	public static final int DIGIT_FIVE=5;
	//Special Character Strings
	
	
    
}
