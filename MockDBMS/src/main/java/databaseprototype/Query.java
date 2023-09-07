package databaseprototype;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import exceptions.QueryExecutionFailedException;

public class Query {
	private String query;
	
	/**
	 * Validates whether a SQL CREATE TABLE query is valid or not.
	 *
	 * @return true if the query is valid, false otherwise
	 */
	private boolean isCreateTableQueryValid() {
		String queryBeginningRegex = Constants.CREATE_QUERY_BEGIN_REGEX;
		String columnCheckingRegex = Constants.CREATE_COLUMN_CHECKING_REGEX;
		String inputQueryBeginning = query.substring(0,query.indexOf("(")+1);
		String columnNames = query.substring(query.indexOf("(")+1,query.length()-2);
		String[] columnNamesDeclaration = columnNames.split(",");
		boolean vals=Constants.BOOLEAN_VALUE_TRUE;
		for(String columnType : columnNamesDeclaration) {
			if(!((Pattern.matches(columnCheckingRegex, columnType)))){
				vals =  Constants.BOOLEAN_VALUE_FALSE;//false;
				break;
		}
		}
		return ((Pattern.matches(queryBeginningRegex, inputQueryBeginning)) && vals);
	}
	
	/**
	 * Validates whether a SQL CREATE DATABASE query is valid or not.
	 *
	 * @return true if the query is valid, false otherwise
	 */
	private boolean isCreateDatabaseQueryValid() {
		return Pattern.matches(Constants.CREATE_DATABASE_QUERY_PATTERN_REGEX, query);
	}
	
	/**

	Checks if the insert query is valid or not.
	@return boolean indicating if the insert query is valid or not.
	@throws QueryExecutionFailedException if the insert query has a mismatch in the number of column names and column values.
	@param the insert query to be validated.
	*/
	private boolean isInsertQueryValid() throws QueryExecutionFailedException {
		String[] inputInsertQuery = query.split(" ");
		ArrayList<String> inputQuerySegmentList = removeSpacesFromArray(inputInsertQuery);
	    boolean isValid=Constants.BOOLEAN_VALUE_TRUE; //true;
	    String beginningQueryString = inputQuerySegmentList.get(Constants.DIGIT_ZERO).toUpperCase()+" "+inputQuerySegmentList.get(Constants.DIGIT_ONE).toUpperCase()+" "+inputQuerySegmentList.get(Constants.DIGIT_TWO)+" (";
	    String[] columnNames = inputQuerySegmentList.get(Constants.DIGIT_THREE).split(",");
	    columnNames[0]=columnNames[0].substring(1);
	    columnNames[columnNames.length-Constants.DIGIT_ONE]=columnNames[columnNames.length-Constants.DIGIT_ONE].substring(Constants.DIGIT_ZERO,columnNames[columnNames.length-Constants.DIGIT_ONE].length()-Constants.DIGIT_ONE);
	    String middleQueryString = ") "+inputQuerySegmentList.get(Constants.DIGIT_FOUR).toUpperCase()+" (";
	    String[] columnValues = inputQuerySegmentList.get(Constants.DIGIT_FIVE).split(",");
	    columnValues[Constants.DIGIT_ZERO]=columnValues[Constants.DIGIT_ZERO].substring(Constants.DIGIT_ONE);
	    columnValues[columnValues.length-Constants.DIGIT_ONE] = columnValues[columnValues.length-Constants.DIGIT_ONE].substring(0,columnValues[columnValues.length-Constants.DIGIT_ONE].length()-2);
	    String queryEndString=");";
	    String columnNameValsRegex=Constants.INSERT_QUERY_COLUMN_NAME_MATCHING_REGEX;
	    String queryBeginningRegex=Constants.INSERT_QUERY_BEGIN_REGEX;
	    String queryMiddleRegex=Constants.INSERT_QUERY_MIDDLE_REGEX;
	    String queryEndRegex=Constants.INSERT_QUERY_END_REGEX;
	    if(columnNames.length == columnValues.length) {
	    	for(int i=Constants.DIGIT_ZERO;i<columnNames.length;i++) {
	    		if(!(Pattern.matches(columnNameValsRegex,columnNames[i].trim())) || !(Pattern.matches(columnNameValsRegex, columnValues[i].trim()))) {
	    			isValid=Constants.BOOLEAN_VALUE_FALSE;
	    			break;
	    		}
	    	}
	    }else {
	    	isValid=Constants.BOOLEAN_VALUE_FALSE;
	    	throw new QueryExecutionFailedException(Constants.INSERT_QUERY_EXCEPTION_COLUMN_VALUES_COUNT_MISMATCH_MESSAGE);
	    }
	    
	    return Pattern.matches(queryBeginningRegex, beginningQueryString) && Pattern.matches(queryMiddleRegex, middleQueryString) &&Pattern.matches(queryEndRegex, queryEndString) &&isValid;
		
	}
	
