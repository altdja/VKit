package bin;

import org.json.simple.parser.ParseException;

/**
 * Created by ALT on 02.11.2015.
 */

public class Main {
    public static void main(String[] args) throws ParseException {
        JSONParser.parseIdRepeat();
        FileWriter.fileWriter();
    }
}
