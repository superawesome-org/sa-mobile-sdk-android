package tv.superawesome.lib.saevents.events;

import org.json.JSONObject;

import java.util.concurrent.Executor;

import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.samodelspace.saad.SAAd;
import tv.superawesome.lib.sasession.session.ISASession;
import tv.superawesome.lib.sautils.SAUtils;

public class SAPGFailEvent extends SAServerEvent {

    public SAPGFailEvent(SAAd ad, ISASession session) {
        super(ad, session);
    }

    public SAPGFailEvent(SAAd ad, ISASession session, Executor executor, int timeout, long retryDelay, boolean isDebug) {
        super(ad, session, executor, timeout, retryDelay, isDebug);
    }

    @Override
    public String getEndpoint() {
        return "/event";
    }

    @Override
    public JSONObject getQuery() {
        try {
            return SAJsonParser.newObject(
                    "sdkVersion", session.getVersion(),
                    "ct", session.getConnectionType(),
                    "bundle", session.getPackageName(),
                    "rnd", ad.rnd,
                    "data", SAUtils.encodeDictAsJsonDict(SAJsonParser.newObject(
                            "placement", ad.placementId,
                            "line_item", ad.lineItemId,
                            "creative", ad.creative.id,
                            "type", "parentalGateFail")));
        } catch (Exception e) {
            return new JSONObject();
        }
    }
}
