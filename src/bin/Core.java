package bin;

import gui.Controller;
import javafx.application.Platform;
import javafx.concurrent.Task;

/**
 * Created by ALT on 13.12.2015.
 */
public class Core {
    static Controller controller;

    private static String pathToFile;
    private static String urlToGroup;
    private static int idGetRequestRetry;
    private static boolean buttonClicked = false;
    private static boolean parseComplite = false;

    public Core(Controller controller) {
        this.controller = controller;
    }

    static Task task;
    public static Task getTask() {
        return task;
    }

    public static void startProgram() {
        if (buttonClicked == false) {
            task = new Task() {
                @Override
                protected Object call() throws Exception {
                    pathToFile = controller.getPath_to_file().getText();
                    urlToGroup = controller.getUrl_to_group().getText();
                    Settings.setFilePath(pathToFile);
                    Settings.setGroupId(urlToGroup);
                    bin.JSONParser.pingGroupId();
                    if (bin.JSONParser.isIdErr() == true) {
                        controller.getStatus().appendText(String.valueOf(CurentTime.getCurrentTime() + "Id группы введен не верно, или с ошибкой!\n"));
                        Platform.runLater(() -> {
                            controller.getStartButton().setText("Получить ID");
                            buttonClicked = false;
                        });
                    } else {
                        controller.getStatus().appendText(String.valueOf(CurentTime.getCurrentTime() + "Пинг сервера ВКонтакте успешно пройден.\n"));
                        JSONParser.parseCount();
                        idGetRequestRetry = (Integer.parseInt(JSONParser.getCount()) / 1000) + 1;
                        controller.getStatus().appendText(CurentTime.getCurrentTime() + "Всего пользователей : " + JSONParser.getCount() + "\n");
                        controller.getStatus().appendText(CurentTime.getCurrentTime() + "Всего запросов : " + idGetRequestRetry + "\n");
                        for (int i = 1; i <= idGetRequestRetry; i++) {
                            if (isCancelled()) {
                                updateProgress(0, 0);
                                controller.getStatus().appendText(CurentTime.getCurrentTime() + "Отмена.\n");
                                break;
                            }else{
                                controller.getStatus().appendText(CurentTime.getCurrentTime() + "Запрос " + i + " из " + idGetRequestRetry + "...");
                                GetRequest.setOffset(i * 1000);
                                JSONParser.parseId();
                                controller.getStatus().appendText("OK\n");
                                updateProgress(i, idGetRequestRetry);
                                if (i == idGetRequestRetry){
                                    parseComplite = true;
                                }
                            }
                        }
                        if (parseComplite == true){
                            controller.getStatus().appendText(CurentTime.getCurrentTime() + "Готово!\n");
                            FileWriter.fileWriter();
                            parseComplite = false;
                        }
                        Platform.runLater(() -> {
                            controller.getStartButton().setText("Получить ID");
                            buttonClicked = false;
                        });
                    }
                    return null;
                }
            };
            buttonClicked = true;
            controller.getStartButton().setText("Отмена");
            new Thread(task).start();
        }else{
            task.cancel();
            controller.getStartButton().setText("Получить ID");
            buttonClicked = false;
        }
    }
}
