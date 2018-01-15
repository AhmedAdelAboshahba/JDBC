package saving;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Formatter;
 
public class DtdWriter {
    private Formatter x;
 
    private void CreateDTD(String PTH,String TableName) {
        try {
           
            Path path = Paths.get(PTH);
            File h = Files.createDirectories(path).toAbsolutePath().normalize().toFile();
            x = new Formatter(h.getAbsolutePath() + "/" + TableName + ".dtd");
        } catch (Exception e) {
            System.out.println("Error !!");
        }
    }
 
    private void WriteDataBaseChild(String DatabaseName, String TableName) {
        x.format("%s", "<!ELEMENT " + DatabaseName + " (" + TableName + ")>");
        x.format("%n");
    }
 
    private void WriteTableChilds(String TableName, ArrayList<String> columnsName) {
        int CheckLength = 0;
        x.format("%s", "<!ELEMENT " + TableName + " (");
        while (CheckLength < columnsName.size()) {
            if (CheckLength != columnsName.size() - 1) {
                x.format("%s", columnsName.get(CheckLength) + ", ");
            } else {
                x.format("%s", columnsName.get(CheckLength) + ")>");
                x.format("%n");
            }
            CheckLength++;
        }
    }
 
    private void WriteFullComponents(ArrayList<String> columnsName) {
        int CheckLength = 0;
        while (CheckLength < columnsName.size()) {
            x.format("%s", "<!ELEMENT " + columnsName.get(CheckLength) + " (#PCDATA)>");
            x.format("%n");
            CheckLength++;
        }
        x.close();
    }
 
    private void WriteEmptyComponents(ArrayList<String> columnsName) {
        int CheckLength = 0;
        while (CheckLength < columnsName.size()) {
            x.format("%s", "<!ELEMENT " + columnsName.get(CheckLength) + " EMPTY>");
            x.format("%n");
            CheckLength++;
        }
        x.close();
    }
 
    public void MakeDtdFile(String path,String DatabaseName, String TableName, boolean empty, ArrayList<String> columnsName) {
        CreateDTD(path,TableName);
        x.format("%s", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        x.format("%n");
        x.format("%n");
        WriteDataBaseChild(DatabaseName, TableName);
        WriteTableChilds(TableName, columnsName);
        if (empty) {
            WriteEmptyComponents(columnsName);
        } else {
            WriteFullComponents(columnsName);
        }
    }
 
}