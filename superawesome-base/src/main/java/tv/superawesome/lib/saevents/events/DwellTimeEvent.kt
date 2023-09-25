package tv.superawesome.lib.saevents.events

import org.json.JSONObject
import tv.superawesome.lib.sajsonparser.SAJsonParser
import tv.superawesome.lib.samodelspace.saad.SAAd
import tv.superawesome.lib.sasession.session.ISASession
import tv.superawesome.lib.sautils.SAUtils
import java.util.concurrent.Executor

class DwellTimeEvent(
    ad: SAAd? = null,
    session: ISASession,
    executor: Executor,
    timeout: Int,
    retryDelay: Long,
    isDebug: Boolean,
) :
    SAServerEvent(ad, session, executor, timeout, retryDelay, isDebug) {

    override fun getEndpoint(): String {
        return "/event"
    }

    @Suppress("MagicNumber")
    override fun getQuery(): JSONObject? {
        return try {
            SAJsonParser.newObject(
                "type", "viewTime",
                "placement", ad.placementId,
                "bundle", session.packageName,
                "creative", ad.creative.id,
                "line_item", ad.lineItemId,
                "ct", session.connectionType.ordinal,
                "sdkVersion", session.version,
                "rnd", session.cachebuster,
                "adRequestId", ad.adRequestId,
                "openRtbPartnerId", ad.openRtbPartnerId,
                "data", SAUtils.encodeDictAsJsonDict(SAJsonParser.newObject(
                    "creative", ad.creative.id,
                    "line_item", ad.lineItemId,
                    "placement", ad.placementId,
                    "type", "viewTime",
                    "value", 5,
                )),
            )
        } catch (e: Exception) {
            JSONObject()
        }
    }
}
