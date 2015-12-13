package gui;

import bin.*;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ALT on 06.11.2015.
 */

public class Controller implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        status.setText(CurentTime.getCurrentTime()+"Программа запущенна.\n");
    }

    Core core;
    public Controller() {
        this.core = new Core(this);
    }

    @FXML
    public TextArea status;
    public TextArea getStatus() {
        return status;
    }

    @FXML
    private TextField path_to_file, url_to_group;
    public TextField getPath_to_file() {
        return path_to_file;
    }
    public TextField getUrl_to_group() {
        return url_to_group;
    }


    public Button startButton;
    private boolean buttonClicked = false;
    public Button getStartButton() {
        return startButton;
    }
    public boolean isButtonClicked() {
        return buttonClicked;
    }
    public void setButtonClicked(boolean buttonClicked) {
        this.buttonClicked = buttonClicked;
    }

    public ProgressBar progressBar;
    public ProgressBar getProgressBar() {
        return progressBar;
    }


    @FXML
    void buttonPathClick() {
        locateFile(MouseEvent.MOUSE_CLICKED);
    }
    private void locateFile(EventType<MouseEvent> event) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Выбор папки");
        File file = chooser.showDialog(new Stage());
        try{
            path_to_file.clear();
            path_to_file.appendText(file.getPath()+"\\");
        }catch (NullPointerException ex){
            path_to_file.setText("C:\\VKit\\");
        }
    }

    @FXML
    void buttonGetIDClick() throws ParseException {
        Core.startProgram();
        progressBar.progressProperty().bind(core.getTask().progressProperty());
    }
}