	/**

	Remove spaces from an array of strings and return a list of non-empty, trimmed strings.
	@param inputStringList an array of strings containing spaces and/or empty strings
	@return a list of non-empty, trimmed strings with no spaces
	*/
	private ArrayList<String> removeSpacesFromArray(String[] inputStringList){
		ArrayList<String> inputQuerySegmentList = new ArrayList<String>();
		for(int j=0;j<inputStringList.length;j++) {
			if(!inputStringList[j].equals(" ")&&!inputStringList[j].equals("")) {
				inputQuerySegmentList.add(inputStringList[j].trim());
			}
		}
		return inputQuerySegmentList;
	}
	
	/**

	Compiles the input SQL query and determines whether it is valid for execution or not.
	@return boolean value representing whether the input query is valid or not
	@throws QueryExecutionFailedException if the input query is not valid and cannot be executed
	*/
	public boolean compileQueryString() throws QueryExecutionFailedException {
		String operationType = query.split(" ")[0].toUpperCase();
		if(operationType.equals(Constants.OPERATION_TYPE_CREATE)) {
			return (isCreateTableQueryValid() || isCreateDatabaseQueryValid());
		}
		else if(operationType.equals(Constants.OPERATION_TYPE_INSERT)) {
			return isInsertQueryValid();
		}
		else if(operationType.equals(Constants.OPERATION_TYPE_SELECT)) {
			return isSelectQueryValid();
		}
		else if(operationType.equals(Constants.OPERATION_TYPE_UPDATE)) {
			return isUpdateQueryValid();
		}
		else if(operationType.equals(Constants.OPERATION_TYPE_DELETE)) {
			return isDeleteQueryValid();
		}
		return false;
	}
	
	/**

	Validates if the input DELETE query is valid.
	@return boolean true if input query is a valid DELETE query, false otherwise.
	*/
	private boolean isDeleteQueryValid() {
		boolean isValid = true;
		String[] inputStringList = query.split(" ");
		ArrayList<String> inputQuerySegmentList = removeSpacesFromArray(inputStringList);
		String regexString = Constants.DELETE_QUERY_REGEX;
		String inputString="";
		for(int j=Constants.DIGIT_ZERO;j<inputQuerySegmentList.size();j++) {
			inputString+=inputQuerySegmentList.get(j).toUpperCase()+" ";
		}
		inputString = inputString.substring(Constants.DIGIT_ZERO,inputString.length()-Constants.DIGIT_ONE);
		isValid = Pattern.matches(regexString, inputString);
		System.out.println(isValid);
		return isValid; 
	}
	
	/**

	* Checks whether the input update query is valid or not based on a regex pattern.
	* @return a boolean value indicating whether the update query is valid or not
	*/
	private boolean isUpdateQueryValid() {
		boolean isValid = Constants.BOOLEAN_VALUE_TRUE;
		String[] inputStringList = query.split(" ");
		ArrayList<String> inputQuerySegmentList = removeSpacesFromArray(inputStringList);
		String regexString =Constants.UPDATE_QUERY_REGEX ;
		String inputString="";
		for(int j=Constants.DIGIT_ZERO;j<inputQuerySegmentList.size();j++) {
			inputString+=inputQuerySegmentList.get(j).toUpperCase()+" ";
		}
		inputString = inputString.substring(Constants.DIGIT_ZERO,inputString.length()-Constants.DIGIT_ONE);
		isValid = Pattern.matches(regexString, inputString);
		return isValid;
	}
	
