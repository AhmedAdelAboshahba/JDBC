package jdbc;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Map;

import database.management.DataBaseManagement;
import database.management.Table;
import parserForSQL.Validity;

public class implResultset implements java.sql.ResultSet {
	// line 94
	// close resultset

	// private DataBaseManagement object;// delete
	private int cursor = 0;
	private int rowsSize;
	private int columnSize;
	// private Table table; // delete it
	private LinkedList<String> columnsNames = new LinkedList<>();
	private LinkedList<String> columnsTypes = new LinkedList<>();
	private String tableName;
	private StatementManager statement; // getStatement lesa
	private implResultSetMetaData metaData; // lesa
	private LinkedList<LinkedList<String>> rsDomain=new LinkedList<>();
	private boolean closed=false;

	public implResultset(LinkedList<LinkedList<String>> domain, String tableName,StatementManager statement) {
		this.tableName = tableName;
		for (int i = 0; i < domain.size(); i++) {
			this.columnsNames.add(domain.get(i).get(0));
			this.columnsTypes.add(domain.get(i).get(1));
		}
		this.rowsSize = domain.get(0).size() - 2;
		this.columnSize = domain.size();
		/*
		for (int j = 2; j < domain.get(0).size(); j++) {
			LinkedList<String> help = new LinkedList<>();
			for (int i = 0; i < domain.size(); i++) {

				help.add(domain.get(i).get(j));

			}
			this.rsDomain.add(help);
		}
		*/
		for(int i =0 ; i< domain.size();i++){
			LinkedList<String> help = new LinkedList<>();
			for(int j =2;j<domain.get(0).size();j++){
				help.add(domain.get(i).get(j));
			}
			this.rsDomain.add(help);
		}
		this.statement=statement;

	}

	private int searchForColumn(String columnName) throws SQLException {
		
		int index = -1;
		for (int i = 0; i < columnsNames.size(); i++) {
			if (columnsNames.get(i).toLowerCase().equals(columnName.toLowerCase())) {
				index = i;
			}
		}
		return index + 1;
	}

	@Override
	public int findColumn(String columnLabel) throws SQLException {
		checkClose();
		int index = searchForColumn(columnLabel);
		if (index != -1) {
			return index;
		} else {
			throw new SQLException();
		}

	}

	@Override
	public boolean first() throws SQLException {
		checkClose();
		if (rowsSize > 0) {
			cursor = 1;
			return true;
		} else
			return false;
	}

	@Override
	public Date getDate(int columnIndex) throws SQLException {
		checkClose();
		if (columnIndex <= columnSize && cursor <= rowsSize && columnIndex>0) { // return null if
																// value == null
			if (columnsTypes.get(columnIndex - 1).equals("date")
					&& rsDomain.get(columnIndex - 1).get(cursor - 1) != null) {
				Date x = Date.valueOf(rsDomain.get(columnIndex - 1).get(cursor - 1));
				return x;
			} else {
				if (rsDomain.get(columnIndex - 1).get(cursor - 1) == null) {
					return null;
				}
				throw new SQLException();
			}
		} else {
			throw new SQLException();
		}

	}

	@Override
	public Date getDate(String columnLabel) throws SQLException {
		checkClose();
		int columnIndex = searchForColumn(columnLabel);
		return getDate(columnIndex);

	}

	@Override
	public float getFloat(int columnIndex) throws SQLException {
		checkClose();
		if (columnIndex <= columnSize && cursor <= rowsSize) {
			if (columnsTypes.get(columnIndex - 1).equals("float")
					&& rsDomain.get(columnIndex - 1).get(cursor - 1) != null) {
				float x = Float.parseFloat(rsDomain.get(columnIndex - 1).get(cursor - 1));
				return x;
			} else {
				if (rsDomain.get(columnIndex - 1).get(cursor - 1) == null) {
					return 0;
				}
				throw new SQLException();
			}
		} else {
			throw new SQLException();
		}
	}

	@Override
	public float getFloat(String columnLabel) throws SQLException {
		checkClose();
		int columnIndex = searchForColumn(columnLabel);
		return getFloat(columnIndex);
	}

