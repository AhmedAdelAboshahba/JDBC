package jdbc;

import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class DriverManager implements java.sql.Driver{

	@Override
	public boolean acceptsURL(String url) throws SQLException {
		// TODO Auto-generated method stub
		if(url == null){
			return false;
		}
		else if(url.toLowerCase().startsWith("jdbc:xmldb")|| url.toLowerCase().startsWith("jdbc:altdb")){
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public Connection connect(String url, Properties info) throws SQLException {
		// TODO Auto-generated method stub
		if(acceptsURL(url) == false){
			throw new SQLException();
		}
		else{
			//File file=(File)info.get("path");
			//String path = file.getAbsolutePath().toString();
			String path = info.get("path").toString();
			Connection A = new ConnectionManager(url,path);
			return A;
		}
	}
	@Override
	public DriverPropertyInfo[] getPropertyInfo(String arg0, Properties arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMajorVersion() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public int getMinorVersion() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}


	@Override
	public boolean jdbcCompliant() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

}