	/**
	* Validates the select query.
	* @return true if the query is valid, false otherwise.
	*/
	public boolean isSelectQueryValid() {
		boolean isValid = Constants.BOOLEAN_VALUE_TRUE;
		String[] inputStringList = query.split(" ");
		ArrayList<String> inputQuerySegmentList = removeSpacesFromArray(inputStringList);
		String beginRegex=Constants.SELECT_QUERY_BEGIN_REGEX;
		String endRegex=null;
		String columnRegex=Constants.SELECT_QUERY_COLUMN_NAME_MATCHING_REGEX;
		String queryBegin=inputQuerySegmentList.get(Constants.DIGIT_ZERO).toUpperCase();
		String queryEnd = null;
		String[] columns = inputQuerySegmentList.get(Constants.DIGIT_ONE).split(",");
		if(query.toUpperCase().contains("WHERE")) {
			endRegex = Constants.SELECT_QUERY_WITH_WHERE_CONDITION_END_REGEX_QUERY;
			queryEnd = inputQuerySegmentList.get(Constants.DIGIT_TWO).toUpperCase()+" "+inputQuerySegmentList.get(Constants.DIGIT_THREE)+" "+inputQuerySegmentList.get(Constants.DIGIT_FOUR).toUpperCase()+" "+inputQuerySegmentList.get(Constants.DIGIT_FIVE);
			
		}else {
			endRegex = Constants.SELECT_QUERY_END_REGEX;
			queryEnd = inputQuerySegmentList.get(Constants.DIGIT_TWO).toUpperCase()+" "+inputQuerySegmentList.get(3);
		}
		for(String column : columns) {
			isValid = Pattern.matches(columnRegex, column);
		}
	    isValid = isValid && Pattern.matches(beginRegex,queryBegin) && Pattern.matches(endRegex,queryEnd);
		return isValid;
	}
	
	/**

	* Handles the creation of a database for a user.
	* @param user the user object
	* @param dbName the name of the database to be created
	* @throws QueryExecutionFailedException if there was an error executing the query
	* @throws IOException if there was an error accessing the file system
	*/
	private void handleDatabaseCreation(User user,String dbName) throws QueryExecutionFailedException, IOException {
		if(user.getdBName().substring(Constants.DIGIT_ZERO,user.getdBName().length()-Constants.DIGIT_ONE)==null) {
			String rootPath = "src/main/resources/database/"+user.getUserName()+"/"+dbName;
			File userDbDirectory = new File(rootPath);
			if(!userDbDirectory.exists()) {
				if(userDbDirectory.mkdirs()) {
					System.out.println(Constants.DB_CREATION_SUCCESS_MESSAGE);
					user.updateUserDB(dbName);
				}
					
				else
					throw new QueryExecutionFailedException(Constants.DB_CREATION_EXCEPTION_MESSAGE);
			}
			}
		else {
			throw new QueryExecutionFailedException(Constants.DB_ALREADY_EXISTS_EXCEPTION_MESSAGE);
		}
	}
	
	/**
	 * Handles table creation for a user in the database.
	 *
	 * @param user the user for whom the table is being created
	 * @throws IOException if there is an error writing the table or metadata files
	 */
	private void handleTableCreation(User user) throws IOException {
		String filePath="src/main/resources/database/"+user.getUserName()+"/"+user.getdBName().substring(0,user.getdBName().length()-1)+"/"+query.split(" ")[2]+".txt";
		String metaDataFilePath="src/main/resources/database/"+user.getUserName()+"/"+user.getdBName().substring(0,user.getdBName().length()-1)+"/"+query.split(" ")[2]+"_metadata.txt";
		String tableMetaData = getTableMetaData(query.substring(query.indexOf("(")+1,query.length()-2).split(","));
        // Write column names into table file path
        String tableHeader = getTableHeaderData(query.substring(query.indexOf("(")+1,query.length()-2).split(","));
        // Write table meta data into metadata file path.
        IOOperations.writeDataIntoFile(filePath, tableHeader);
        IOOperations.writeDataIntoFile(metaDataFilePath, tableMetaData);
	}
	
	/**

	* Returns the table header data for the given columns.
	* The header data contains column names separated by colons and ended with a newline character.
	* @param columns the columns of the table
	* @return the header data as a string
	*/
	private String getTableHeaderData(String[] columns) {
		String headerData="";
		for(String column : columns) {
			String columnName = column.split(" ")[0];
			headerData= headerData+columnName;
			headerData=headerData+":";
		}
		return headerData+"\n";
	}
	
	/**

	* Generates table metadata string from given column names and data types.
	* @param columns Array of column names
	* @return String containing table metadata.
	*/
	private String getTableMetaData(String[] columns){
		String metaData="";
		for(String column : columns) {
			metaData=metaData+column;
			metaData=metaData+"\n";
		}
		return metaData+"\n";
	}
	
