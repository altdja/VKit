package bin;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

/**
 * Created by ALT on 06.11.2015.
 */

public class JSONParser {
    private static String count, pingGroupId, fullIdList = "", tempIdList = "";

    private static boolean idErr = false;
    public static boolean isIdErr() {
        return idErr;
    }

    public static String getCount() {
        return count;
    }

    public static String getFullIdList() {
        return fullIdList;
    }

    public static void setFullIdList(String fullIdList) {
        JSONParser.fullIdList = fullIdList;
    }

    public static void parseIdRepeat() throws ParseException {
        //Рудимент!!!Без него не работает, УБРАТЬ!
    }

    public static void pingGroupId() throws ParseException {
        try {
            org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
            GetRequest.getRequest();
            Object object = parser.parse(GetRequest.getJSONdata());
            JSONObject jsonObject = (JSONObject) object;
            pingGroupId = String.valueOf(jsonObject);
            boolean checkId = pingGroupId.contains("response");
            idErr = checkId != true;
        }catch (NullPointerException ex) {
            pingGroupId();
            idErr = false;
        }
    }

    public static void parseCount() throws ParseException {
        try {
            org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
            GetRequest.getRequest();
            Object object = parser.parse(GetRequest.getJSONdata());
            JSONObject jsonObject = (JSONObject) object;
            JSONObject jsonObjectInner = (JSONObject) jsonObject.get("response");
            count = String.valueOf(jsonObjectInner.get("count"));
        }catch (NullPointerException ex) {
            parseCount();
        }
    }

    public static void parseId() throws ParseException {
        try {
            org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
            GetRequest.getRequest();
            Object object = parser.parse(GetRequest.getJSONdata());
            JSONObject jsonObject = (JSONObject) object;
            JSONObject jsonObjectInner = (JSONObject) jsonObject.get("response");
            tempIdList = String.valueOf(jsonObjectInner.get("users"));
            removeGarbage();
        }catch (NullPointerException ex) {
            parseId();
        }
    }

    public static void removeGarbage () {
        String clearIdList = tempIdList;
        tempIdList = "";
        for (int i = 0; i < clearIdList.length(); i++) {
            if(clearIdList.charAt(i) == '[') {
                continue;
            }
            if(clearIdList.charAt(i) == ']') {
                System.out.print("");
            }else{
                tempIdList = tempIdList + clearIdList.charAt(i);
            }
        }
        setFullIdList(fullIdList+tempIdList);
    }
}
