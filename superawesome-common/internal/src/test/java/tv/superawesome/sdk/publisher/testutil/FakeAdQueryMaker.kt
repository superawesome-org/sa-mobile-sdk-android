package tv.superawesome.sdk.publisher.testutil

import kotlinx.serialization.json.Json
import tv.superawesome.sdk.publisher.components.AdQueryMakerType
import tv.superawesome.sdk.publisher.components.EncoderType
import tv.superawesome.sdk.publisher.models.AdQuery
import tv.superawesome.sdk.publisher.models.AdQueryBundle
import tv.superawesome.sdk.publisher.models.AdRequest
import tv.superawesome.sdk.publisher.models.AdResponse
import tv.superawesome.sdk.publisher.models.ConnectionType
import tv.superawesome.sdk.publisher.models.EventData
import tv.superawesome.sdk.publisher.models.EventQuery
import tv.superawesome.sdk.publisher.models.EventQueryBundle
import tv.superawesome.sdk.publisher.models.EventType
import java.net.URLEncoder

class FakeAdQueryMaker : AdQueryMakerType {

    private val json = Json {
        allowStructuredMapKeys = true
        ignoreUnknownKeys = true
    }

    private val encoder = object : EncoderType {
        override fun encodeUri(string: String?): String {
            return URLEncoder.encode(string, "UTF-8")
        }

        override fun encodeUrlParamsFromObject(map: Map<String, Any?>): String {
            val params = map.entries
                .map { entry -> "${entry.key}=${entry.value}" }
                .joinToString(separator = "&") { element -> element }
            return encodeUri(params)
        }

    }

    override suspend fun makeAdQuery(request: AdRequest): AdQueryBundle =
        AdQueryBundle(
            parameters = AdQuery(
                test = false,
                sdkVersion = "0.0",
                rnd = 0,
                bundle = "",
                name = "",
                dauId = 0,
                ct = ConnectionType.Unknown.ordinal,
                lang = "",
                device = "",
                pos = 0,
                skip = 0,
                playbackMethod = 0,
                startDelay = 0,
                install = 0,
                w = 0,
                h = 0,
                timestamp = 0L,
            ),
            options = null,
        )

    override fun makeImpressionQuery(adResponse: AdResponse): EventQueryBundle =
        makeEventQuery(type = EventType.ImpressionDownloaded)

    override fun makeClickQuery(adResponse: AdResponse): EventQueryBundle =
        makeEventQuery(type = null)

    override fun makeVideoClickQuery(adResponse: AdResponse): EventQueryBundle =
        makeEventQuery(type = null)

    override fun makeEventQuery(adResponse: AdResponse, eventData: EventData): EventQueryBundle =
        makeEventQuery(type = null, data = eventData)

    private fun makeEventQuery(type: EventType?, data: EventData? = null) =
        EventQueryBundle(
            parameters = EventQuery(
                placement = 0,
                bundle = "",
                creative = 0,
                lineItem = 0,
                ct = ConnectionType.Unknown.ordinal,
                sdkVersion = "0.0",
                rnd = "",
                type = type,
                noImage = false,
                data = data?.let { encodeData(data) },
            ),
            options = null,
        )

    private fun encodeData(eventData: EventData): String {
        val dataAsJson = json.encodeToString(EventData.serializer(), eventData)
        return encoder.encodeUri(dataAsJson)
    }
}
