package tv.superawesome.sdk.publisher.common.components

import kotlinx.serialization.json.Json
import tv.superawesome.sdk.publisher.common.models.*
import java.util.*

interface AdQueryMakerType {
    suspend fun makeAdQuery(request: AdRequest): AdQuery
    fun makeImpressionQuery(request: EventRequest): EventQuery
    fun makeClickQuery(request: EventRequest): EventQuery
    fun makeVideoClickQuery(request: EventRequest): EventQuery
    fun makeEventQuery(request: EventRequest): EventQuery
}

class AdQueryMaker(private val device: DeviceType,
                   private val sdkInfoType: SdkInfoType,
                   private val connectionProvider: ConnectionProviderType,
                   private val numberGenerator: NumberGeneratorType,
                   private val idGenerator: IdGeneratorType,
                   private val encoder: EncoderType,
                   private val json: Json,
                   private val locale: Locale) : AdQueryMakerType {

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

    override fun makeImpressionQuery(request: EventRequest): EventQuery = EventQuery(
            placement = request.placementId,
            bundle = sdkInfoType.bundle,
            creative = request.creativeId,
            line_item = request.lineItemId,
            ct = connectionProvider.findConnectionType(),
            sdkVersion = sdkInfoType.version,
            rnd = numberGenerator.nextIntForCache(),
            type = null,
            no_image = null,
            data = null
    )

    override fun makeClickQuery(request: EventRequest): EventQuery = EventQuery(
            placement = request.placementId,
            bundle = sdkInfoType.bundle,
            creative = request.creativeId,
            line_item = request.lineItemId,
            ct = connectionProvider.findConnectionType(),
            sdkVersion = sdkInfoType.version,
            rnd = numberGenerator.nextIntForCache(),
            type = EventType.impressionDownloaded,
            no_image = true,
            data = null
    )

    override fun makeVideoClickQuery(request: EventRequest): EventQuery = EventQuery(
            placement = request.placementId,
            bundle = sdkInfoType.bundle,
            creative = request.creativeId,
            line_item = request.lineItemId,
            ct = connectionProvider.findConnectionType(),
            sdkVersion = sdkInfoType.version,
            rnd = numberGenerator.nextIntForCache(),
            type = null,
            no_image = null,
            data = null
    )

    override fun makeEventQuery(request: EventRequest): EventQuery = EventQuery(
            placement = request.placementId,
            bundle = sdkInfoType.bundle,
            creative = request.creativeId,
            line_item = request.lineItemId,
            ct = connectionProvider.findConnectionType(),
            sdkVersion = sdkInfoType.version,
            rnd = numberGenerator.nextIntForCache(),
            type = request.type,
            no_image = null,
            data = encodeData(request)
    )

    private fun encodeData(request: EventRequest): String? = if (request.data != null) {
        val dataAsJson = json.encodeToString(EventData.serializer(), request.data)
        encoder.encodeUri(dataAsJson)
    } else {
        null
    }
}