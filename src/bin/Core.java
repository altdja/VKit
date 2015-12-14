package bin;

import gui.Controller;
import javafx.application.Platform;
import javafx.concurrent.Task;

/**
 * Created by ALT on 13.12.2015.
 */
public class Core {
    static Controller controller;
    static JSONParser jsonParser;

    private static String pathToFile;
    private static String urlToGroup;
    private static int idGetRequestRetry;

    public Core(JSONParser jsonParser) {
        this.jsonParser = jsonParser;
    }

    public Core(Controller controller) {
        this.controller = controller;
    }

    static Task task;
    public static Task getTask() {
        return task;
    }


    public static void startProgram() {
        if (controller.isButtonClicked() == false) {
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
                            controller.setButtonClicked(false);
                        });
                    } else {
                        controller.getStatus().appendText(String.valueOf(CurentTime.getCurrentTime() + "Пинг сервера ВКонтакте успешно пройден.\n"));
                        JSONParser.parseIdRepeat();
                        JSONParser.parseCount();
                        idGetRequestRetry = Integer.parseInt(JSONParser.getCount()) / 1000;
                        controller.getStatus().appendText(CurentTime.getCurrentTime() + "Всего пользователей : " + JSONParser.getCount() + "\n");
                        controller.getStatus().appendText(CurentTime.getCurrentTime() + "Всего запросов : " + idGetRequestRetry + "\n");
                        for (int i = 0; i <= idGetRequestRetry; i++) {
                            if (isCancelled()) {
                                updateProgress(0, 0);
                                controller.getStatus().appendText(CurentTime.getCurrentTime() + "Отмена.\n");
                                break;
                            }else{
                                GetRequest.setOffset(i * 1000);
                                JSONParser.parseId();
                                controller.getStatus().appendText(CurentTime.getCurrentTime() + "Запрос " + i + " из " + idGetRequestRetry + "...ОК\n");
                                updateProgress(i, idGetRequestRetry);
                            }
                        }
                        controller.getStatus().appendText(CurentTime.getCurrentTime() + "Готово!\n");
                        FileWriter.fileWriter();
                        Platform.runLater(() -> {
                            controller.getStartButton().setText("Получить ID");
                            controller.setButtonClicked(false);
                        });
                    }
                    return null;
                }
            };
            controller.setButtonClicked(true);
            controller.getStartButton().setText("Отмена");
            new Thread(task).start();
        }else{
            task.cancel();
            controller.getStartButton().setText("Получить ID");
            controller.setButtonClicked(false);
        }
    }
}
