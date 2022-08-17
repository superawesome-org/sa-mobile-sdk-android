package tv.superawesome.sdk.publisher.common.components

import kotlinx.serialization.json.Json
import tv.superawesome.sdk.publisher.common.models.*
import java.util.*

interface AdQueryMakerType {
    suspend fun makeAdQuery(request: AdRequest): AdQuery
    fun makeImpressionQuery(adResponse: AdResponse): EventQuery
    fun makeClickQuery(adResponse: AdResponse): EventQuery
    fun makeVideoClickQuery(adResponse: AdResponse): EventQuery
    fun makeEventQuery(adResponse: AdResponse, eventData: EventData): EventQuery
}

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

    override suspend fun makeAdQuery(request: AdRequest): AdQuery = AdQuery(
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
        timestamp = timeProvider.millis()
    )

    override fun makeImpressionQuery(adResponse: AdResponse): EventQuery = EventQuery(
        placement = adResponse.placementId,
        bundle = sdkInfoType.bundle,
        creative = adResponse.ad.creative.id,
        lineItem = adResponse.ad.lineItemId,
        ct = connectionProvider.findConnectionType(),
        sdkVersion = sdkInfoType.version,
        rnd = numberGenerator.nextIntForCache(),
        type = null,
        noImage = null,
        data = null
    )

    override fun makeClickQuery(adResponse: AdResponse): EventQuery = EventQuery(
        placement = adResponse.placementId,
        bundle = sdkInfoType.bundle,
        creative = adResponse.ad.creative.id,
        lineItem = adResponse.ad.lineItemId,
        ct = connectionProvider.findConnectionType(),
        sdkVersion = sdkInfoType.version,
        rnd = numberGenerator.nextIntForCache(),
        type = EventType.ImpressionDownloaded,
        noImage = true,
        data = null
    )

    override fun makeVideoClickQuery(adResponse: AdResponse): EventQuery = EventQuery(
        placement = adResponse.placementId,
        bundle = sdkInfoType.bundle,
        creative = adResponse.ad.creative.id,
        lineItem = adResponse.ad.lineItemId,
        ct = connectionProvider.findConnectionType(),
        sdkVersion = sdkInfoType.version,
        rnd = numberGenerator.nextIntForCache(),
        type = null,
        noImage = null,
        data = null
    )

    override fun makeEventQuery(adResponse: AdResponse, eventData: EventData): EventQuery {
        return EventQuery(
            placement = adResponse.placementId,
            bundle = sdkInfoType.bundle,
            creative = adResponse.ad.creative.id,
            lineItem = adResponse.ad.lineItemId,
            ct = connectionProvider.findConnectionType(),
            sdkVersion = sdkInfoType.version,
            rnd = numberGenerator.nextIntForCache(),
            type = eventData.type,
            noImage = null,
            data = encodeData(eventData)
        )
    }

    private fun encodeData(eventData: EventData): String {
        val dataAsJson = json.encodeToString(EventData.serializer(), eventData)
        return encoder.encodeUri(dataAsJson)
    }
}
