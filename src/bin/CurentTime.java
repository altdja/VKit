package bin;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by aburdeiny on 07.12.2015.
 */
public class CurentTime {
    public static String getCurrentTime() {
        Date now = new Date();
        return DateFormat.getTimeInstance().format(now)+" : ";
    }
}
