package tv.superawesome.lib.saevents.events

import org.json.JSONObject
import tv.superawesome.lib.sajsonparser.SAJsonParser
import tv.superawesome.lib.samodelspace.saad.SAAd
import tv.superawesome.lib.sasession.session.ISASession
import java.util.concurrent.Executor

class DwellTimeEvent(
    ad: SAAd? = null,
    session: ISASession,
    executor: Executor,
    timeout: Int,
    isDebug: Boolean
) :
    SAServerEvent(ad, session, executor, timeout, isDebug) {

    override fun getEndpoint(): String {
        return "/event"
    }

    override fun getQuery(): JSONObject? {
        return try {
            SAJsonParser.newObject(
                "type", "custom.analytics.DWELL_TIME",
                "placement", ad.placementId,
                "bundle", session.packageName,
                "creative", ad.creative.id,
                "line_item", ad.lineItemId,
                "ct", session.connectionType.ordinal,
                "sdkVersion", session.version,
                "rnd", session.cachebuster
            )
        } catch (e: Exception) {
            JSONObject()
        }
    }
}