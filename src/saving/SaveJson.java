 package saving;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

 
public class SaveJson implements ISave{
    private String url;
    public SaveJson (String url) {
        this.url = url;
    }
    public void save (LinkedList<LinkedList<LinkedList<String>>> tableData ,LinkedList<LinkedList<String>> saveColumnNames) {
        for (int l=0 ; l<tableData.size() ; l++) {
            try {
                //PrintWriter pw = new PrintWriter(tableData.get(l).get(0).get(0)+"/"+tableData.get(l).get(0).get(1)+".json");
                Path path = Paths.get(url);
//              File h = Files.createDirectories(path).toAbsolutePath().normalize().toFile();
                File h = Files.createDirectories(path).toAbsolutePath().normalize().toFile();
                PrintWriter pw = new PrintWriter(h.getAbsolutePath()+ "\\"+tableData.get(l).get(0).get(1)+".json");
                pw.println("{\"" +tableData.get(l).get(0).get(0) + "\":[");
                pw.println(tableData.get(l).get(0).get(1) + "{");
                for (int i=1 ; i<tableData.get(l).size() ; i++){//iterate over columns
                    String line = "";
                    for (int j=0 ; j<tableData.get(l).get(i).size() ; j++) {//iterate over each column
                        if (j == 1) {
                            continue;
                        } else {
                            line += tableData.get(l).get(i).get(j)+";";
                        }
                    }
                    pw.println("\t\""+tableData.get(l).get(i).get(1)+"\":" + line + ",");
                }
                pw.println("}");
                pw.println("}]");
                pw.close();
            } catch ( IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
           
           
        }
    }
   
    public LinkedList<LinkedList<LinkedList<String>>> load (LinkedList<LinkedList<String>> tableDatabaseNames) {
        LinkedList<LinkedList<LinkedList<String>>> returnTable = new LinkedList<>();
            try {
                for (int l=0 ; l<tableDatabaseNames.size() ; l++) {
                    LinkedList<LinkedList<String>> data = new LinkedList<>();
                    String fileName = tableDatabaseNames.get(l).get(0)+"/"+tableDatabaseNames.get(l).get(1)+".json";
                    System.out.println(fileName);
                    BufferedReader br = new BufferedReader(new FileReader(fileName));
                    String databaseName = tableDatabaseNames.get(l).get(0);
                    String tablename = tableDatabaseNames.get(l).get(1);
                    LinkedList<String> databaseAndTable = new LinkedList<>();
                    databaseAndTable.add(databaseName);
                    databaseAndTable.add(tablename);
                    data.add(databaseAndTable);
                    for(String line;( line=br.readLine() )!=null;) {
                         if (line.contains("[{" + databaseName)) {
                             continue;
                         }
                         if (line.contains("}]")) { //end of file
                             break;
                         }
                         for (int i=2 ; i<tableDatabaseNames.get(l).size() ; i++) {
                             if (line.contains(tableDatabaseNames.get(l).get(i))) {
                                 String column = line.substring(line.indexOf(":")+1, line.indexOf(","));
                                 System.out.println(column);
                                 String[] columnData = column.split(";");
                                 LinkedList<String> columnList = new LinkedList<>();
                                 for (int j=0 ; j<columnData.length ; j++) {
                                     if (j == 1) {
                                         columnList.add(tableDatabaseNames.get(l).get(i));
                                     }
                                     columnList.add(columnData[j]);
                                 }
                                 data.add(columnList);
                             }
                         }
                    }
                    returnTable.add(data);
                    br.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return returnTable;
    }
 
	@Override
	public void dropDatabase(String dbName) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void dropTable(String dbName, String tableName) {
		// TODO Auto-generated method stub
		
	}
}