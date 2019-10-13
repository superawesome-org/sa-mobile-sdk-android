package tv.superawesome.sagdprisminorsdk.minor.network;

import android.content.Context;

import org.json.JSONObject;

import tv.superawesome.lib.sanetwork.request.SANetwork;
import tv.superawesome.lib.sanetwork.request.SANetworkInterface;
import tv.superawesome.sagdprisminorsdk.minor.SAAgeCheck;

public class Service implements NetworkInterface {

    // protected request vars
    protected String url;
    protected String bundleId;
    protected String dateOfBirth;

    // private data
    private Context context = null;
    private SANetwork network;

    public Service() {
        network = new SANetwork();
    }

    @Override
    public String getEndpoint() {
        return null;
    }

    @Override
    public HTTPMethod getMethod() {
        return HTTPMethod.GET;
    }

    @Override
    public JSONObject getQuery() {
        return new JSONObject();
    }

    @Override
    public JSONObject getHeader() {
        //no header, is empty
        return new JSONObject();
    }

    @Override
    public JSONObject getBody() {
        return new JSONObject();
    }

    @Override
    public void success(int status, String payload, boolean success) {
        // do nothing
    }

    public void execute(Context context, String dateOfBirth, String bundleId, ServiceResponseInterface listener) {
        this.context = context;

        url = SAAgeCheck.sdk.getURL();
        this.bundleId = bundleId;
        this.dateOfBirth = dateOfBirth;

        // case where either the user is logged or we don't need a logged user

        final Service instance = this;

        switch (getMethod()) {
            case GET: {
                network.sendGET(url + getEndpoint(), getQuery(), getHeader(), new SANetworkInterface() {
                    @Override
                    public void saDidGetResponse(int status, String payload, boolean success) {
                        instance.success(status, payload, success);
                    }
                });
                break;
            }
        }

    }

    public void execute(Context context, Object param, ServiceResponseInterface listener) {
        execute(context, bundleId, listener);
    }

}