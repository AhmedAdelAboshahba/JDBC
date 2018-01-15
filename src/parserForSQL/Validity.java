package parserForSQL;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import database.management.ArgumentValidation;
import database.management.DataBaseManagement;
import saving.SaveXML;

public class Validity {
	private String[] parts;
	private String expression;
	private String operator;
	private String dataBaseName;
	private String tableName;
	private String columnToBeChecked;
	private String valueToBeChecked;
	private String columnToBeAdded;
	private String columnTypeToBeAdded;
	private LinkedList<String> columnName = new LinkedList<>();
	private LinkedList<String> columnType = new LinkedList<>();
	private LinkedList<String> valuesList = new LinkedList<>();
	private boolean checkValidity;
	private ParsingToJava ParsingToJavaObject = new ParsingToJava();
	private ArgumentValidation parsingObject;
	private String finalPath;
	private String finalSave;

	public Validity(String path, String url) {
		this.finalSave = url;
		this.finalPath = path;
		if (url.toLowerCase().contains("xmldb")) {
			finalSave = "xml";
		} else {
			finalSave = "json";
		}
		this.parsingObject = new ArgumentValidation(path, finalSave);
	}

	public int getAffectedRows() {
		return parsingObject.getAffectedRows();
	}

	public String getTableName() {
		return tableName;
	}

	public int getIndexOfDbUsed() {
		return parsingObject.getIndexOfDbUsed();
	}

	public DataBaseManagement getDbManage() {
		return parsingObject.getDbManage();
	}

	public LinkedList<LinkedList<String>> getSelected() {
		return parsingObject.getSelectedLinkedList();
	}

	public void validOrNot(String command) throws SQLException {
		expression = command;
		expression = expression.replaceAll("( )+", " ");
		expression = expression.trim();
		if (createDataBaseValidity() == true) {
			// done without changes ,final
			parsingObject.creatDataBase(dataBaseName);
		} else if (distinctValidityWithoutCondition() == true) {
			parsingObject.selectDistinct(tableName, columnName);

		} else if (distinctValidity() == true) {
			// final
			parsingObject.selectDistinctByCondition(tableName, columnName, columnToBeChecked, valueToBeChecked,
					operator);
		} else if (createTableValidity() == true) {

			// done with changes in create table validity and
			// setColumnsToBeCreated ,final
			parsingObject.creatTable(tableName, columnName, columnType);
		} else if (insertIntoValidity() == true) {
			// done with changes in insert into and values to be inserted,final
			parsingObject.insertIntoTable(tableName, valuesList);

		} else if (deleteWithoutCondition() == true) {
			// done ,final
			parsingObject.deleteAll(tableName);

		} else if (deleteValidity() == true) {
			// done ,final
			parsingObject.deleteFromTable(tableName, columnToBeChecked, valueToBeChecked, operator);

		} else if (dropDataBaseValidity() == true) {
			// done without changes,final
			parsingObject.dropDataBase(dataBaseName);
		} else if (dropTableValidity() == true) {
			// done without changes,final
			parsingObject.dropTable(tableName);
		} else if (selectAllWithCondition() == true) {
			// done,final
			parsingObject.selectAllByCondition(tableName, columnToBeChecked, valueToBeChecked, operator);

		} else if (selectWithCondition() == true) {
			// done,final
			parsingObject.selectFromByCondition(tableName, columnToBeChecked, valueToBeChecked, operator, columnName);

		} else if (selectAllValidity() == true) {
			// done,final
			parsingObject.selectAll(tableName);
		} else if (select() == true) {
			// done,final
			parsingObject.selectFromTable(tableName, columnName);
		} else if (updateTableValidity() == true) {
			// still without condition ,final
			parsingObject.updateTable(tableName, columnToBeChecked, operator, valueToBeChecked, columnName, valuesList);
		} else if (updateTableWithoutCondition() == true) {
			parsingObject.updateSelectedColumns(tableName, valuesList, columnName);

		} else if (useDataBaseValidity() == true) {
			// done without changes,final
			parsingObject.use(dataBaseName);
		} else if (insertIntoWithColumnsValidity() == true) {

			// done,final
			parsingObject.insertByColumn(tableName, valuesList, columnName);

		} else if (addColumnValidity() == true) {
			// final
			parsingObject.addColumn(tableName, columnToBeAdded, columnTypeToBeAdded);

		} else if (dropColumnValidity() == true) {
			// final
			parsingObject.dropColumn(tableName, columnToBeAdded);

		} else {
			throw new SQLException("Invalid SQL");
		}
	}

