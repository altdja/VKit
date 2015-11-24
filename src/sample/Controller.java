package sample;

import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class Controller {

    @FXML
    private TextField path_to_file, url_to_group;
    @FXML
    private Label status;

    @FXML protected void locateFile(EventType<MouseEvent> event) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Open File");
        File file = chooser.showDialog(new Stage());
        try{
            path_to_file.clear();
            path_to_file.appendText(file.getPath()+"\\");
        }catch (NullPointerException ex){
            hashCode();
        }
    }

    @FXML protected void startScan(EventType<MouseEvent> event) {
        try {
            if(url_to_group.getText() == null){
                status.setText("Состояние: нет ссылки на группу...");
            }
        }catch(NullPointerException ex){

        }
    }

    @FXML
    void buttonPathClick() {
        locateFile(MouseEvent.MOUSE_CLICKED);
    }

    @FXML
    void buttonGetIDClick() {
        startScan(MouseEvent.MOUSE_CLICKED);
    }
}
