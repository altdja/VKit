package bin;

import org.json.simple.parser.ParseException;

/**
 * Created by ALT on 06.11.2015.
 */
public class GetRequest {
    private static String JSONdata;
    private static int offset = 0;

    public static void getRequest() throws ParseException {
        ServerRequest ServerRequest = new ServerRequest();
        JSONdata = ServerRequest.getJSON("http://api.vk.com/method/groups.getMembers?group_id="+ Settings.getGroupId()+"&offset="+offset+"&count=1000", 5000);
    }

    public static void setJSONdata(String JSONdata) {
        GetRequest.JSONdata = JSONdata;
    }

    public static String getJSONdata() {
        return JSONdata;
    }

    public static void setOffset(int offset) {
        GetRequest.offset = offset;
    }
}