	public boolean createDataBaseValidity() {
		checkValidity = Pattern.matches("[cC][rR][Ee][aA][Tt][Ee]\\s[Dd][Aa][Tt][Aa][Bb][aA][sS][eE]\\s\\w+",
				expression);
		if (checkValidity == true) {
			dataBaseName = ParsingToJavaObject.findDataBaseName(expression);
		}
		return checkValidity;
	}

	public boolean createTableValidity() {
		Pattern columns = Pattern.compile(
				"(\\s*\\w+\\s([iI][Nn][Tt]|[Vv][Aa][Rr][Cc][Hh][Aa][Rr]|[Dd][Aa][Tt][Ee]|[Ff][Ll][Oo][Aa][Tt])\\s*,)*\\s*\\w+\\s([iI][Nn][Tt]|[Vv][Aa][Rr][Cc][Hh][Aa][Rr]|[Dd][Aa][Tt][Ee]|[Ff][Ll][Oo][Aa][Tt])");
		checkValidity = Pattern.matches(
				"[cC][rR][Ee][aA][Tt][Ee]\\s[Tt][Aa][Bb][Ll][eE]\\s\\w+\\s*\\(" + columns.toString() + "\\s*\\)",
				expression);
		if (checkValidity == true) {
			// search for table name
			tableName = ParsingToJavaObject.findTableName(expression);
			// search for column names and types
			ParsingToJavaObject.setColumnsToBeCreated(expression);
			columnName = ParsingToJavaObject.getColumnName();
			columnType = ParsingToJavaObject.getColumnType();
		}
		return checkValidity;
	}

	public boolean insertIntoValidity() {
		// modified
		Pattern values = Pattern.compile("((.+),)*(.+)");
		checkValidity = Pattern
				.matches("[Ii][Nn][sS][eE][Rr][tT]\\s[Ii][Nn][Tt][Oo]\\s\\w+\\s[Vv][Aa][Ll][Uu][Ee][Ss]\\s*\\(\\s*"
						+ values.toString() + "\\s*\\)", expression);
		if (checkValidity == true) {
			tableName = ParsingToJavaObject.tableNameToBeInsertedAt(expression);
			// search for values to be inserted
			valuesList = ParsingToJavaObject.valuesToBeInserted(expression);
		}
		return checkValidity;
	}

	public boolean deleteValidity() {
		checkValidity = Pattern.matches(
				"[Dd][Ee][Ll][Ee][Tt][Ee]\\s[Ff][Rr][oO][Mm](.+)\\s[Ww][Hh][eE][rR][eE]\\s\\w+\\s*(=||>||<)(.+)",
				expression);
		if (checkValidity == true) {
			// search for table name
			tableName = ParsingToJavaObject.tableNameToBeDeleted(expression);
			// search for operator
			operator = ParsingToJavaObject.findOperator(expression);
			// search for value and column name
			ParsingToJavaObject.findTheCondition(expression);
			columnToBeChecked = ParsingToJavaObject.getColumnName().getFirst();
			valueToBeChecked = ParsingToJavaObject.getValuesList().getFirst();

		}
		return checkValidity;
	}

	public boolean dropDataBaseValidity() {
		checkValidity = Pattern.matches("[Dd][Rr][Oo][Pp]\\s[Dd][Aa][Tt][Aa][Bb][aA][sS][eE]\\s\\w+", expression);
		// search for database name
		if (checkValidity == true) {
			dataBaseName = ParsingToJavaObject.findDataBaseName(expression);
		}
		return checkValidity;
	}

	public boolean dropTableValidity() {
		checkValidity = Pattern.matches("[Dd][Rr][Oo][Pp]\\s[Tt][Aa][Bb][Ll][eE]\\s\\w+", expression);
		// search for table name
		if (checkValidity == true) {
			tableName = ParsingToJavaObject.findTableName(expression);
		}
		return checkValidity;
	}

	public boolean selectAllValidity() {
		checkValidity = Pattern.matches("[Ss][Ee][Ll][Ee][cC][tT]\\s*\\*\\s*[Ff][Rr][oO][Mm]\\s\\w+", expression);
		// search for table name
		if (checkValidity == true) {
			tableName = ParsingToJavaObject.tableNameToBeDeleted(expression);
		}
		return checkValidity;
	}

	public boolean select() {
		Pattern columns = Pattern.compile("((.+),)*(.+)");
		checkValidity = Pattern
				.matches("[Ss][Ee][Ll][Ee][cC][tT]\\s" + columns.toString() + "\\s[Ff][Rr][oO][Mm]\\s\\w+", expression);
		if (checkValidity == true) {
			// search table name
			tableName = ParsingToJavaObject.tableNameToBeDeleted(expression);
			// search columns name
			ParsingToJavaObject.setColumnsToBeSelected(expression);
			columnName = ParsingToJavaObject.getColumnName();

		}
		return checkValidity;
	}

