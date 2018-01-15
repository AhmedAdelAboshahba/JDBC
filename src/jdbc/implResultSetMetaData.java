package jdbc;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedList;

public class implResultSetMetaData implements ResultSetMetaData {
	private LinkedList<String> columnsNames = new LinkedList<>();
	private LinkedList<String> columnsTypes = new LinkedList<>();
	private String tableName;
	

	public implResultSetMetaData(LinkedList<String> columnsNames, LinkedList<String> columnsTypes, String tableName) {
		this.columnsNames = columnsNames;
		this.columnsTypes = columnsTypes;
		this.tableName = tableName;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getCatalogName(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getColumnClassName(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getColumnCount() throws SQLException {
		return columnsNames.size();
	}

	@Override
	public int getColumnDisplaySize(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getColumnLabel(int arg0) throws SQLException {
		return columnsNames.get(arg0 - 1);
	}

	@Override
	public String getColumnName(int arg0) throws SQLException {
		return columnsNames.get(arg0 - 1);
	}

	@Override
	public int getColumnType(int arg0) throws SQLException {
		int i = 0;
		if (columnsTypes.get(arg0 - 1) == "varchar") {
			i = Types.VARCHAR;
		} else if (columnsTypes.get(arg0 - 1) == "int") {
			i = Types.INTEGER;
		} else if (columnsTypes.get(arg0 - 1) == "date") {
			i = Types.DATE;
		} else if (columnsTypes.get(arg0 - 1) == "float") {
			i = Types.FLOAT;
		}
		return i;
	}

	@Override
	public String getColumnTypeName(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getPrecision(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getScale(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getSchemaName(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getTableName(int arg0) throws SQLException {
		return tableName;
	}

	@Override
	public boolean isAutoIncrement(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isCaseSensitive(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isCurrency(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isDefinitelyWritable(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int isNullable(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isReadOnly(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isSearchable(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isSigned(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isWritable(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

}
