package tv.superawesome.sagdprisminorsdk.minor.network;

import org.json.JSONObject;

public interface NetworkInterface {
    String getEndpoint ();
    HTTPMethod getMethod ();
    JSONObject getQuery ();
    JSONObject getHeader ();
    JSONObject getBody ();
    void success(int status, String payload, boolean success);
}
