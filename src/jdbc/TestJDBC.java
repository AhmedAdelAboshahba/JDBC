package jdbc;

import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
 
import org.junit.Test;
 
import org.junit.Assert;
 
public class TestJDBC {
    private String protocol = "xmldb";
    private String tmp = System.getProperty("java.io.tmpdir");
   
    private Connection createUseDatabase(String databaseName) throws SQLException {
        Driver driver = new DriverManager();
        Properties info = new Properties();
        File dbDir = new File(tmp + "/jdbc/" + Math.round((((float) Math.random()) * 100000)));
        info.put("path", dbDir.getAbsoluteFile());
 
        Connection connection = driver.connect("jdbc:" + protocol + "://localhost", info);
        Statement statement = connection.createStatement();        
        statement.execute("CREATE DATABASE " + databaseName);      
        statement.execute("USE " + databaseName);
        statement.close();
        return connection;
    }
   
    @Test
    public void testcreat() throws SQLException { //test creat tables
        Connection connection=createUseDatabase("class");
        try {
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE table_name1(column_name1 varchar, column_name2 int, column_name3 date)");
            statement.close();
        } catch (Throwable e) {
            Assert.fail("Failed to create table");
        }
        try {
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE table_name2(column_name1 varchar, column_name2 int, column_name3 float)");
            statement.close();
        } catch (Throwable e) {
            Assert.fail("Failed to create table");
        }
        try {
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE table_name2(column_name1 varchar, column_name2 int, column_name3 date)");
            Assert.fail("Created existing table successfully!");
        } catch (SQLException e) {
 
        } catch (Throwable e) {
            Assert.fail("Invalid Exception thrown");
        }
 
    }
 
