package tv.superawesome.sdk.publisher.common.components

import kotlinx.serialization.json.Json
import tv.superawesome.sdk.publisher.common.models.AdQuery
import tv.superawesome.sdk.publisher.common.models.AdQueryBundle
import tv.superawesome.sdk.publisher.common.models.AdRequest
import tv.superawesome.sdk.publisher.common.models.AdResponse
import tv.superawesome.sdk.publisher.common.models.EventData
import tv.superawesome.sdk.publisher.common.models.EventQuery
import tv.superawesome.sdk.publisher.common.models.EventQueryBundle
import tv.superawesome.sdk.publisher.common.models.EventType
import java.util.Locale

interface AdQueryMakerType {
    suspend fun makeAdQuery(request: AdRequest): AdQueryBundle
    fun makeImpressionQuery(adResponse: AdResponse): EventQueryBundle
    fun makeClickQuery(adResponse: AdResponse): EventQueryBundle
    fun makeVideoClickQuery(adResponse: AdResponse): EventQueryBundle
    fun makeEventQuery(adResponse: AdResponse, eventData: EventData): EventQueryBundle
}

@Suppress("LongParameterList")
class AdQueryMaker(
    private val device: DeviceType,
    private val sdkInfoType: SdkInfoType,
    private val connectionProvider: ConnectionProviderType,
    private val numberGenerator: NumberGeneratorType,
    private val idGenerator: IdGeneratorType,
    private val encoder: EncoderType,
    private val json: Json,
    private val locale: Locale,
    private val timeProvider: TimeProviderType,
) : AdQueryMakerType {

    override suspend fun makeAdQuery(request: AdRequest): AdQueryBundle = AdQueryBundle(
        AdQuery(
            test = request.test,
            sdkVersion = sdkInfoType.version,
            rnd = numberGenerator.nextIntForCache(),
            bundle = sdkInfoType.bundle,
            name = sdkInfoType.name,
            dauId = idGenerator.findDauId(),
            ct = connectionProvider.findConnectionType(),
            lang = locale.toString(),
            device = device.genericType.name,
            pos = request.pos,
            skip = request.skip,
            playbackMethod = request.playbackMethod,
            startDelay = request.startDelay,
            install = request.install,
            w = request.w,
            h = request.h,
            timestamp = timeProvider.millis(),
        ),
        options = buildOptions(requestOptions = request.options)
    )

    override fun makeImpressionQuery(adResponse: AdResponse): EventQueryBundle = EventQueryBundle(
        EventQuery(
            placement = adResponse.placementId,
            bundle = sdkInfoType.bundle,
            creative = adResponse.ad.creative.id,
            lineItem = adResponse.ad.lineItemId,
            ct = connectionProvider.findConnectionType(),
            sdkVersion = sdkInfoType.version,
            rnd = adResponse.ad.random ?: "",
            type = null,
            noImage = null,
            data = null,
        ),
        options = buildOptions(requestOptions = adResponse.requestOptions)
    )

    override fun makeClickQuery(adResponse: AdResponse): EventQueryBundle = EventQueryBundle(
        EventQuery(
            placement = adResponse.placementId,
            bundle = sdkInfoType.bundle,
            creative = adResponse.ad.creative.id,
            lineItem = adResponse.ad.lineItemId,
            ct = connectionProvider.findConnectionType(),
            sdkVersion = sdkInfoType.version,
            rnd = adResponse.ad.random ?: "",
            type = EventType.ImpressionDownloaded,
            noImage = true,
            data = null
        ),
        options = buildOptions(requestOptions = adResponse.requestOptions)
    )

    override fun makeVideoClickQuery(adResponse: AdResponse): EventQueryBundle = EventQueryBundle(
        EventQuery(
            placement = adResponse.placementId,
            bundle = sdkInfoType.bundle,
            creative = adResponse.ad.creative.id,
            lineItem = adResponse.ad.lineItemId,
            ct = connectionProvider.findConnectionType(),
            sdkVersion = sdkInfoType.version,
            rnd = adResponse.ad.random ?: "",
            type = null,
            noImage = null,
            data = null
        ),
        options = buildOptions(requestOptions = adResponse.requestOptions)
    )

    override fun makeEventQuery(adResponse: AdResponse, eventData: EventData): EventQueryBundle =
        EventQueryBundle(
            EventQuery(
                placement = adResponse.placementId,
                bundle = sdkInfoType.bundle,
                creative = adResponse.ad.creative.id,
                lineItem = adResponse.ad.lineItemId,
                ct = connectionProvider.findConnectionType(),
                sdkVersion = sdkInfoType.version,
                rnd = adResponse.ad.random ?: "",
                type = eventData.type,
                noImage = null,
                data = encodeData(eventData)
            ),
            options = buildOptions(requestOptions = adResponse.requestOptions)
        )

    private fun encodeData(eventData: EventData): String {
        val dataAsJson = json.encodeToString(EventData.serializer(), eventData)
        return encoder.encodeUri(dataAsJson)
    }

    private fun buildOptions(requestOptions: Map<String, Any>?): Map<String, Any> {
        val optionsDict = mutableMapOf<String, Any>()

        QueryAdditionalOptions.instance?.options?.let {
            merge(new = it, original = optionsDict)
        }

        requestOptions?.let {
            merge(new = it, original = optionsDict)
        }

        return optionsDict
    }

    private fun merge(new: Map<String, Any>, original: MutableMap<String, Any>) {
        new.forEach { (key, value) ->
            when (value) {
                is String -> original[key] = value
                is Int -> original[key] = value
            }
        }
    }
}
