package databaseprototype;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import exceptions.QueryExecutionFailedException;

public class DatabasePrototypeTest {

	/**

	* Tests the compilation of a CREATE TABLE query string.
	* @throws QueryExecutionFailedException if the query execution fails
	* @param none
	* @return void
	*/
	@Test
	public void compileQueryStringTestForCreateTable() throws QueryExecutionFailedException {
		Query query = new Query();
		query.setQuery("CREATE TABLE myTable (id INT,name varchar,salary DOUBLE);");
		assertTrue(query.compileQueryString());
	}
	
	/**

	* Tests the compilation of a CREATE Database query string.
	* @throws QueryExecutionFailedException if the query execution fails
	* @param none
	* @return void
	*/
	@Test
	public void compileQueryStringTestForCreateDatabase() throws QueryExecutionFailedException {
		Query query = new Query();
		query.setQuery("CREATE DATABASE dbName;");
		assertTrue(query.compileQueryString());
	}
	
	/**

	* Tests the compilation of a INSERT data query string.
	* @throws QueryExecutionFailedException if the query execution fails
	* @param none
	* @return void
	*/
	@Test
	public void compileQueryStringTestForInsertData() throws QueryExecutionFailedException {
		Query query = new Query();
		query.setQuery("INSERT INTO myTable (id,name) VALUES (1,Raju);");
		assertTrue(query.compileQueryString());
	}
	

	/**

	* Tests the compilation of a INSERT data query string.
	* @throws QueryExecutionFailedException if the query execution fails
	* @param none
	* @return void
	*/
	@Test(expected = QueryExecutionFailedException.class)
	public void compileQueryStringTestForInsertDataThrowsExceptionTest() throws QueryExecutionFailedException {
		Query query = new Query();
		query.setQuery("INSERT INTO myTable (id,name,salary) VALUES (1,Raju);");
		query.compileQueryString();
	}
	
	
	/**

	* Tests the compilation of a DELETE data query string.
	* @throws QueryExecutionFailedException if the query execution fails
	* @param none
	* @return void
	*/
	@Test
	public void compileQueryStringTestForDeleteQuery() throws QueryExecutionFailedException {
		Query query = new Query();
		query.setQuery("DELETE FROM myTable WHERE (id=2);");
		assertTrue(query.compileQueryString());
	}
	
	/**

	* Tests the compilation of a Update data query string.
	* @throws QueryExecutionFailedException if the query execution fails
	* @param none
	* @return void
	*/
	@Test
	public void compileQueryStringTestForUpdateQuery() throws QueryExecutionFailedException {
		Query query = new Query();
		query.setQuery("UPDATE myTable SET (name=Lalith) WHERE (id=3);");
		assertTrue(query.compileQueryString());
	}
	
}
