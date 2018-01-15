package saving;

import java.util.LinkedList;

public interface ISave {
	public void save(LinkedList<LinkedList<LinkedList<String>>> tableData ,LinkedList<LinkedList<String>> saveColumnNames);
	public LinkedList<LinkedList<LinkedList<String>>> load (LinkedList<LinkedList<String>> tableDatabaseNames);
    public void dropDatabase(String dbName); 
    public void dropTable(String dbName,String tableName);



}