	/**

	* Handles query execution operations
	* @param User object user and Logger object log
	* @return String containing query execution result
	*/
	public String executeQuery(User user,Logger log) throws QueryExecutionFailedException, IOException {
		String result="";
		ArrayList<String> inputQuerySegmentList = removeSpacesFromArray(query.split(" "));
		String operationType = inputQuerySegmentList.get(0).toUpperCase();
		
		if(operationType.equals(Constants.OPERATION_TYPE_CREATE)) {
			String creationType = query.split(" ")[1].toUpperCase();
			if(creationType.equals("DATABASE")) {
				handleDatabaseCreation(user,query.split(" ")[2]);
				log.logInfo(Constants.DB_CREATION_SUCCESS_MESSAGE);
			}else {
				handleTableCreation(user);
				log.logInfo(Constants.TABLE_CREATION_SUCCESS_MESSAGE);
			}
		}
		else if(operationType.equals(Constants.OPERATION_TYPE_INSERT)) {
			String tableName = inputQuerySegmentList.get(Constants.DIGIT_TWO);
			if(dbAndTableExists(tableName,user.getUserName(),user.getdBName())) {
				if(isColumnNamesMatching(inputQuerySegmentList.get(3),tableName,user.getdBName(),user.getUserName())){
				
					if(isColumnDataTypesMatching(inputQuerySegmentList.get(3),inputQuerySegmentList.get(5),tableName,user.getdBName(),user.getUserName())) {
						String filePath = "src/main/resources/database/"+user.getUserName()+"/"+user.getdBName()+"/"+tableName+".txt";
						String dataToBeInserted = "";
						String[] inputColumnValues = inputQuerySegmentList.get(5).split(",");
						inputColumnValues[0] = inputColumnValues[0].substring(1);
						inputColumnValues[inputColumnValues.length - 1] = inputColumnValues[inputColumnValues.length - 1].substring(0,inputColumnValues[inputColumnValues.length - 1].length()-2);
						for(String value : inputColumnValues) {
							dataToBeInserted = dataToBeInserted + value+":";
						}
						dataToBeInserted+="\n";
						IOOperations.writeDataIntoFile(filePath, dataToBeInserted);
						System.out.println("Inserted successfully : "+dataToBeInserted);
						log.logInfo("Inserted successfully : "+dataToBeInserted);
					}else {
						throw new QueryExecutionFailedException(Constants.VALUE_DATA_TYPE_MISMATCH_MESSAGE);
					}
				}else {
					throw new QueryExecutionFailedException(Constants.COLUMN_NAME_MISMATCH_MESSAGE);
				}
			}
		}
		else if(operationType.equals(Constants.OPERATION_TYPE_SELECT)) {
			String tableName = inputQuerySegmentList.get(Constants.DIGIT_THREE);
			String[] columnNamesList=null;
			String header="";
			String[] data;
			if(query.toUpperCase().contains(Constants.CONSTRAINT_WHERE)) {
				if(dbAndTableExists(tableName,user.getUserName(),user.getdBName())) {
//					System.out.println("DB and table exists");
					if(inputQuerySegmentList.get(1).equals("*")) {
						 columnNamesList = fetchColumnNames(tableName, user.getdBName(), user.getUserName());
						 String[] columnDataTypeList = fetchColumnDataType(tableName, user.getdBName(), user.getUserName());
						 for(int i=0;i<columnDataTypeList.length;i++) {
							 columnDataTypeList[i] = columnDataTypeList[i].split(" ")[1];
						 }
						 File tablePath = new File("src/main/resources/database/"+user.getUserName()+"/"+user.getdBName()+"/"+tableName+".txt");
						 data = IOOperations.readDataFromFile(tablePath).split(Constants.PIPE_DELIMITER_REGEX);
						 header = data[1].toUpperCase();
						 
						 for(int i=1;i<data.length;i++) {
							 if(evaluateWhereCondition(inputQuerySegmentList.get(5),columnNamesList,columnDataTypeList,data[i])) {
								 result+=data[i]+"||";
								 log.logInfo("Executing select query : "+result);
							 }
						 }
						display(header, result.split(Constants.PIPE_DELIMITER_REGEX));
						log.logInfo(Constants.SELECT_QUERY_EXECUTION_SUCCESS_MESSAGE);
					}else {
						 columnNamesList = fetchColumnNames(tableName, user.getdBName(), user.getUserName());
						 String[] columnDataTypeList = fetchColumnDataType(tableName, user.getdBName(), user.getUserName());
						 for(int i=0;i<columnDataTypeList.length;i++) {
							 columnDataTypeList[i] = columnDataTypeList[i].split(" ")[1];
						 }
						 File tablePath = new File("src/main/resources/database/"+user.getUserName()+"/"+user.getdBName()+"/"+tableName+".txt");
						 data = IOOperations.readDataFromFile(tablePath).split(Constants.PIPE_DELIMITER_REGEX);
						 for(int i=1;i<data.length;i++) {
							 if(evaluateWhereCondition(inputQuerySegmentList.get(5),columnNamesList,columnDataTypeList,data[i])) {
								 result+=data[i]+"||";
							 }
						 }
						 String[] inputColumnList = inputQuerySegmentList.get(1).split(",");
						 String[] resultData = result.split(Constants.PIPE_DELIMITER_REGEX);
						 int[] columnNameIndexList = new int[inputColumnList.length];
							List<String> headerList = Arrays.asList(columnNamesList);
							for(int k=0;k<inputColumnList.length;k++) {
								columnNameIndexList[k] = headerList.indexOf(inputColumnList[k]);
							}
							
							for(int i=0;i<inputColumnList.length;i++) {
								header+=inputColumnList[i].toUpperCase()+" : ";
							}
							for(int j=0;j<resultData.length;j++) {
								String line="";
								String[] lineList = resultData[j].split(":");
								for(int k=0;k<inputColumnList.length;k++) {
									line+=lineList[columnNameIndexList[k]]+" : ";
									log.logInfo("Executing select query : "+line);
								}
								resultData[j] = line+"\n";
							}
							log.logInfo(Constants.SELECT_QUERY_EXECUTION_SUCCESS_MESSAGE);
							displayResultOfWhere(header,resultData);
						
					}
				}
			}
			else {
				tableName = tableName.substring(0,tableName.length()-1);
				if(dbAndTableExists(tableName,user.getUserName(),user.getdBName())) {
//					System.out.println("DB and table exists");
					if(inputQuerySegmentList.get(1).equals("*")) {
						File tablePath = new File("src/main/resources/database/"+user.getUserName()+"/"+user.getdBName()+"/"+tableName+".txt");
						 data = IOOperations.readDataFromFile(tablePath).split("\\|\\|");
						 header = data[1].toUpperCase();
						 display(header,data);
						 log.logInfo(Constants.SELECT_QUERY_EXECUTION_SUCCESS_MESSAGE);
					}
					else {
						File tablePath = new File("src/main/resources/database/"+user.getUserName()+"/"+user.getdBName()+"/"+tableName+".txt");
						String[] inputColumnList = inputQuerySegmentList.get(1).split(",");
						String[] tempColumnNameList = fetchColumnNames(tableName, user.getdBName(), user.getUserName());
						data = IOOperations.readDataFromFile(tablePath).split(Constants.PIPE_DELIMITER_REGEX);
						//newly added
						int[] columnNameIndexList = new int[inputColumnList.length];
						List<String> headerList = Arrays.asList(tempColumnNameList);
						for(int k=0;k<inputColumnList.length;k++) {
							columnNameIndexList[k] = headerList.indexOf(inputColumnList[k]);
						}
						for(int i=0;i<inputColumnList.length;i++) {
							header+=inputColumnList[i].toUpperCase()+" : ";
						}
						
						for(int j=1;j<data.length;j++) {
							String line="";
							String[] lineList = data[j].split(":");
							for(int k=0;k<inputColumnList.length;k++) {
								line+=lineList[columnNameIndexList[k]]+" : ";
								log.logInfo("Executing select query : "+line);
							}
							data[j] = line+"\n";
						}
						display(header,data);
						log.logInfo(Constants.SELECT_QUERY_EXECUTION_SUCCESS_MESSAGE);
					}
					
				}
			}
			
		}
		else if(operationType.equals(Constants.OPERATION_TYPE_UPDATE)) {
			String tableName = inputQuerySegmentList.get(1);
			if(dbAndTableExists(tableName,user.getUserName(),user.getdBName())) {
				String[] columnNames = fetchColumnNames(tableName,user.getdBName() , user.getUserName());
				String updatingColumnName = inputQuerySegmentList.get(3).split("=")[0];
				String updatingColumnValue = inputQuerySegmentList.get(3).split("=")[1];
				updatingColumnValue = updatingColumnValue.substring(0,updatingColumnValue.length()-1);
				updatingColumnName = updatingColumnName.substring(1);
				boolean isGivenColumnNameValid=Constants.BOOLEAN_VALUE_FALSE;
				for(int i=0;i<columnNames.length;i++) {
					if(columnNames[i].equals(updatingColumnName)) {
						isGivenColumnNameValid = Constants.BOOLEAN_VALUE_TRUE;
						break;
					}
				}
				if(isGivenColumnNameValid) {
					String[] columnDataTypeList = fetchColumnDataType(tableName, user.getdBName(), user.getUserName()); 
					for(int i=0;i<columnDataTypeList.length;i++) {
						 columnDataTypeList[i] = columnDataTypeList[i].split(" ")[1];
					 }
					List<String> columnNamesList = Arrays.asList(columnNames);
					int updateColumnIndex = columnNamesList.indexOf(updatingColumnName);
					
					File tablePath = new File("src/main/resources/database/"+user.getUserName()+"/"+user.getdBName()+"/"+tableName+".txt");
						 String[] data = IOOperations.readDataFromFile(tablePath).split("\\|\\|");
						 String header = data[0];
						 System.out.println("Header info : "+data[0]);
						 result = header+"\n";
						 int updateCount = 0;
						 for(int i=1;i<data.length;i++) {
							 if(evaluateWhereCondition(inputQuerySegmentList.get(5),columnNames,columnDataTypeList,data[i])) {
								 String temporaryString = "";
								 String[] columnValues = data[i].split(":");
								 
								 columnValues[updateColumnIndex] = updatingColumnValue;
								 for(int j=0;j<columnValues.length;j++) {
									 temporaryString+=columnValues[j]+":";
								 }
								 data[i] = temporaryString;
								 updateCount++;
								 
							 }
						   result+=data[i]+"\n";
						 }
						 
						 IOOperations.writeDataIntoFileNotInAppendMode("src/main/resources/database/"+user.getUserName()+"/"+user.getdBName()+"/"+tableName+".txt", result);
						 displayResultOfWhere(header, data);

						 log.logInfo(updateCount + Constants.RECORD_UPDATE_EXECUTION_SUCCESS_MESSAGE);
				}
				else {
					throw new QueryExecutionFailedException(Constants.COLUMN_NAME_MISMATCH_MESSAGE);
				}
			}
		}else if(operationType.equals(Constants.OPERATION_TYPE_DELETE)) {
			String tableName = inputQuerySegmentList.get(2);
			if(dbAndTableExists(tableName,user.getUserName(),user.getdBName())) {
				String[] columnNames = fetchColumnNames(tableName,user.getdBName() , user.getUserName());
				String deleteColumnName = inputQuerySegmentList.get(4).split("=")[0];
				deleteColumnName = deleteColumnName.substring(1);
				boolean isGivenColumnNameValid=Constants.BOOLEAN_VALUE_FALSE;
				for(int i=0;i<columnNames.length;i++) {
					if(columnNames[i].equals(deleteColumnName)) {
						isGivenColumnNameValid = Constants.BOOLEAN_VALUE_TRUE;
						break;
					}
				}
				if(isGivenColumnNameValid) {
					File tablePath = new File("src/main/resources/database/"+user.getUserName()+"/"+user.getdBName()+"/"+tableName+".txt");
					 String[] data = IOOperations.readDataFromFile(tablePath).split(Constants.PIPE_DELIMITER_REGEX);
					 String header = data[0];
					 System.out.println("Header info : "+data[0]);
					 result = header+"\n";
					 String[] columnDataTypeList = fetchColumnDataType(tableName, user.getdBName(), user.getUserName()); 
						for(int i=0;i<columnDataTypeList.length;i++) {
							 columnDataTypeList[i] = columnDataTypeList[i].split(" ")[1];
						 }
					 int deleteCount = 0;
					 for(int i=1;i<data.length;i++) {
						 if(!evaluateWhereCondition(inputQuerySegmentList.get(4),columnNames,columnDataTypeList,data[i])) {
						    result+=data[i]+"\n";
						    deleteCount++;
						 }
						 }
					 IOOperations.writeDataIntoFileNotInAppendMode("src/main/resources/database/"+user.getUserName()+"/"+user.getdBName()+"/"+tableName+".txt", result);
					 
				displayResultOfWhere(header, result.split(Constants.PIPE_DELIMITER_REGEX));
				log.logInfo(deleteCount+" : rows deleted");
				}else {
					throw new QueryExecutionFailedException("Given invalid column name to set");
				}
				
			}else {
				throw new QueryExecutionFailedException("Given invalid column name to set");
			}
		}
		return result;
	}
	
