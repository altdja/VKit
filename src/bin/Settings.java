package bin;

/**
 * Created by ALT on 06.11.2015.
 */

/**
Класс настройки: filePath - путь сохранения фаила, groupId - имя группы ВК.
*/

public class Settings {
    private static String filePath = "D://VKit/";
    private static String groupId = "kruzhka.chaya";

    public static String getFilePath() {
        return filePath;
    }

    public static String getGroupId() {
        return groupId;
    }
}
