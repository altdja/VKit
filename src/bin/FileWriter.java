package bin;

import java.io.File;
import java.io.IOException;

/**
 * Created by ALT on 06.11.2015.
 */
public class FileWriter {

    public static void fileWriter() {
        File FilePath = new File(Settings.getFilePath());
        FilePath.mkdirs();
        try (java.io.FileWriter fileWriter = new java.io.FileWriter(Settings.getFilePath()+ Settings.getGroupId()+".txt")) {
            fileWriter.write(String.valueOf(JSONParser.getFullIdList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