	/**

	* Dsiplays data for select query execution
	* @param String of result set header
	* @param String Array of data from result set
	*/
	private void displayResultOfWhere(String header, String[] resultData) {
		// TODO Auto-generated method stub
		System.out.println("=============================================================");
		System.out.println(header);
		System.out.println("=============================================================");
		 for(int i=0;i<resultData.length;i++) {
			 System.out.println(resultData[i]);
		 }
		 System.out.println("=============================================================");
	
		
	}

	/**

	* To evaluate the where condition given in the query
	* @param Array ofColumnNames
	* @param String of where condition from query
	* @param Array of Column Data Types
	* @param String line of data read from file.
	* @return boolean result of where condition evaluation
	*/
	private boolean evaluateWhereCondition(String condition, String[] columnNames, String[] columnDataType, String line) throws QueryExecutionFailedException {
		boolean isValid = false;
		String conditionColumn,conditionValue;
		Map<Integer,String> operationMap = new HashMap<Integer,String>();
		operationMap.put(1, "=");
		operationMap.put(2, ">");
		operationMap.put(3, "<");
		operationMap.put(4, ">=");
		operationMap.put(5, "<=");
		int operationType=0;
		if(condition.contains("<=")) {
			conditionColumn = condition.split("<=")[0].substring(1);
			conditionValue = condition.split("<=")[1].substring(0,condition.split("<=")[1].length()-2);
			operationType=5;
		}else if(condition.contains(">=")) {
			conditionColumn = condition.split(">=")[0].substring(1);
			conditionValue = condition.split(">=")[1].substring(0,condition.split(">=")[1].length()-2);
			operationType=4;
		}else if(condition.contains("=")) {
			conditionColumn = condition.split("=")[0].substring(1);
			conditionValue = condition.split("=")[1].substring(0,condition.split("=")[1].length()-2);
			operationType=1;
		}else if(condition.contains(">")) {
			conditionColumn = condition.split(">")[0].substring(1);
			conditionValue = condition.split(">")[1].substring(0,condition.split(">")[1].length()-2);
			operationType=2;
		}else if(condition.contains("<")) {
			conditionColumn = condition.split("<")[0].substring(1);
			conditionValue = condition.split("<")[1].substring(0,condition.split("<")[1].length()-2);
			operationType=3;
		}else {
			throw new QueryExecutionFailedException("Invalid conditional statment in where clause");
		}
		
		String dataType="";
		int columnIndex=0;
		for(int i=0;i<columnNames.length;i++) {
			if(columnNames[i].equals(conditionColumn)) {
				dataType = columnDataType[i];
				columnIndex = i;
				break;
			}
		}
		if(dataType.equals("INT")) {
			int value = Integer.parseInt(conditionValue);
			int lineValue = Integer.parseInt(line.split(":")[columnIndex]);
			switch(operationType) {
			case 1 : isValid = (value == lineValue);
					break;
			case 2 : isValid = (lineValue>value);
					break;
			case 3 : isValid = (lineValue<value);
					break;
			case 4 : isValid = (lineValue>=value);
					break;
			case 5 : isValid = (lineValue<=value);
					 break;
			}
		}else if(dataType.equals("VARCHAR")) {
			String value = conditionValue;
			String lineValue = line.split(":")[columnIndex];
			isValid = value.equals(lineValue);
		}else if(dataType.equals("DOUBLE")) {
			double value = Double.parseDouble(conditionValue);
			double lineValue = Double.parseDouble(line.split(":")[columnIndex]);
			switch(operationType) {
			case 1 : isValid = (value == lineValue);
					break;
			case 2 : isValid = (lineValue>value);
					break;
			case 3 : isValid = (lineValue<value);
					break;
			case 4 : isValid = (lineValue>=value);
					break;
			case 5 : isValid = (lineValue<=value);
					break;
			 }
		}
		return isValid;
	}
	