    @Test
    public void testinsert() throws SQLException { //test insert tables
        Connection connection = createUseDatabase("class");
        try { // insert into table not found
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE table_name1(column_name1 varchar, column_name2 int, column_name3 date)");
            statement.execute("INSERT INTO table_name2 VALUES ('value1', 3, 1.3)");
            int count = statement.executeUpdate("INSERT INTO table_name2 VALUES ('value1', 3, 1.3)");
            Assert.assertEquals("Insert returned a number != 1", 0, count);    
            statement.close();
        }
        catch (Throwable e) {
            Assert.fail("Failed to insert into table");
        }
        try {//insert with invalid column
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE table_name5(column_name1 varchar, column_name2 int, column_name3 varchar)");
            statement.executeUpdate(
                    "INSERT INTO table_name5(ffff , column_name3, column_name2) VALUES ('value1', 'value3', 4)");
            Assert.fail("Inserted with invalid column name!!");
            statement.close();
        } catch (SQLException e) {
        } catch (Throwable e) {
            Assert.fail("Invalid Exception thrown");
        }
        try {//insert values are not equals to number of columns
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE table_name5(column_name1 varchar, column_name2 int, column_name3 varchar)");
            statement.executeUpdate(
                    "INSERT INTO table_name5(column_name1 , column_name3, column_name2) VALUES ('value1', '2')");
            Assert.fail("Inserted with invalid column name!!");
            statement.close();
        } catch (SQLException e) {
        } catch (Throwable e) {
            Assert.fail("Invalid Exception thrown");
        }
 
        try {// true insert
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE table_name2(column_name1 varchar, column_name2 int, column_name3 float)");
            int count = statement.executeUpdate("INSERT INTO table_name2 VALUES ('value1', 3, 1.3)");
            count+=statement.executeUpdate("INSERT INTO table_name2 VALUES ('value1', 3, 1.3)");
            count+=statement.executeUpdate("INSERT INTO table_name2 VALUES ('value1', 3, 1.4)");
            Assert.assertEquals("Insert returned a number != 1", 3, count);
            statement.close();
        } catch (Throwable e) {
            Assert.fail("Failed to insert into table");
        }
        connection.close();
 
    }
    @Test //
    public void testUpdate() throws SQLException {
        Connection connection = createUseDatabase("class");
        try {//updated to table not found
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE table_name5(column_name1 varchar, column_name2 int, column_name3 varchar)");
            statement.executeUpdate("INSERT INTO table_name5 VALUES ('value1', 3, 'value1')");
            statement.executeUpdate("INSERT INTO table_name5 VALUES ('value1', 4, 'value1')");
            statement.executeUpdate(
                    "UPDATE table_name6 SET column_name1='1111111111', COLUMN_NAME2=2222222, column_name3='333333333'");
            Assert.fail("Inserted with invalid table!!");
            statement.close();
        } catch (SQLException e) {
        } catch (Throwable e) {
            Assert.fail("Inserted with invalid table!!");
        }
        try {//updated with invalid values
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE table_name5(column_name1 varchar, column_name2 int, column_name3 varchar)");
            statement.executeUpdate("INSERT INTO table_name5 VALUES ('value1', 3, 'value1')");
            statement.executeUpdate("INSERT INTO table_name5 VALUES ('value1', 4, 'value1')");
            statement.executeUpdate(
                    "UPDATE table_name5 SET column_name1='1111111111', column_name2=2ffffffffff5f, column_name3='333333333'");
            Assert.fail("Inserted with invalid value!!");
            statement.close();
        } catch (SQLException e) {
        } catch (Throwable e) {
            Assert.fail("Inserted with invalid value!!");
        }
       
        try {//true updated
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE table_name5(column_name1 varchar, column_name2 int, column_name3 varchar)");
            statement.executeUpdate("INSERT INTO table_name5 VALUES ('value1', 3, 1.3)");
            statement.executeUpdate("INSERT INTO table_name5 VALUES ('value1', 4, 1.7)");
            statement.executeUpdate(
                    "UPDATE table_name5 SET column_name1='1111111111', COLUMN_NAME2=2222222, column_name3='333333333'");
            statement.close();
        } catch (SQLException e) {
       
       
        try {//true update
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE table_name7(column_name1 varchar, column_name2 int, column_name3 varchar)");
            int count1 = statement.executeUpdate(
                    "INSERT INTO table_name7(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)");
            Assert.assertEquals("Insert returned a number != 1", 1, count1);
            int count2 = statement.executeUpdate(
                    "INSERT INTO table_name7(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)");
            Assert.assertEquals("Insert returned a number != 1", 1, count2);
            int count3 = statement.executeUpdate(
                    "INSERT INTO table_name7(column_name1, COLUMN_NAME3, column_NAME2) VALUES ('value2', 'value4', 5)");
            Assert.assertEquals("Insert returned a number != 1", 1, count3);
            int count4 = statement.executeUpdate(
                    "UPDATE table_name7 SET column_name1='1111111111', COLUMN_NAME2=2222222, column_name3='333333333'");
            Assert.assertEquals("Updated returned wrong number", count1 + count2 + count3, count4);
            statement.close();
        } catch (Throwable r) {
            Assert.fail("Failed to update table");
        }
        connection.close();
    }
       
 
}
    @Test //
    public void testConditionalUpdate() throws SQLException {
        Connection connection = createUseDatabase("TestDB_Create");
        try {//true updated
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE table_name5(column_name1 varchar, column_name2 int, column_name3 varchar)");
            statement.executeUpdate("INSERT INTO table_name5 VALUES ('value1', 3, 'value1')");
            statement.executeUpdate("INSERT INTO table_name5 VALUES ('value1', 4, 'value1')");
            statement.executeUpdate(
                    "UPDATE table_name8 SET COLUMN_NAME2=222222, column_name3='1993-10-03' WHERE coLUmn_NAME1='value1'");
            statement.close();
        } catch (SQLException e) {
            Assert.fail("Invalid Exception thrown");
        }
       
        connection.close();
    }
    @Test //
    public void testUpdateEmptyOrInvalidTable() throws SQLException {
        Connection connection = createUseDatabase("TestDB_Create");
        try {
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE table_name9(column_name1 varchar, column_name2 int, column_name3 varchar)");
            int count = statement.executeUpdate(
                    "UPDATE table_name9 SET column_name1='value1', column_name2=15, column_name3='value2'");
            Assert.assertEquals("Updated empty table retruned non-zero count!", 0, count);
            statement.close();
        } catch (Throwable e) {
            Assert.fail("Failed to update table");
        }
 
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(
                    "UPDATE wrong_table_name9 SET column_name1='value1', column_name2=15, column_name3='value2'");
            Assert.fail("empty table");
            statement.close();
        } catch (SQLException e) {
        } catch (Throwable e) {
            Assert.fail("empty table");
        }
        connection.close();
    }
    @Test //
    public void testDelete() throws SQLException {
        Connection connection = createUseDatabase("TestDB_Create");
       
 
 
        try {
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE table_name10(column_name1 varchar, column_name2 int, column_name3 date)");
            int count1 = statement.executeUpdate(
                    "INSERT INTO table_name10(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', '2011-01-25', 4)");
            Assert.assertEquals("Insert returned a number != 1", 1, count1);
            int count2 = statement.executeUpdate(
                    "INSERT INTO table_name10(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', '2011-01-28', 4)");
            Assert.assertEquals("Insert returned a number != 1", 1, count2);
            int count3 = statement.executeUpdate(
                    "INSERT INTO table_name10(column_name1, COLUMN_NAME3, column_NAME2) VALUES ('value2', '2011-02-11', 5)");
            Assert.assertEquals("Insert returned a number != 1", 1, count3);
            int count4 = statement.executeUpdate("DELETE From table_name10");
            Assert.assertEquals("Delete returned wrong number", 3, count4);
            statement.close();
        } catch (Throwable e) {
            Assert.fail("Failed to delete from table");
        }
        connection.close();
    }
 
    @Test //
    public void testConditionalDelete() throws SQLException {
        Connection connection = createUseDatabase("TestDB_Create");
        try {
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE table_name11(column_name1 varchar, column_name2 int, column_name3 DATE)");
            int count1 = statement.executeUpdate(
                    "INSERT INTO table_name11(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', '2011-01-25', 4)");
            Assert.assertEquals("Insert returned a number != 1", 1, count1);
            int count2 = statement.executeUpdate(
                    "INSERT INTO table_name11(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', '2010-06-30', 4)");
            Assert.assertEquals("Insert returned a number != 1", 1, count2);
            int count4 = statement.executeUpdate("DELETE From table_name11  WHERE coLUmn_NAME3>'2011-01-25'");
            Assert.assertEquals("Delete returned wrong number", 0, count4);
           
            statement.close();
        } catch (Throwable e) {
            Assert.fail("Failed to delete from table");
        }
        connection.close();
    }
    @Test //
    public void testSelect() throws SQLException {
        Connection connection = createUseDatabase("TestDB_Create");
        try {
            Statement statement = connection.createStatement();
            statement
                    .execute("CREATE TABLE table_name12(column_name1 varchar, column_name2 int, column_name3 varchar)");
            int count1 = statement.executeUpdate(
                    "INSERT INTO table_name12(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)");
            Assert.assertEquals("Insert returned a number != 1", 1, count1);
            int count2 = statement.executeUpdate(
                    "INSERT INTO table_name12(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)");
            Assert.assertEquals("Insert returned a number != 1", 1, count2);
            int count3 = statement.executeUpdate(
                    "INSERT INTO table_name12(column_name1, COLUMN_NAME3, column_NAME2) VALUES ('value2', 'value4', 5)");
            Assert.assertEquals("Insert returned a number != 1", 1, count3);
            int count4 = statement.executeUpdate(
                    "INSERT INTO table_name12(column_name1, COLUMN_NAME3, column_NAME2) VALUES ('value5', 'value6', 6)");
            Assert.assertEquals("Insert returned a number != 1", 1, count4);
            ResultSet result = statement.executeQuery("SELECT * From table_name12");
            int rows = 0;
            while (result.next())
                rows++;
            Assert.assertNotNull("Null result retruned", result);
            Assert.assertEquals("Wrong number of rows", 4, rows);
            Assert.assertEquals("Wrong number of columns", 3, result.getMetaData().getColumnCount());
            statement.close();
        } catch (Throwable e) {
            Assert.fail("Failed to select from table");
        }
        connection.close();
    }
 
 
 
    }