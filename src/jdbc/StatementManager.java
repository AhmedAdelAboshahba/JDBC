package jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.LinkedList;

import parserForSQL.Validity;

public class StatementManager implements java.sql.Statement {
	private Validity parser;
	private String urlName, pathName;
	private LinkedList<String> batch = new LinkedList<>();
	private boolean closed = false;
	private ResultSet rs;
	private int rowsCount;
	private ConnectionManager connect = null;

	public StatementManager(String url, String path, ConnectionManager connectManage , Validity parser) {
		this.connect = connectManage;
		this.urlName = url;
		this.pathName = path;
		this.parser = parser;
	}
	@Override
	public void addBatch(String sql) throws SQLException {
		// TODO Auto-generated method stub
		if (closed == false) {
			batch.add(sql);
		}
		else{
			throw new SQLException();
		}
	}
	@Override
	public void clearBatch() throws SQLException {
		// TODO Auto-generated method stub
		if (closed == false) {
			batch = new LinkedList<>();
		}
		else{
			throw new SQLException();
		}
	}
	@Override
	public void close() throws SQLException {
		// TODO Auto-generated method stub
		closed = true;

	}
	@Override
	public boolean execute(String sql) throws SQLException {
		// TODO Auto-generated method stub
		if (closed == false) {
			LinkedList<LinkedList<String>> selected;
			String temp = sql.toLowerCase();
			parser.validOrNot(sql);
			if (temp.contains("select") || temp.contains("distinct")) {
				selected = parser.getSelected();
				if (selected.get(0).size() > 2) {
					String tableName = parser.getTableName();
					rs = new implResultset(selected,tableName,this);
				 rowsCount = parser.getAffectedRows();
					return true;
				} else {
					 rowsCount = parser.getAffectedRows();
					
					return false;
				}
			} else {
				rowsCount = parser.getAffectedRows();
				
				return false;
			}
		} else {
			throw new SQLException();
		}

	}
	@Override
	public int[] executeBatch() throws SQLException {
		// TODO Auto-generated method stub
		LinkedList<LinkedList<String>> selected ;
		if (closed == false) {
			String temp;
			int[] result = new int[batch.size()];
			for (int i = 0; i < batch.size(); i++) {
				try {
					parser.validOrNot(batch.get(i));
					temp = batch.get(i).toLowerCase();
					if (temp.contains("select") || temp.contains("distinct")) {
						selected = parser.getSelected();
						if (selected.get(0).size() > 2) {
							String tableName = parser.getTableName();
							rs = new implResultset(selected,tableName,this);
							 rowsCount = parser.getAffectedRows();
						}
						result[i] = SUCCESS_NO_INFO;
					} else {
						result[i] = parser.getAffectedRows();
					}
				} catch (SQLException e) {
					result[i] = EXECUTE_FAILED;
				}
			}
			return result;
		} else {
			throw new SQLException();
		}
	}

	@Override
	public ResultSet executeQuery(String sql) throws SQLException {
		// TODO Auto-generated method stub
		if (closed == false) {
			LinkedList<LinkedList<String>> selected;
			String temp = sql.toLowerCase();
			if (temp.contains("select") || temp.contains("distinct")) {
				parser.validOrNot(sql);
				selected = parser.getSelected();
				String tableName =parser.getTableName();
				rs = new implResultset(selected,tableName,this);
				return rs;
			} else {
				throw new SQLException();
			}
		} else {
			throw new SQLException();
		}
	}

	@Override
	public int executeUpdate(String sql) throws SQLException {
		// TODO Auto-generated method stub
		if (closed == false) {
			String temp = sql.toLowerCase();
			if (temp.contains("select") || temp.contains("distinct")) {
				throw new SQLException();
			} else {
				parser.validOrNot(sql);
				rowsCount = parser.getAffectedRows();
				return rowsCount;
			}
		} else {
			throw new SQLException();
		}
	}
	@Override
	public Connection getConnection() throws SQLException {
		// TODO Auto-generated method stub
		if (closed == false) {
			return connect;
		} else {
			throw new SQLException();
		}
	}
	@Override
	public ResultSet getResultSet() throws SQLException {
		// TODO Auto-generated method stub
		if (closed == false) {
			return rs;
		} else {
			throw new SQLException();
		}
	}
	@Override
	public int getUpdateCount() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T unwrap(Class<T> arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	

	@Override
	public void cancel() throws SQLException {
		throw new UnsupportedOperationException();
	}


	@Override
	public void clearWarnings() throws SQLException {
		throw new UnsupportedOperationException();
	}

	

	@Override
	public void closeOnCompletion() throws SQLException {
		throw new UnsupportedOperationException();
	}

	

	@Override
	public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean execute(String sql, int[] columnIndexes) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean execute(String sql, String[] columnNames) throws SQLException {
		throw new UnsupportedOperationException();
	}


	@Override
	public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int executeUpdate(String sql, String[] columnNames) throws SQLException {
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
	public ResultSet getGeneratedKeys() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getMaxFieldSize() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getMaxRows() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean getMoreResults() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean getMoreResults(int current) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getQueryTimeout() throws SQLException {
		throw new UnsupportedOperationException();
	}

	

	@Override
	public int getResultSetConcurrency() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getResultSetHoldability() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getResultSetType() throws SQLException {
		throw new UnsupportedOperationException();
	}


	@Override
	public SQLWarning getWarnings() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isCloseOnCompletion() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isClosed() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isPoolable() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setCursorName(String name) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setEscapeProcessing(boolean enable) throws SQLException {
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
	public void setMaxFieldSize(int max) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setMaxRows(int max) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setPoolable(boolean poolable) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setQueryTimeout(int seconds) throws SQLException {
		throw new UnsupportedOperationException();
	}
}