	/**

	* Dsiplays data for select query execution
	* @param String of result set header
	* @param String Array of data from result set
	*/
	private void display(String header,String[] data) {
		System.out.println("=============================================================");
		System.out.println(header);
		System.out.println("=============================================================");
		 for(int i=2;i<data.length;i++) {
			 System.out.println(data[i]);
		 }
		 System.out.println("=============================================================");
	}
	
	/**

	* Dsiplays data for select query execution
	* @param String of column Names
	* @param String of Column Values
	* @param String of table name
	* @param String of DB name
	* @param String of User name
	* @return boolean of whether datatypes are matching
	*/
	private boolean isColumnDataTypesMatching(String columnNames,String columnValues, String tableName, String dbName, String userName) throws IOException {
	boolean isValid=true;
	String[] inputColumnNames = columnNames.split(",");
	inputColumnNames[0] = inputColumnNames[0].substring(1);
	inputColumnNames[inputColumnNames.length - 1] = inputColumnNames[inputColumnNames.length - 1].substring(0,inputColumnNames[inputColumnNames.length - 1].length()-1);
	String[] inputColumnValues = columnValues.split(",");
	inputColumnValues[0] = inputColumnValues[0].substring(1);
	inputColumnValues[inputColumnValues.length - 1] = inputColumnValues[inputColumnValues.length - 1].substring(0,inputColumnValues[inputColumnValues.length - 1].length()-2);
	String[] columnDataTypes = fetchColumnDataType(tableName,dbName,userName);
	String intRegex = "[0-9]+";
	String varcharRegex = "[a-zA-Z0-9_#*@=+?<>.!%]+";
	String doubleRegex = "[0-9.]+";
	
	for(int i=0;i<inputColumnNames.length;i++) {
		if((columnDataTypes[i].split(" ")[0].equals(inputColumnNames[i])) && (columnDataTypes[i].split(" ")[1].equals("INT"))) {
		   isValid = Pattern.matches(intRegex, inputColumnValues[i]);
		}else if((columnDataTypes[i].split(" ")[0].equals(inputColumnNames[i])) && (columnDataTypes[i].split(" ")[1].equals("VARCHAR"))) {
			isValid = Pattern.matches(varcharRegex, inputColumnValues[i]);
		}else if((columnDataTypes[i].split(" ")[0].equals(inputColumnNames[i])) && (columnDataTypes[i].split(" ")[1].equals("DOUBLE"))) {
			isValid = Pattern.matches(doubleRegex, inputColumnValues[i]);
		}
	}
	
		return isValid;
	}
	
