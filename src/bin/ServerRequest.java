package bin;

import org.json.simple.parser.ParseException;
import sample.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ALT on 02.11.2015.
 */

public class ServerRequest {

    public String getJSON(String url, int timeout) throws ParseException {
        HttpURLConnection connect = null;
        try {
            URL u = new URL(url);
            connect = (HttpURLConnection) u.openConnection();
            connect.setRequestMethod("GET");
            connect.setRequestProperty("Content-length", "0");
            connect.setUseCaches(false);
            connect.setAllowUserInteraction(false);
            connect.setConnectTimeout(timeout);
            connect.setReadTimeout(timeout);
            connect.connect();
            int status = connect.getResponseCode();

            switch (status){
                case 200:
                    BufferedReader br = new BufferedReader(new InputStreamReader(connect.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                    sb.append(line+"\n");
                }
                br.close();
                return sb.toString();
            }
        } catch (IOException ex) {
        } finally {
            if (connect != null) {
                try {
                    connect.disconnect();
                } catch (Exception ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }
}
