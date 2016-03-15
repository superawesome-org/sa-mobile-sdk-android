package tv.superawesome.lib.sanetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

/**
 * Created by gabriel.coman on 22/12/15.
 */
public class SASyncGet {

    public static String execute(String urlstr) throws IOException {

        URL url;
        String response = null;
        try {
            url = new URL(urlstr);

            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("User-Agent", SAUserAgent.getUserAgent());

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                response = "";
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }
}
