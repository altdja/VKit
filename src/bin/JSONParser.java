package bin;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

/**
 * Created by ALT on 06.11.2015.
 */

public class JSONParser {
    private static String count, test;
    private static boolean idErr = false;
    private static String fullIdList = "", tempIdList = "";
    private static int idGetRequestRetry;

    public static boolean isIdErr() {
        return idErr;
    }

    public static String getFullIdList() {
        return fullIdList;
    }

    public static void setFullIdList(String fullIdList) {
        JSONParser.fullIdList = fullIdList;
    }

    public static void parseIdRepeat() throws ParseException {
        parseCount();
        idGetRequestRetry = Integer.parseInt(count) / 1000;
        System.out.println("Count: "+count);
        System.out.println("Total response: "+idGetRequestRetry);
        for (int i = 0; i <= idGetRequestRetry; i++) {
            GetRequest.setOffset(i*1000);
            parseId();
            System.out.print("\rResponse: "+i);
        }
        System.out.println("\nDone!");
    }

    public static void parseCount() throws ParseException {
        org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
        GetRequest.getRequest();
        Object object = parser.parse(GetRequest.getJSONdata());
        try {
            JSONObject jsonObject = (JSONObject) object;
            if(jsonObject.get("error") == "error"){
                idErr = true;
            }
        }catch (NullPointerException ex) {
        }
        JSONObject jsonObject = (JSONObject) object;
        JSONObject jsonObjectInner = (JSONObject) jsonObject.get("response");
        count = String.valueOf(jsonObjectInner.get("count"));
    }

    public static void parseId() throws ParseException {
        org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
        GetRequest.getRequest();
        Object object = parser.parse(GetRequest.getJSONdata());
        JSONObject jsonObject = (JSONObject) object;
        JSONObject jsonObjectInner = (JSONObject) jsonObject.get("response");
        tempIdList = String.valueOf(jsonObjectInner.get("users"));
        removeGarbage();
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