	/**

	* Fetches the data types of columns in the specified table from the metadata file.
	* @param tableName the name of the table to fetch column data types for
	* @param dbName the name of the database containing the table
	* @param userName the name of the user accessing the database
	* @return an array of strings containing the data types of each column in the table
	* @throws IOException if an error occurs while reading the metadata file
	*/
	private String[] fetchColumnDataType(String tableName,String dbName, String userName) throws IOException {
		File metaDataFile = new File("src/main/resources/database/"+userName+"/"+dbName+"/"+tableName+"_metadata.txt");
		String[] columnTypes = IOOperations.readDataFromFile(metaDataFile).split("\\|\\|");
		
		return columnTypes;
	}
	
	private boolean isColumnNamesMatching(String columnNames, String tableName, String dbName, String userName) throws IOException {
		String[] inputColumnNames = columnNames.split(",");
		inputColumnNames[0] = inputColumnNames[0].substring(1);
		inputColumnNames[inputColumnNames.length - 1] = inputColumnNames[inputColumnNames.length - 1].substring(0,inputColumnNames[inputColumnNames.length - 1].length()-1);
		String[] actualColumnNames = fetchColumnNames(tableName,dbName,userName);
		boolean isValid=true;
		if(inputColumnNames.length == actualColumnNames.length) {
			for(int k=0;k<inputColumnNames.length;k++) {
				if(!inputColumnNames[k].equals(actualColumnNames[k])) {
					isValid=false;
					break;
				}
			}
			
		}
		return isValid;
	}
	
