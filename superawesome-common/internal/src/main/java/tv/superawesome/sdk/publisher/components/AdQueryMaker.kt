package tv.superawesome.sdk.publisher.components

import kotlinx.serialization.json.Json
import tv.superawesome.sdk.publisher.models.AdQuery
import tv.superawesome.sdk.publisher.models.AdRequest
import tv.superawesome.sdk.publisher.models.AdQueryBundle
import tv.superawesome.sdk.publisher.models.AdResponse
import tv.superawesome.sdk.publisher.models.EventData
import tv.superawesome.sdk.publisher.models.EventQuery
import tv.superawesome.sdk.publisher.models.EventQueryBundle
import tv.superawesome.sdk.publisher.models.EventType
import tv.superawesome.sdk.publisher.models.PerformanceMetricTags
import java.util.Locale

interface AdQueryMakerType {
    suspend fun makeAdQuery(request: AdRequest): AdQueryBundle
    fun makeImpressionQuery(adResponse: AdResponse): EventQueryBundle
    fun makeClickQuery(adResponse: AdResponse): EventQueryBundle
    fun makeVideoClickQuery(adResponse: AdResponse): EventQueryBundle
    fun makeEventQuery(adResponse: AdResponse, eventData: EventData): EventQueryBundle

    /**
     * Makes performance tags from the given [adResponse].
     */
    fun makePerformanceTags(adResponse: AdResponse): PerformanceMetricTags
}

@Suppress("LongParameterList")
class AdQueryMaker(
    private val device: DeviceType,
    private val sdkInfoType: SdkInfoType,
    private val connectionProvider: ConnectionProviderType,
    private val numberGenerator: NumberGeneratorType,
    private val idGenerator: IdGeneratorType,
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
            ct = connectionProvider.findConnectionType().ordinal,
            lang = locale.toString(),
            device = device.genericType.name,
            pos = request.pos,
            skip = request.skip,
            playbackMethod = request.playbackMethod,
            startDelay = request.startDelay,
            install = request.install,
            w = request.w,
            h = request.h,
            openRtbPartnerId = request.openRtbPartnerId,
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
            ct = connectionProvider.findConnectionType().ordinal,
            sdkVersion = sdkInfoType.version,
            rnd = adResponse.ad.random ?: "",
            type = null,
            noImage = null,
            data = null,
            adRequestId = adResponse.ad.adRequestId,
            openRtbPartnerId = adResponse.ad.openRtbPartnerId,
        ),
        options = buildOptions(requestOptions = adResponse.requestOptions)
    )

    override fun makeClickQuery(adResponse: AdResponse): EventQueryBundle = EventQueryBundle(
        EventQuery(
            placement = adResponse.placementId,
            bundle = sdkInfoType.bundle,
            creative = adResponse.ad.creative.id,
            lineItem = adResponse.ad.lineItemId,
            ct = connectionProvider.findConnectionType().ordinal,
            sdkVersion = sdkInfoType.version,
            rnd = adResponse.ad.random ?: "",
            type = EventType.ImpressionDownloaded,
            noImage = true,
            data = null,
            adRequestId = adResponse.ad.adRequestId,
            openRtbPartnerId = adResponse.ad.openRtbPartnerId,
        ),
        options = buildOptions(requestOptions = adResponse.requestOptions)
    )

    override fun makeVideoClickQuery(adResponse: AdResponse): EventQueryBundle = EventQueryBundle(
        EventQuery(
            placement = adResponse.placementId,
            bundle = sdkInfoType.bundle,
            creative = adResponse.ad.creative.id,
            lineItem = adResponse.ad.lineItemId,
            ct = connectionProvider.findConnectionType().ordinal,
            sdkVersion = sdkInfoType.version,
            rnd = adResponse.ad.random ?: "",
            type = null,
            noImage = null,
            data = null,
            adRequestId = adResponse.ad.adRequestId,
            openRtbPartnerId = adResponse.ad.openRtbPartnerId,
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
                ct = connectionProvider.findConnectionType().ordinal,
                sdkVersion = sdkInfoType.version,
                rnd = adResponse.ad.random ?: "",
                type = eventData.type,
                noImage = null,
                data = encodeData(eventData),
                adRequestId = adResponse.ad.adRequestId,
                openRtbPartnerId = adResponse.ad.openRtbPartnerId,
            ),
            options = buildOptions(requestOptions = adResponse.requestOptions)
        )

    override fun makePerformanceTags(adResponse: AdResponse): PerformanceMetricTags =
        PerformanceMetricTags(
            placementId = adResponse.placementId,
            lineItemId = adResponse.ad.lineItemId,
            creativeId = adResponse.ad.creative.id,
            format = adResponse.ad.creative.format,
            sdkVersion = sdkInfoType.version,
            connectionType = connectionProvider.findConnectionType().ordinal,
        )

    private fun encodeData(eventData: EventData): String =
        json.encodeToString(EventData.serializer(), eventData)

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
