package sample;

import bin.FileWriter;
import bin.GetRequest;
import bin.Settings;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.File;
import java.text.DateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ALT on 06.11.2015.
 */

public class Controller {
    @FXML
    private TextField path_to_file, url_to_group;
    @FXML
    public TextArea status;
    private String pathToFile, urlToGroup;

    @FXML protected void locateFile(EventType<MouseEvent> event) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Open File");
        File file = chooser.showDialog(new Stage());
        try{
            path_to_file.clear();
            path_to_file.appendText(file.getPath()+"\\");
        }catch (NullPointerException ex){
            path_to_file.setText("C:\\VKit\\");
        }
    }

    @FXML protected void startScan(EventType<MouseEvent> event) throws ParseException {
        try {
            pathToFile = path_to_file.getText();
            urlToGroup = url_to_group.getText();
            Settings.setFilePath(pathToFile);
            Settings.setGroupId(urlToGroup);
            bin.JSONParser.pingGroupId();
            if(bin.JSONParser.isIdErr() == true){
                status.appendText(String.valueOf(getCurrentTime()+" : Id группы введен не верно, или с ошибкой!\n"));
            }else{
                status.appendText(String.valueOf(getCurrentTime()+" : Пинг сервера ВКонтакте успешно пройден.\n"));
                bin.JSONParser.parseIdRepeat();
                FileWriter.fileWriter();
            }
        }catch(NullPointerException ex){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void buttonPathClick() {
        locateFile(MouseEvent.MOUSE_CLICKED);
    }

    @FXML
    void buttonGetIDClick() throws ParseException {
        startScan(MouseEvent.MOUSE_CLICKED);
    }

    String getCurrentTime() {
        Date now = new Date();
        return DateFormat.getTimeInstance().format(now);
    }

    void statusTextOut() {

    }
}
