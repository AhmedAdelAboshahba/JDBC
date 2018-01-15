package database.management;

import java.util.LinkedList;

import parserForSQL.Validity;
import saving.SaveXML;

public class Main {

	public static void main(String[] args) {

		// TODO Auto-generated method stub
		/*
		 * SaveXML obj = new SaveXML(); //
		 * System.out.println(obj.getSavedList());
		 * //System.out.println(obj.loadXML(obj.getSavedList()));
		 * ArgumentValidation obje=new ArgumentValidation();
		 * LinkedList<LinkedList<LinkedList<String>>> allTabls=new
		 * LinkedList<>(); allTabls=obj.loadXML(obj.getSavedList()); //
		 * System.out.println(allTabls);
		 * obje.load(obj.loadXML(obj.getSavedList()));
		 */
	/*	Validity parse = new Validity();
		parse.validOrNot("create database one");
		parse.validOrNot("use one");
		parse.validOrNot("create table h ( col1 varchar ,col2 float)");
		parse.validOrNot("insert into h values ('ahmed' , 10)");
		parse.validOrNot("insert into h values ('ahmed' , 210.0005)");
		parse.validOrNot("insert into h values ('ahmed' , 210.0006)");
		parse.validOrNot("select col1,col2 from h");
		parse.validOrNot("update h set col1=ahmed2,col2=300.55 where col2>  210");
		parse.validOrNot("select col1,col2 from h");
		parse.validOrNot("delete from h where col2 < 210");
		parse.validOrNot("select col1,col2 from h");

		// parse.validOrNot("use one");

		// parse.validOrNot("insert into h values ('neho' , 98)");
		/*
		 * Validity parse = new Validity();
		 * 
		 * LinkedList<String> columnNames = new LinkedList<>();
		 * LinkedList<String> typeNames = new LinkedList<>(); LinkedList<String>
		 * values = new LinkedList<>(); values.add("st"); values.add("11");
		 * values.add("11");
		 * 
		 * columnNames.add("col1"); columnNames.add("col2");
		 * columnNames.add("col3");
		 * 
		 * typeNames.add("varchar"); typeNames.add("int"); typeNames.add("int");
		 * parse.validOrNot("create database one"); parse.validOrNot("use one");
		 * parse.validOrNot("create table h ( col1 varchar ,col2 int)");
		 * parse.validOrNot("insert into h values ('ahmed' , 15)");
		 * parse.validOrNot("insert into h values ('48' , 45)");
		 * parse.validOrNot("insert into h (col2,col1) values ('48' , 45)");
		 * parse.validOrNot("select col1,col2 from h where col1=ahmed");
		 * //parse.validOrNot("update h set col1=fady , col2=10");
		 * parse.validOrNot("delete from h where col1=ahmed");
		 * 
		 * 
		 * 
		 * 
		 * /* parse.validOrNot("drop table h"); parse.validOrNot(
		 * "drop database one");
		 * 
		 * //ArgumentValidation A = new ArgumentValidation();
		 * //A.creatDataBase("DB_ONE"); // A.use("DB_To"); //A.use("DB_ONE");
		 * //A.creatTable("table1", columnNames, typeNames); //
		 * A.creatTable("table2", columnNames, typeNames); //
		 * A.dropTable("table1");
		 * 
		 * //A.insertIntoTable("table2", values); /* values = new
		 * LinkedList<>(); values.add("st"); values.add("11");
		 * values.add("10000"); A.insertIntoTable("table2", values); //
		 * A.insertIntoTable("table1", values); // A.selectAll("table2");
		 * A.insertIntoTable("table2", values); // A.selectAll("table2");
		 * columnNames = new LinkedList<>(); //columnNames.add("col3");
		 * columnNames.add("col1"); columnNames.add("col2"); LinkedList<String>
		 * newValues=new LinkedList<>(); newValues.add("fady");
		 * newValues.add("50"); A.updateTable("table2", "col3", "=", "10000",
		 * columnNames, newValues); A.selectAll("table2"); //
		 * A.selectAllByCondition("table2", "col3", "11", "<"); columnNames=new
		 * LinkedList<>(); columnNames.add("col1"); columnNames.add("col3");
		 * A.selectFromByCondition("table2", "col2", "11", ">", columnNames);
		 * A.deleteAll("table2"); A.selectAll("table2"); values = new
		 * LinkedList<>(); values.add("st"); values.add("11");
		 * values.add("10000"); A.insertIntoTable("table2", values);
		 * A.selectAll("table2"); values = new LinkedList<>();
		 * values.add("200"); values.add("11"); values.add("ahs"); columnNames =
		 * new LinkedList<>(); columnNames.add("col3"); columnNames.add("col2");
		 * columnNames.add("col1"); A.insertByColumn("table2", values,
		 * columnNames); A.selectAll("table2");
		 * 
		 * values = new LinkedList<>(); values.add("59"); values.add("go");
		 * columnNames = new LinkedList<>(); columnNames.add("col3");
		 * columnNames.add("col1"); A.updateSelectedColumns("table2", values,
		 * columnNames);
		 * 
		 * A.selectAll("table2");
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * //A.deleteFromTable("table2", "col3", "10000", "="); //
		 * A.selectAll("table2"); //A.selectFromTable("table2",columnNames); /*
		 * values = new LinkedList<>(); values.add("st"); values.add("10");
		 * values.add("20"); A.insertIntoTable("table2", values); values = new
		 * LinkedList<>(); values.add("st"); values.add("66"); values.add("11");
		 * A.insertIntoTable("table2", values); A.selectAll("table2");
		 * //A.deleteFromTable("table2", "col3", "11", "="); values = new
		 * LinkedList<>(); values.add("col3"); values.add("col2");
		 * //values.add("col1"); A.creatTable("table1", columnNames, typeNames);
		 * A.selectFromTable("table2", values); values = new LinkedList<>();
		 * values.add("st"); values.add("66"); values.add("11");
		 * A.insertIntoTable("table1", values);
		 */

		// A.insertIntoTable("table2", values);
		// A.insertIntoTable("table2", values);

		// System.out.println(A.dbManage.getDataBases().get(0).getTables().get(0).getColumns().get(0).getValues());
		// System.out.println(A.dbManage.getDataBases().get(0).getTables().get(0).getColumns().get(1).getValues());
		// System.out.println(A.dbManage.getDataBases().get(0).getTables().get(0).getColumns().get(2).getValues());
	}

}
