package parserForSQL;

import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

import jdbc.DriverManager;

public class main {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		String protocol = "altdb";
		 String tmp = System.getProperty("java.io.tmpdir");

		Driver driver = new DriverManager();
		Properties info = new Properties();
		File dbDir = new File(tmp + File.separator + "jdbc" + File.separator + Math.round((((float) Math.random()) * 100000)));
		info.put("path", "D:\\2nd year");
		Connection connect = driver.connect("jdbc:" + protocol + "://localhost", info);
		Statement st;
		st=connect.createStatement();
		Scanner scan = new Scanner(System.in);
		String number="0";
        String sql;
//        while(!number.equals("-1")){
//        	number=scan.nextLine();
//        	if(number.equals("1") ){
//        		sql = scan.nextLine();
//        		st.execute(sql);
//        	}
//        	else if(number.equals("2")){
//        		sql = scan.nextLine();
//        		st.executeUpdate(sql);
//        	}else if(number.equals("3")){
//        		sql = scan.nextLine();
//        		st.executeQuery(sql);
//        	}else if(number.equals("4")){
//        		st.executeBatch();
//        	}
//        	else if(number.equals("5")){
//        		sql = scan.nextLine();
//        		st.addBatch(sql);
//        	}
//        }
		st.execute("CREATE DATABASE dbone");
		st.execute("use dbone");
		st.execute("create table table1 (col1 varchar,col2 int , col3 varchar)");
		st.executeUpdate("insert into table1 values('value1',3,1.3)");
		st.executeUpdate("insert into table1 values('value',4,5.5)");
	//	st.executeUpdate("update table1 set col1='11111',col2=2f,col3='3434'");
		//st.execute("select col1,col2,col3 from table1");
		//st.execute("update table1 set col1 = three ,col3 = 2010-6-6 where col2>1");
		//ResultSet rs = st.getResultSet();
		//while(rs.next())
		//System.out.println(rs.getInt("col2"));
		/*
 Validity parser = new Validity("a","b");
 parser.validOrNot("create database db " );
 parser.validOrNot("use db" );
 parser.validOrNot("create table TablenAme (col1 varchar)" );
 parser.validOrNot("Alter table Tablename drop column col1 " );
 parser.validOrNot("Alter table Tablename drop column col1 " );
 */
	}

}