	@Override
	public int getInt(int columnIndex) throws SQLException {
		checkClose();
		if (columnIndex <= columnSize && cursor <= rowsSize) {
			if (columnsTypes.get(columnIndex - 1).equals("int")
					&& rsDomain.get(columnIndex - 1).get(cursor - 1) != null) {
				Integer x = Integer.parseInt(rsDomain.get(columnIndex - 1).get(cursor - 1));
				return (int) x;
			} else {
				if (rsDomain.get(columnIndex - 1).get(cursor - 1) == null) {
					return 0;
				}
				throw new SQLException();
			}
		} else {
			throw new SQLException();
		}
	}

	@Override
	public int getInt(String columnLabel) throws SQLException {
		checkClose();
		int index = searchForColumn(columnLabel);
		return getInt(index);
	}

	@Override
	public Object getObject(int columnIndex) throws SQLException {
		checkClose();
		if (columnIndex <= columnSize && cursor <= rowsSize) {

			if (columnsTypes.get(columnIndex - 1).equals("varchar")) {
				return getString(columnIndex);
			} else if (columnsTypes.get(columnIndex - 1).equals("int")) {
				return getInt(columnIndex);

			} else if (columnsTypes.get(columnIndex - 1).equals("float")) {
				return getFloat(columnIndex);

			} else {
				return getDate(columnIndex);

			}

		} else {
			throw new SQLException();
		}
	}

	@Override
	public String getString(int columnIndex) throws SQLException {
		checkClose();
		if (columnIndex <= columnSize && cursor <= rowsSize) {
			if (columnsTypes.get(columnIndex - 1).equals("varchar")
					&& rsDomain.get(columnIndex - 1).get(cursor - 1) != null) {

				return rsDomain.get(columnIndex - 1).get(cursor - 1);
			} else {
				if (rsDomain.get(columnIndex - 1).get(cursor - 1) == null) {
					return null;
				}
				throw new SQLException();
			}
		} else {
			throw new SQLException();
		}
	}

	@Override
	public String getString(String columnLabel) throws SQLException {
		checkClose();
		int index = searchForColumn(columnLabel);
		return getString(index);
	}

	@Override
	public boolean isAfterLast() throws SQLException {
		checkClose();
		if (cursor == rowsSize + 1) {
			return true;
		} else
			return false;
	}

	@Override
	public boolean isBeforeFirst() throws SQLException {
		checkClose();
		if (cursor == 0) {
			return true;
		} else
			return false;
	}

	@Override
	public boolean isClosed() throws SQLException {
		return closed;
	}

	@Override
	public boolean isFirst() throws SQLException {
		checkClose();
		if (cursor == 1) {
			return true;
		} else
			return false;
	}

	@Override
	public boolean isLast() throws SQLException {
		checkClose();
		if (cursor == rowsSize) {
			return true;
		} else
			return false;
	}

	@Override
	public boolean last() throws SQLException {
		checkClose();
		if (rowsSize > 0) {
			cursor = rowsSize;
			return true;
		} else
			return false;
	}

	@Override
	public boolean next() throws SQLException {
		checkClose();
		if (cursor < rowsSize) {
			cursor++;
			return true;
		} else
			return false;
	}

	@Override
	public boolean previous() throws SQLException {
		checkClose();
		if (cursor > 1) {
			cursor--;
			return true;
		} else
			return false;
	}

	@Override
	public boolean absolute(int row) throws SQLException {
		checkClose();
		if (row == 0) {
			beforeFirst();
			return false;
		} else if (row > 0 && row <= rowsSize) {
			cursor = row;
			return true;
		} else if (row < 0) {
			int reverse = rowsSize + row + 1;
			cursor = reverse;
			return true;
		} else if (row == rowsSize + 1) {
			afterLast();
			return false;
		} else
			return false;
	}

	@Override
	public void afterLast() throws SQLException {
		checkClose();
		if (rowsSize > 0) {
			cursor = rowsSize + 1;
		}
	}

	@Override
	public void beforeFirst() throws SQLException {
		checkClose();
		if (rowsSize > 0) {
			cursor = 0;
		}

	}

	@Override
	public ResultSetMetaData getMetaData() throws SQLException {
		checkClose();
		metaData=new implResultSetMetaData(columnsNames, columnsTypes, tableName);
		return metaData;
	}

	@Override
	public Statement getStatement() throws SQLException {
		checkClose();
		return statement;
	}

