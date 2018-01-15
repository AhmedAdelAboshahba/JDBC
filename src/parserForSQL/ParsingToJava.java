package parserForSQL;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParsingToJava implements ParserInterface {
	private String operator;
	private StringBuilder dataBaseName = new StringBuilder();
	private StringBuilder tableName = new StringBuilder();
	private StringBuilder columnSentence = new StringBuilder();
	private StringBuilder valuesSentence = new StringBuilder();
	private StringBuilder valueToBeChecked = new StringBuilder();
	private StringBuilder columnToBeChecked = new StringBuilder();
	private StringBuilder columnToBeAddedOrDeleted= new StringBuilder();
	private StringBuilder columnTypeToBeAdded= new StringBuilder();
	private LinkedList<String>columnName = new LinkedList<>();
	private LinkedList<String>columnType = new LinkedList<>();
	private LinkedList<String>valuesList = new LinkedList<>();
	private String[] parts;
	private String temp;
	private Pattern word;
	private Matcher matcher;
	private String[] temp2;

	public ParsingToJava() {

	}

	public String findDataBaseName(String expression) {
		// TODO Auto-generated method stub
		word = Pattern.compile("\\w+$");
		matcher = word.matcher(expression.trim());
		dataBaseName = new StringBuilder();
		while (matcher.find()) {
			dataBaseName.append(matcher.group(0));
		}
		return dataBaseName.toString();
	}

	public String findTableName(String expression) {
		// TODO Auto-generated method stub
		word = Pattern.compile("[Tt][Aa][Bb][Ll][eE]\\s\\w+");
		matcher = word.matcher(expression);
		tableName = new StringBuilder();
		while (matcher.find()) {
			tableName.append(matcher.group(0));
		}
		temp = tableName.toString().replaceAll("\\(", " ");
		word = Pattern.compile("\\w+$");
		matcher = word.matcher(temp);
		tableName = new StringBuilder();
		while (matcher.find()) {
			tableName.append(matcher.group(0));
		}
		return tableName.toString();
	}

	public void setColumnsToBeCreated(String expression) {
		// TODO Auto-generated method stub
		Pattern columns = Pattern.compile(
				"(\\s*\\w+\\s([iI][Nn][Tt]|[Vv][Aa][Rr][Cc][Hh][Aa][Rr]|[Dd][Aa][Tt][Ee]|[Ff][Ll][Oo][Aa][Tt])\\s*,)*\\s*\\w+\\s([iI][Nn][Tt]|[Vv][Aa][Rr][Cc][Hh][Aa][Rr]|[Dd][Aa][Tt][Ee]|[Ff][Ll][Oo][Aa][Tt])");
		word = Pattern.compile("\\(" + columns + "\\)");
		matcher = word.matcher(expression);
		columnSentence = new StringBuilder();
		columnName = new LinkedList<>();
		columnType = new LinkedList<>();
		while (matcher.find()) {
			columnSentence.append(matcher.group(0));
		}
		columnSentence.deleteCharAt(0);
		columnSentence.deleteCharAt(columnSentence.length() - 1);
		parts = columnSentence.toString().split(",");
		for (int counter = 0; counter < parts.length; counter++) {
			parts[counter] = parts[counter].trim();
			word = Pattern.compile("^\\w+");
			matcher = word.matcher(parts[counter]);
			columnSentence = new StringBuilder();
			while (matcher.find()) {
				columnSentence.append(matcher.group(0));
			}
			columnName.add(columnSentence.toString());
			word = Pattern.compile("[iI][Nn][Tt]|[Vv][Aa][Rr][Cc][Hh][Aa][Rr]|[Dd][Aa][Tt][Ee]|[Ff][Ll][Oo][Aa][Tt]");
			matcher = word.matcher(parts[counter]);
			columnSentence = new StringBuilder();
			while (matcher.find()) {
				columnSentence.append(matcher.group(0));
			}
			columnType.add(columnSentence.toString().replaceAll(" ", "").toLowerCase());

		}

	}

	public LinkedList<String> getColumnName() {
		// TODO Auto-generated method stub
		return columnName;
	}

	public LinkedList<String> getColumnType() {
		// TODO Auto-generated method stub
		return columnType;
	}

	public String tableNameToBeInsertedAt(String expression) {
		// TODO Auto-generated method stub
		word = Pattern.compile("[Ii][Nn][Tt][Oo]\\s\\w+");
		matcher = word.matcher(expression);
		tableName = new StringBuilder();
		while (matcher.find()) {
			tableName.append(matcher.group(0));
		}
		parts = tableName.toString().split(" ");
		return parts[1].trim();
	}

	public LinkedList<String> valuesToBeInserted(String expression) {
		// TODO Auto-generated method stub
		valuesList = new LinkedList<>();
		valuesSentence = new StringBuilder();
		Pattern values = Pattern.compile("((.+),)*(.+)");
		word = Pattern.compile("\\(" + values + "\\)");
		matcher = word.matcher(expression);
		while (matcher.find()) {
			valuesSentence.append(matcher.group(0));
		}
		valuesSentence.deleteCharAt(0);
		valuesSentence.deleteCharAt(valuesSentence.length() - 1);
		parts = valuesSentence.toString().split(",");
		for (int counter = 0; counter < parts.length; counter++) {
			parts[counter] = parts[counter].replaceAll("'", " ");
			valuesList.add(parts[counter].trim());
		}
		return valuesList;
	}

	public String tableNameToBeDeleted(String expression) {
		// TODO Auto-generated method stub
		word = Pattern.compile("[Ff][Rr][oO][Mm]\\s\\w+");
		matcher = word.matcher(expression);
		tableName = new StringBuilder();
		while (matcher.find()) {
			tableName.append(matcher.group(0));
		}
		word = Pattern.compile("\\w+$");
		matcher = word.matcher(tableName);
		tableName = new StringBuilder();
		while (matcher.find()) {
			tableName.append(matcher.group(0));
		}
		return tableName.toString();
	}

	public String findOperator(String expression) {
		// TODO Auto-generated method stub
		word = Pattern.compile("[Ww][Hh][eE][rR][eE]\\s\\w+\\s*(=||>||<)\\s*(.+)");
		matcher = word.matcher(expression);
		columnSentence = new StringBuilder();
		while (matcher.find()) {
			columnSentence.append(matcher.group(0));
		}
		if (columnSentence.toString().contains("=") == true) {
			operator = "=";
		} else if (columnSentence.toString().contains(">") == true) {
			operator = ">";
		} else if (columnSentence.toString().contains("<") == true) {
			operator = "<";
		}
		return operator;
	}

	public void findTheCondition(String expression) {
		// TODO Auto-generated method stub
		word = Pattern.compile("[Ww][Hh][eE][rR][eE]\\s\\w+\\s*(=||>||<)\\s*(.+)");
		matcher = word.matcher(expression);
		columnSentence = new StringBuilder();
		while (matcher.find()) {
			columnSentence.append(matcher.group(0));
		}
		parts = columnSentence.toString().split(operator);
		word = Pattern.compile("\\w+$");
		matcher = word.matcher(parts[0].trim());
		columnSentence = new StringBuilder();
		while (matcher.find()) {
			columnSentence.append(matcher.group(0));
		}
		parts[1] = parts[1].replaceAll("'", " ");
		columnName = new LinkedList<>();
		valuesList = new LinkedList<>();
		columnName.add(columnSentence.toString());
		valuesList.add(parts[1].trim());
	}

	public LinkedList<String> getValuesList() {
		// TODO Auto-generated method stub
		return valuesList;
	}

	public String tableToBeSelected(String expression) {
		// TODO Auto-generated method stub

		return null;
	}

	public void setColumnsToBeSelected(String expression) {
		// TODO Auto-generated method stub
		parts = expression.split("[Ff][Rr][Oo][Mm]");
		parts = parts[0].split(",");
		word = Pattern.compile("\\w+$");
		matcher = word.matcher(parts[0].trim());
		columnSentence = new StringBuilder();
		while (matcher.find()) {
			columnSentence.append(matcher.group(0));
		}
		parts[0] = columnSentence.toString();
		columnName = new LinkedList<>();
		for (int counter = 0; counter < parts.length; counter++) {
			columnName.add(parts[counter].trim());
		}
	}

	public String tableToBeUpdated(String expression) {
		// TODO Auto-generated method stub
		word = Pattern.compile("[uU][pP][dD][aA][tT][eE]\\s\\w+\\s[sS][eE][tT]");
		matcher = word.matcher(expression);
		tableName = new StringBuilder();
		while (matcher.find()) {
			tableName.append(matcher.group(0));
		}
		parts = tableName.toString().split(" ");
		tableName = new StringBuilder(parts[1].trim());
		return tableName.toString();
	}

	public void columnsUpdatedWithValues(String expression) {
		// TODO Auto-generated method stub
		word = Pattern.compile("([sS][eE][tT](((.+)=(.+),)*(.+)=(.+)))");
		matcher = word.matcher(expression);
		columnSentence = new StringBuilder();
		while (matcher.find()) {
			columnSentence.append(matcher.group(2));
		}
		parts = columnSentence.toString().split(",");
		columnName = new LinkedList<>();
		valuesList = new LinkedList<>();
		for (int counter = 0; counter < parts.length; counter++) {
			temp2 = parts[counter].split("=");
			columnName.add(temp2[0].trim());
			temp2[1] = temp2[1].replaceAll("'", "");
			valuesList.add(temp2[1].trim());
		}

	}

	public String columnsToBeDeleted(String expression) {
		// TODO Auto-generated method stub
		word = Pattern.compile("([Aa][Dd][Dd]|[Dd][Rr][Oo][Pp])\\s[cC][Oo][Ll][Uu][Mm][Nn]\\s\\w+");
		matcher = word.matcher(expression);
		columnToBeAddedOrDeleted = new StringBuilder();
		while (matcher.find()) {
			columnToBeAddedOrDeleted.append(matcher.group(0));
		}
		parts = columnToBeAddedOrDeleted.toString().split(" ");
		return parts[2];
	}
	public String columnsToBeAdded(String expression) {
		// TODO Auto-generated method stub
		word = Pattern.compile("[Aa][Dd][Dd]\\s\\w+");
		matcher = word.matcher(expression);
		columnToBeAddedOrDeleted = new StringBuilder();
		while (matcher.find()) {
			columnToBeAddedOrDeleted.append(matcher.group(0));
		}
		parts = columnToBeAddedOrDeleted.toString().split(" ");
		return parts[1];
	}

	public String columnType(String expression) {
		word = Pattern.compile(
				"[Aa][Dd][Dd]\\s\\w+\\s([iI][Nn][Tt]|[Vv][Aa][Rr][Cc][Hh][Aa][Rr]|[Dd][Aa][Tt][Ee]|[Ff][Ll][Oo][Aa][Tt])");
		matcher = word.matcher(expression);
		columnTypeToBeAdded = new StringBuilder();
		while (matcher.find()) {
			columnTypeToBeAdded.append(matcher.group(0));
		}
		parts = columnTypeToBeAdded.toString().split(" ");
		return parts[2].toLowerCase();
	}

	@Override
	public String valueToBeChecked(String expression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String columnToBeChecked(String expression, String operator) {
		// TODO Auto-generated method stub
		return null;
	}

}
