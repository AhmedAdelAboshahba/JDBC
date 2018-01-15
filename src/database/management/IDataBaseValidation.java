package database.management;
import java.sql.SQLException;
import java.util.LinkedList;

public interface IDataBaseValidation {
		/*
	    Receive a name of database from user
		and create it
		*/
		public void creatDataBase(String dbName) throws SQLException;
		
		/*
		Receive a name of Table from user
		and create it in last Created DataBase
		*/
		public void creatTable(String tableName,LinkedList<String> columnsName,LinkedList<String> columnsType ) throws SQLException;
		
		/*
		 Receive a name of Table that the user insert in it
		 Receive the values of each column respectively 
		 */
		 
		public void insertIntoTable (String tableName , LinkedList<String> values) throws SQLException;
		
		public void selectAll (String tableName ) throws SQLException;
		public void selectFromTable (String tableName,LinkedList<String> selectedColumn ) throws SQLException;
		
		public void deleteFromTable (String tableName, String columnToBeChecked, String valueToBeChecked,String operator ) throws SQLException;
		
		public void updateTable(String tableName,String conditionColumn,String operator,String conditionValue,LinkedList <String> setColumns ,LinkedList <String>  setValues) throws SQLException;		
		/*
		  Receive a name of database from user
		and delete it
		 */
		 
		public void dropDataBase(String databaseName) throws SQLException;
		
		/*
		  Receive a name of Table from user
		and delete it
		 */
		public void dropTable(String tableName) throws SQLException;
		public void selectAllByCondition(String tableName,String columnName,String Value,String operator) throws SQLException;
		public void selectFromByCondition(String tableName,String columnName,String Value,String operator,LinkedList<String> selectedColumns) throws SQLException;
		public void deleteAll(String tableName) throws SQLException;

		void dropColumn(String tableName, String columnName) throws SQLException;

		void addColumn(String tableName, String columnName, String columntype) throws SQLException;
		public void insertByColumn(String tableName,LinkedList<String> values,LinkedList<String> columnsName) throws SQLException;
		public void updateSelectedColumns(String tableName,LinkedList<String> values,LinkedList<String> columnsName) throws SQLException;

		void selectDistinctByCondition(String tableName, LinkedList<String> columnsNames, String columnName,
				String Value, String operator) throws SQLException;
		
	
}