	public boolean updateTableValidity() {
		Pattern equating = Pattern.compile("((.+)=(.+),)*(.+)=(.+)");
		checkValidity = Pattern.matches(
				"[uU][pP][dD][aA][tT][eE]\\s\\w+\\s[sS][eE][tT]\\s" + equating + "[Ww][Hh][eE][rR][eE](.+)(=|>|<)(.+)",
				expression);
		// search for table name
		if (checkValidity == true) {
			parts = expression.split("[Ww][Hh][eE][rR][eE]");
			tableName = ParsingToJavaObject.tableToBeUpdated(expression);
			// search for operator
			operator = ParsingToJavaObject.findOperator(expression);
			// search for condtion
			ParsingToJavaObject.findTheCondition(expression);
			columnToBeChecked = ParsingToJavaObject.getColumnName().getFirst();
			valueToBeChecked = ParsingToJavaObject.getValuesList().getFirst();
			// search for lists of columns and values
			ParsingToJavaObject.columnsUpdatedWithValues(parts[0].trim());
			valuesList = ParsingToJavaObject.getValuesList();
			columnName = ParsingToJavaObject.getColumnName();
		}
		return checkValidity;
	}

	public boolean useDataBaseValidity() {
		checkValidity = Pattern.matches("[Uu][Ss][Ee]\\s\\w+", expression);
		if (checkValidity == true) {
			dataBaseName = ParsingToJavaObject.findDataBaseName(expression);
		}

		return checkValidity;
	}

	public boolean addColumnValidity() {
		checkValidity = Pattern.matches(
				"[Aa][Ll][Tt][Ee][Rr]\\s[Tt][Aa][Bb][Ll][Ee]\\s\\w+\\s[Aa][Dd][Dd]\\s\\w+\\s([iI][Nn][Tt]|[Vv][Aa][Rr][Cc][Hh][Aa][Rr]|[Dd][Aa][Tt][Ee]|[Ff][Ll][Oo][Aa][Tt])",
				expression);
		if (checkValidity == true) {
			tableName = ParsingToJavaObject.findTableName(expression);
			columnToBeAdded = ParsingToJavaObject.columnsToBeAdded(expression);
			columnTypeToBeAdded = ParsingToJavaObject.columnType(expression);
		}
		return checkValidity;
	}

	public boolean dropColumnValidity() {
		checkValidity = Pattern.matches(
				"[Aa][Ll][Tt][Ee][Rr]\\s[Tt][Aa][Bb][Ll][Ee]\\s\\w+\\s[Dd][rR][Oo][Pp]\\s[cC][oO][lL][Uu][Mm][Nn]\\s\\w+",
				expression);
		if (checkValidity == true) {
			tableName = ParsingToJavaObject.findTableName(expression);
			columnToBeAdded = ParsingToJavaObject.columnsToBeDeleted(expression);
		}
		return checkValidity;
	}

	private boolean insertIntoWithColumnsValidity() {
		Pattern values = Pattern.compile("((.+),)*(.+)");
		Pattern columns = Pattern.compile("((.+),)*(.+)");
		checkValidity = Pattern.matches("[Ii][Nn][sS][eE][Rr][tT]\\s[Ii][Nn][Tt][Oo]\\s\\w+\\s*\\(" + columns.toString()
				+ "\\)\\s*[Vv][Aa][Ll][Uu][Ee][Ss]\\s*\\(" + values.toString() + "\\)", expression);
		if (checkValidity == true) {
			parts = expression.split("[Vv][Aa][Ll][Uu][Ee][Ss]");
			tableName = ParsingToJavaObject.tableNameToBeInsertedAt(expression);
			// search for values to be inserted
			columnName = ParsingToJavaObject.valuesToBeInserted(parts[0].trim());
			valuesList = ParsingToJavaObject.valuesToBeInserted(parts[1].trim());
		}

		return checkValidity;
	}

