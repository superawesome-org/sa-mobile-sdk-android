package tv.superawesome.lib.saevents.events;

import org.json.JSONObject;

import java.util.concurrent.Executor;

import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.samodelspace.saad.SAAd;
import tv.superawesome.lib.samodelspace.saad.SACreativeFormat;
import tv.superawesome.lib.sasession.session.ISASession;

public class SAClickEvent extends SAServerEvent {

    public SAClickEvent(SAAd ad, ISASession session) {
        super(ad, session);
    }

    public SAClickEvent(SAAd ad, ISASession session, Executor executor, int timeout, long retryDelay, boolean isDebug) {
        super(ad, session, executor, timeout, retryDelay, isDebug);
    }

    @Override
    public String getEndpoint() {
        return ad != null && ad.creative != null ? ad.creative.format == SACreativeFormat.video ? "/video/click" : "/click" : "";
    }

    @Override
    public JSONObject getQuery () {
        try {
            return SAJsonParser.newObject(
                "placement", ad.placementId,
                "bundle", session.getPackageName(),
                "creative", ad.creative.id,
                "line_item", ad.lineItemId,
                "ct", session.getConnectionType().ordinal(),
                "sdkVersion", session.getVersion(),
                "rnd", session.getCachebuster(),
                "ad_request_id", ad.adRequestId
            );
        } catch (Exception e) {
            return new JSONObject();
        }
    }
}