	@Override
	public void close() throws SQLException {
		closed = true;
	}
	private void checkClose() throws SQLException{
		if(closed==true){
			throw new SQLException();
		}
	}

	@Override
	public void cancelRowUpdates() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clearWarnings() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteRow() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Array getArray(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
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
	public Array getArray(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public InputStream getAsciiStream(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public InputStream getAsciiStream(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public InputStream getBinaryStream(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public InputStream getBinaryStream(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Blob getBlob(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Blob getBlob(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean getBoolean(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean getBoolean(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public byte getByte(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public byte getByte(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public byte[] getBytes(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public byte[] getBytes(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Reader getCharacterStream(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Reader getCharacterStream(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Clob getClob(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Clob getClob(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getConcurrency() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getCursorName() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Date getDate(int columnIndex, Calendar cal) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Date getDate(String columnLabel, Calendar cal) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public double getDouble(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public double getDouble(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getFetchDirection() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getFetchSize() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getHoldability() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getLong(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getLong(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Reader getNCharacterStream(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Reader getNCharacterStream(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public NClob getNClob(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public NClob getNClob(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getNString(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getNString(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getObject(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getObject(int columnIndex, Map<String, Class<?>> map) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getObject(String columnLabel, Map<String, Class<?>> map) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Ref getRef(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Ref getRef(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getRow() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public RowId getRowId(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public RowId getRowId(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public SQLXML getSQLXML(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public SQLXML getSQLXML(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public short getShort(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public short getShort(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Time getTime(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Time getTime(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Time getTime(int columnIndex, Calendar cal) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Time getTime(String columnLabel, Calendar cal) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Timestamp getTimestamp(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Timestamp getTimestamp(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getType() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public URL getURL(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public URL getURL(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public InputStream getUnicodeStream(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public InputStream getUnicodeStream(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void insertRow() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void moveToCurrentRow() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void moveToInsertRow() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void refreshRow() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean relative(int rows) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean rowDeleted() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean rowInserted() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean rowUpdated() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setFetchDirection(int direction) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setFetchSize(int rows) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateArray(int columnIndex, Array x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateArray(String columnLabel, Array x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateAsciiStream(String columnLabel, InputStream x, int length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBigDecimal(String columnLabel, BigDecimal x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBinaryStream(String columnLabel, InputStream x, int length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBlob(int columnIndex, Blob x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBlob(String columnLabel, Blob x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBoolean(int columnIndex, boolean x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBoolean(String columnLabel, boolean x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateByte(int columnIndex, byte x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateByte(String columnLabel, byte x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBytes(int columnIndex, byte[] x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBytes(String columnLabel, byte[] x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateCharacterStream(String columnLabel, Reader reader, int length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateClob(int columnIndex, Clob x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateClob(String columnLabel, Clob x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateClob(int columnIndex, Reader reader) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateClob(String columnLabel, Reader reader) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateDate(int columnIndex, Date x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateDate(String columnLabel, Date x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateDouble(int columnIndex, double x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateDouble(String columnLabel, double x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateFloat(int columnIndex, float x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateFloat(String columnLabel, float x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateInt(int columnIndex, int x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateInt(String columnLabel, int x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateLong(int columnIndex, long x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateLong(String columnLabel, long x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNClob(String columnLabel, NClob nClob) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNClob(int columnIndex, Reader reader) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNClob(String columnLabel, Reader reader) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNString(int columnIndex, String nString) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNString(String columnLabel, String nString) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNull(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNull(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateObject(int columnIndex, Object x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateObject(String columnLabel, Object x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateObject(int columnIndex, Object x, int scaleOrLength) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateRef(int columnIndex, Ref x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateRef(String columnLabel, Ref x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateRow() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateRowId(int columnIndex, RowId x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateRowId(String columnLabel, RowId x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateShort(int columnIndex, short x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateShort(String columnLabel, short x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateString(int columnIndex, String x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateString(String columnLabel, String x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateTime(int columnIndex, Time x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateTime(String columnLabel, Time x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateTimestamp(String columnLabel, Timestamp x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean wasNull() throws SQLException {
		throw new UnsupportedOperationException();
	}

}
