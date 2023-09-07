package databaseprototype;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class IOOperations {

	//Generic read method to read data from given file where each line  is distinguished using || delimiter
	public static String readDataFromFile(File fileName) throws IOException {
		String data="";
		String eachLine="";
		BufferedReader inputFileReader = new BufferedReader(new FileReader(fileName));
	    data = inputFileReader.readLine();
		while((eachLine=inputFileReader.readLine())!=null) {
			data=data+"||"+eachLine;
		}
		return data;
		
	}
	
	//Generic read method to read only first line from given file
	public static String readFirstLineDataFromFile(File fileName) throws IOException {
		String data="";
		BufferedReader inputFileReader = new BufferedReader(new FileReader(fileName));
	    data = inputFileReader.readLine();
	    return data;
	}
	
	//This method is to read user credentials data from credentials file
	public static String[] readDataFromFile(File fileName, String userName, String password) throws IOException {

		String line="";
		BufferedReader inputFileReader = new BufferedReader(new FileReader(fileName));
	    
		while((line=inputFileReader.readLine())!=null) {
			if(line.contains(userName)) {
				return line.split(":");
			}
		}
		return null;
	}
	
	//For writing data into user credentials.
	public static void writeDataIntoFile(User user) throws IOException{
		FileWriter fileWriter=null;
		BufferedWriter outputFileWriter=null;
		try {
			fileWriter = new FileWriter("src/main/resources/database/UserCredentials.txt",true);
			outputFileWriter = new BufferedWriter(fileWriter);
			outputFileWriter.newLine();
			String toBeAdded = "<"+user.getUserName()+":"+user.getPassword()+":"+user.getSecurityQuestionAnswer()+":"+user.getdBName()+">";
			outputFileWriter.write(toBeAdded);
			outputFileWriter.flush();
		}catch(Exception e) {
			
		}
		finally {
			outputFileWriter.close();
			fileWriter.close();
			
		}
	}
	
	
	public static void writeDataIntoFile(String path,String data) throws IOException {
		FileWriter fileWriter=null;
		BufferedWriter outputFileWriter=null;
		try {
			fileWriter = new FileWriter(path,true);
			outputFileWriter = new BufferedWriter(fileWriter);
			outputFileWriter.write(data);
			outputFileWriter.flush();
		}catch(Exception e){
			System.out.println("Exception in writeDataIntoFile");
			e.printStackTrace();
		}finally {
			outputFileWriter.close();
			fileWriter.close();
		}
	}
	
	public static void writeDataIntoFileNotInAppendMode(String path,String data) throws IOException {
		
		FileWriter fileWriter=null;
		BufferedWriter outputFileWriter=null;
		try {
			fileWriter = new FileWriter(path);
			outputFileWriter = new BufferedWriter(fileWriter);
			outputFileWriter.write(data);
			outputFileWriter.flush();
		}catch(Exception e){
			System.out.println("Exception in writeDataIntoFile");
			e.printStackTrace();
		}finally {
			outputFileWriter.close();
			fileWriter.close();
		}
		
	}
	
	public static void overWriteDataInFile(String path,String userName, String dbName) throws IOException {
		StringBuilder data= new StringBuilder();
		String line="";
		BufferedReader inputFileReader = new BufferedReader(new FileReader(path));
	    
		while((line=inputFileReader.readLine())!=null) {
			if(line.contains(userName)) {
				String[] replacingLine =  line.split(":");
				String updatedLine=replacingLine[0]+":"+replacingLine[1]+":"+replacingLine[2]+":"+dbName+">"+"\n";		
			     data.append(updatedLine);	
			}
			else {
				data.append(line+"\n");
			}
		}
		writeDataIntoFile(path,data.toString());
	}
	
}