	private boolean selectWithCondition() {
		Pattern columns = Pattern.compile("((.+),)*(.+)");
		Pattern condition = Pattern.compile("(.+)(=|>|<)(.+)");
		checkValidity = Pattern.matches("[Ss][Ee][Ll][Ee][cC][tT]\\s" + columns.toString()
				+ "\\s[Ff][Rr][oO][Mm]\\s\\w+\\s*[Ww][Hh][eE][rR][eE]" + condition.toString(), expression);
		if (checkValidity == true) {
			// search table name
			tableName = ParsingToJavaObject.tableNameToBeDeleted(expression);
			// search columns name
			ParsingToJavaObject.setColumnsToBeSelected(expression);
			columnName = ParsingToJavaObject.getColumnName();
			operator = ParsingToJavaObject.findOperator(expression);
			// search for value and column name
			ParsingToJavaObject.findTheCondition(expression);
			columnToBeChecked = ParsingToJavaObject.getColumnName().getFirst();
			valueToBeChecked = ParsingToJavaObject.getValuesList().getFirst();

		}
		return checkValidity;
	}

	private boolean selectAllWithCondition() {
		Pattern condition = Pattern.compile("(.+)(=|>|<)(.+)");
		checkValidity = Pattern
				.matches("[Ss][Ee][Ll][Ee][cC][tT]\\s*\\*\\s*[Ff][Rr][oO][Mm]\\s\\w+\\s*[Ww][Hh][eE][rR][eE]"
						+ condition.toString(), expression);
		if (checkValidity == true) {
			// search table name
			tableName = ParsingToJavaObject.tableNameToBeDeleted(expression);
			operator = ParsingToJavaObject.findOperator(expression);
			// search for value and column name
			ParsingToJavaObject.findTheCondition(expression);
			columnToBeChecked = ParsingToJavaObject.getColumnName().getFirst();
			valueToBeChecked = ParsingToJavaObject.getValuesList().getFirst();

		}
		return checkValidity;
	}

	private boolean deleteWithoutCondition() {
		checkValidity = Pattern.matches("[Dd][Ee][Ll][Ee][Tt][Ee]\\s[Ff][Rr][oO][Mm]\\s\\w+", expression);
		if (checkValidity == true) {
			// search table name
			tableName = ParsingToJavaObject.tableNameToBeDeleted(expression);
		}
		return checkValidity;
	}

	private boolean updateTableWithoutCondition() {
		Pattern equating = Pattern.compile("((.+)=(.+),)*(.+)=(.+)");
		checkValidity = Pattern.matches("[uU][pP][dD][aA][tT][eE]\\s\\w+\\s[sS][eE][tT]\\s" + equating, expression);
		// search for table name
		if (checkValidity == true) {
			tableName = ParsingToJavaObject.tableToBeUpdated(expression);
			// search for lists of columns and values
			ParsingToJavaObject.columnsUpdatedWithValues(expression);
			valuesList = ParsingToJavaObject.getValuesList();
			columnName = ParsingToJavaObject.getColumnName();
		}
		return checkValidity;
	}

	private boolean distinctValidity() {
		Pattern columns = Pattern.compile("((.+),)*(.+)");
		Pattern condition = Pattern.compile("(.+)(=|>|<)(.+)");
		checkValidity = Pattern
				.matches(
						"[Ss][Ee][Ll][Ee][cC][tT]\\s[Dd][iI][Ss][Tt][Ii][Nn][Cc][Tt]" + columns.toString()
								+ "\\s[Ff][Rr][oO][Mm]\\s\\w+\\s*[Ww][Hh][eE][rR][eE]" + condition.toString(),
						expression);
		if (checkValidity == true) {
			// search table name
			tableName = ParsingToJavaObject.tableNameToBeDeleted(expression);
			// search columns name
			ParsingToJavaObject.setColumnsToBeSelected(expression);
			columnName = ParsingToJavaObject.getColumnName();
			operator = ParsingToJavaObject.findOperator(expression);
			// search for value and column name
			ParsingToJavaObject.findTheCondition(expression);
			columnToBeChecked = ParsingToJavaObject.getColumnName().getFirst();
			valueToBeChecked = ParsingToJavaObject.getValuesList().getFirst();

		}
		return checkValidity;
	}

	private boolean distinctValidityWithoutCondition() {
		Pattern columns = Pattern.compile("((.+),)*(.+)");
		checkValidity = Pattern.matches("[Ss][Ee][Ll][Ee][cC][tT]\\s[Dd][iI][Ss][Tt][Ii][Nn][Cc][Tt]"
				+ columns.toString() + "\\s[Ff][Rr][oO][Mm]\\s\\w+", expression);

		if (checkValidity == true) {
			// search table name
			tableName = ParsingToJavaObject.tableNameToBeDeleted(expression);
			// search columns name
			ParsingToJavaObject.setColumnsToBeSelected(expression);
			columnName = ParsingToJavaObject.getColumnName();

		}
		return checkValidity;
	}
}
