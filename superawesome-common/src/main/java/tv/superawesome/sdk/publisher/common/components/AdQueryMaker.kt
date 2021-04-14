package tv.superawesome.sdk.publisher.common.components

import kotlinx.serialization.json.Json
import tv.superawesome.sdk.publisher.common.models.AdQuery
import tv.superawesome.sdk.publisher.common.models.AdRequest
import tv.superawesome.sdk.publisher.common.models.AdResponse
import tv.superawesome.sdk.publisher.common.models.EventData
import tv.superawesome.sdk.publisher.common.models.EventQuery
import tv.superawesome.sdk.publisher.common.models.EventType
import java.util.Locale

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
) : AdQueryMakerType {

    override suspend fun makeAdQuery(request: AdRequest): AdQuery = AdQuery(
        test = request.test,
        sdkVersion = sdkInfoType.version,
        rnd = numberGenerator.nextIntForCache(),
        bundle = sdkInfoType.bundle,
        name = sdkInfoType.name,
        dauid = idGenerator.findDauId(),
        ct = connectionProvider.findConnectionType(),
        lang = locale.toString(),
        device = device.genericType.name,
        pos = request.pos,
        skip = request.skip,
        playbackmethod = request.playbackmethod,
        startdelay = request.startdelay,
        instl = request.instl,
        w = request.w,
        h = request.h
    )

    override fun makeImpressionQuery(adResponse: AdResponse): EventQuery = EventQuery(
        placement = adResponse.placementId,
        bundle = sdkInfoType.bundle,
        creative = adResponse.ad.creative.id,
        line_item = adResponse.ad.line_item_id,
        ct = connectionProvider.findConnectionType(),
        sdkVersion = sdkInfoType.version,
        rnd = numberGenerator.nextIntForCache(),
        type = null,
        no_image = null,
        data = null
    )

    override fun makeClickQuery(adResponse: AdResponse): EventQuery = EventQuery(
        placement = adResponse.placementId,
        bundle = sdkInfoType.bundle,
        creative = adResponse.ad.creative.id,
        line_item = adResponse.ad.line_item_id,
        ct = connectionProvider.findConnectionType(),
        sdkVersion = sdkInfoType.version,
        rnd = numberGenerator.nextIntForCache(),
        type = EventType.impressionDownloaded,
        no_image = true,
        data = null
    )

    override fun makeVideoClickQuery(adResponse: AdResponse): EventQuery = EventQuery(
        placement = adResponse.placementId,
        bundle = sdkInfoType.bundle,
        creative = adResponse.ad.creative.id,
        line_item = adResponse.ad.line_item_id,
        ct = connectionProvider.findConnectionType(),
        sdkVersion = sdkInfoType.version,
        rnd = numberGenerator.nextIntForCache(),
        type = null,
        no_image = null,
        data = null
    )

    override fun makeEventQuery(adResponse: AdResponse, eventData: EventData): EventQuery {
        return EventQuery(
            placement = adResponse.placementId,
            bundle = sdkInfoType.bundle,
            creative = adResponse.ad.creative.id,
            line_item = adResponse.ad.line_item_id,
            ct = connectionProvider.findConnectionType(),
            sdkVersion = sdkInfoType.version,
            rnd = numberGenerator.nextIntForCache(),
            type = eventData.type,
            no_image = null,
            data = encodeData(eventData)
        )
    }

    private fun encodeData(eventData: EventData): String {
        val dataAsJson = json.encodeToString(EventData.serializer(), eventData)
        return encoder.encodeUri(dataAsJson)
    }
}
