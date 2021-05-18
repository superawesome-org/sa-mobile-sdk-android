package tv.superawesome.lib.saevents.events

import java.util.concurrent.Executor
import org.json.JSONObject
import tv.superawesome.lib.sajsonparser.SAJsonParser
import tv.superawesome.lib.samodelspace.saad.SAAd
import tv.superawesome.lib.sasession.session.ISASession

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
                "value", 1
            )
        } catch (e: Exception) {
            JSONObject()
        }
    }
}