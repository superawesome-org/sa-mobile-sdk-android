package tv.superawesome.lib.saevents.events;

import org.json.JSONObject;

import java.util.concurrent.Executor;

import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.samodelspace.saad.SAAd;
import tv.superawesome.lib.sasession.session.ISASession;
import tv.superawesome.lib.sautils.SAUtils;

public class SAPGSuccessEvent extends SAServerEvent {

    public SAPGSuccessEvent(SAAd ad, ISASession session) {
        super(ad, session);
    }

    public SAPGSuccessEvent(SAAd ad, ISASession session, Executor executor, int timeout, boolean isDebug) {
        super(ad, session, executor, timeout, isDebug);
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
                    "rnd", session.getCachebuster(),
                    "data", SAUtils.encodeDictAsJsonDict(SAJsonParser.newObject(
                            "placement", ad.placementId,
                            "line_item", ad.lineItemId,
                            "creative", ad.creative.id,
                            "type", "parentalGateSuccess")));
        } catch (Exception e) {
            return new JSONObject();
        }
    }
}
