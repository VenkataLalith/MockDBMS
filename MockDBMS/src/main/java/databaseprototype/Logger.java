package databaseprototype;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger 
{
  private User user;
  private String logFilePath;
  private FileWriter logFile;
  private DateTimeFormatter dtf;
  private LocalDateTime now;
  private BufferedWriter logWriter;
  
  
public Logger(User user) throws IOException {
	super();
	this.user = user;
	dtf = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
	now = LocalDateTime.now();
	this.logFilePath = "src/main/resources/database/"+user.getUserName()+"/logs/"+user.getUserName()+dtf.format(now)+".txt";
	logFile = new FileWriter(logFilePath,true);
	logWriter = new BufferedWriter(logFile);
	
}

public void createLogFile() throws IOException {
	File logFile = new File(logFilePath);
	logFile.createNewFile();
}

public void logInfo(String message) throws IOException {
	dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	now = LocalDateTime.now();
	String metadata = "["+dtf.format(now)+"   @"+user.getUserName()+" : INFO]  ";
	logWriter.newLine();
	logWriter.write(metadata+message);
	logWriter.flush();
}

public void closeLogFile() throws IOException {
	logWriter.close();
	logFile.close();
}

public User getUser() {
	return user;
}
public void setUser(User user) {
	this.user = user;
}
public String getLogFilePath() {
	return logFilePath;
}
public void setLogFilePath(String logFilePath) {
	this.logFilePath = logFilePath;
}

}
