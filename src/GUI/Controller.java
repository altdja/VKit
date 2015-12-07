package gui;

import bin.CurentTime;
import bin.FileWriter;
import bin.JSONParser;
import bin.Settings;
import javafx.application.Platform;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ALT on 06.11.2015.
 */

public class Controller implements Initializable {
    @FXML
    private TextField path_to_file, url_to_group;

    @FXML
    public TextArea status;
    private String pathToFile, urlToGroup;

    public void appendText(String str) {
        Platform.runLater(() -> status.appendText(str));
    }

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
            new NewThread();
    }

    @FXML
    void buttonPathClick() {
        locateFile(MouseEvent.MOUSE_CLICKED);
    }

    @FXML
    void buttonGetIDClick() throws ParseException {
        startScan(MouseEvent.MOUSE_CLICKED);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        status.setText(CurentTime.getCurrentTime()+"Программа запущенна.\n");
        OutputStream out = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                appendText(String.valueOf((char) b));
            }
        };
        System.setOut(new PrintStream(out, true));
    }

    class NewThread implements Runnable {
        Thread thread;

        NewThread() {
            thread = new Thread(this, "Background thread");
            thread.start(); // Запускаем поток
        }

        public void run() {
            try {
                pathToFile = path_to_file.getText();
                urlToGroup = url_to_group.getText();
                Settings.setFilePath(pathToFile);
                Settings.setGroupId(urlToGroup);
                bin.JSONParser.pingGroupId();
                if(bin.JSONParser.isIdErr() == true){
                    status.appendText(String.valueOf(CurentTime.getCurrentTime()+"Id группы введен не верно, или с ошибкой!\n"));
                }else{
                    status.appendText(String.valueOf(CurentTime.getCurrentTime()+"Пинг сервера ВКонтакте успешно пройден.\n"));
                    JSONParser.parseIdRepeat();
                    FileWriter.fileWriter();
                }
            }catch(NullPointerException ex){
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