	/**

	* Compares the input column names with the actual column names of a table and returns a boolean indicating whether they match.

	* @param columnNames a comma-separated string containing the names of the input columns

	* @param tableName the name of the table whose columns need to be compared

	* @param dbName the name of the database that the table belongs to

	* @param userName the name of the user who owns the database and table

	* @return true if the input column names match the actual column names of the table, false otherwise

	* @throws IOException if there is an error reading the table metadata file
	*/
	private String[] fetchColumnNames(String tableName,String databaseName,String userName) throws IOException {
		File metaDataFile = new File("src/main/resources/database/"+userName+"/"+databaseName+"/"+tableName+".txt");
		String[] columnNames = IOOperations.readFirstLineDataFromFile(metaDataFile).split(":");
		return columnNames;
	}
	
	/**

	* Checks whether a given table exists in a given database and user's directory.
	* @param tableName the name of the table to check for existence
	* @param userName the name of the user owning the database
	* @param dBName the name of the database to check for existence
	* @return true if the table exists in the given database and user's directory, false otherwise
	* @throws QueryExecutionFailedException if the database or table does not exist
	*/
	private boolean dbAndTableExists(String tableName,String userName, String dBName) throws QueryExecutionFailedException {
		String dBPath="src/main/resources/database/"+userName+"/"+dBName;;
		File dBDirectory = new File(dBPath);
		boolean isExist = false;
		if(dBDirectory.exists()) {
			File tableFileDirectory = new File(dBPath+"/");
			if(exists(tableFileDirectory,tableName+".txt")) {
				isExist=true;
			}
			else {
				throw new QueryExecutionFailedException("Table does not exist but db exist");
			}
		}
		else {
			throw new QueryExecutionFailedException("DB Does not exist");
		}
		return isExist;
	}
	
	/**

	* Checks if the given file exists in the specified directory.
	* @param fileDirectory The directory to search for the file.
	* @param filename The name of the file to search for.
	* @return true if the file exists in the directory, false otherwise.
	*/
	public boolean exists(File fileDirectory, String filename){
	    String[] files = fileDirectory.list();
	    for(String file : files)
	        if(file.equals(filename))
	            return true;
	    return false;
	}
	
	/**

	* Sets the query to be executed.
	* @param query The query to be executed.
	*/
	public void setQuery(String query) {
		this.query = query;
	}
}



