package tv.superawesome.sdk.publisher.common.components

import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.junit.Test
import tv.superawesome.sdk.publisher.common.base.BaseTest
import tv.superawesome.sdk.publisher.common.models.*
import java.util.*
import kotlin.test.assertEquals

class AdQueryMakerTest : BaseTest()  {
    @MockK
    lateinit var deviceType: DeviceType

    @MockK
    lateinit var sdkInfoType: SdkInfoType

    @MockK
    lateinit var connectionProviderType: ConnectionProviderType

    @MockK
    lateinit var numberGeneratorType: NumberGeneratorType

    @MockK
    lateinit var idGeneratorType: IdGeneratorType

    @MockK
    lateinit var encoderType: EncoderType

    @MockK
    lateinit var json: Json

    @MockK
    lateinit var locale: Locale

    @InjectMockKs
    lateinit var queryMaker: AdQueryMaker

    @Test
    fun test_adQuery() {
        // Given
        val request = AdRequest(false, 10, 20, 30, 40, 50, 60, 70)
        coEvery { idGeneratorType.findDauId() } returns 99
        every { sdkInfoType.version } returns "sdk_version"
        every { sdkInfoType.bundle } returns "sdk_bundle"
        every { sdkInfoType.name } returns "sdk_name"
        every { numberGeneratorType.nextIntForCache() } returns 33
        every { connectionProviderType.findConnectionType() } returns ConnectionType.cellular4g
        every { deviceType.genericType } returns DeviceCategory.tablet
        every { locale.toString() } returns "en_en"

        // When
        val query = runBlocking { queryMaker.makeAdQuery(request) }

        // Then
        assertEquals(false, query.test)
        assertEquals("sdk_version", query.sdkVersion)
        assertEquals(33, query.rnd)
        assertEquals("sdk_bundle", query.bundle)
        assertEquals("sdk_name", query.name)
        assertEquals(99, query.dauid)
        assertEquals(ConnectionType.cellular4g, query.ct)
        assertEquals("en_en", query.lang)
        assertEquals(DeviceCategory.tablet.name, query.device)
        assertEquals(10, query.pos)
        assertEquals(20, query.skip)
        assertEquals(30, query.playbackmethod)
        assertEquals(40, query.startdelay)
        assertEquals(50, query.instl)
        assertEquals(60, query.w)
        assertEquals(70, query.h)
    }

    @Test
    fun test_clickQuery() {
        // Given
        val request = mockk<AdResponse> {}
        every { sdkInfoType.version } returns "sdk_version"
        every { sdkInfoType.bundle } returns "sdk_bundle"
        every { numberGeneratorType.nextIntForCache() } returns 33
        every { connectionProviderType.findConnectionType() } returns ConnectionType.cellular4g

        // When
        val query = queryMaker.makeClickQuery(request)

        // Then
        assertEquals(10, query.placement)
        assertEquals("sdk_bundle", query.bundle)
        assertEquals(20, query.creative)
        assertEquals(30, query.line_item)
        assertEquals(ConnectionType.cellular4g, query.ct)
        assertEquals("sdk_version", query.sdkVersion)
        assertEquals(33, query.rnd)
        assertEquals(EventType.impressionDownloaded, query.type)
        assertEquals(true, query.no_image)
        assertEquals(null, query.data)
    }

    @Test
    fun test_videoClickQuery() {
        // Given
        val request = mockk<AdResponse> {}
        every { sdkInfoType.version } returns "sdk_version"
        every { sdkInfoType.bundle } returns "sdk_bundle"
        every { numberGeneratorType.nextIntForCache() } returns 33
        every { connectionProviderType.findConnectionType() } returns ConnectionType.cellular4g

        // When
        val query = queryMaker.makeVideoClickQuery(request)

        // Then
        assertEquals(10, query.placement)
        assertEquals("sdk_bundle", query.bundle)
        assertEquals(20, query.creative)
        assertEquals(30, query.line_item)
        assertEquals(ConnectionType.cellular4g, query.ct)
        assertEquals("sdk_version", query.sdkVersion)
        assertEquals(33, query.rnd)
        assertEquals(null, query.type)
        assertEquals(null, query.no_image)
        assertEquals(null, query.data)
    }

    @Test
    fun test_eventQuery() {
        // Given
        val data = EventData(10, 30, 20, EventType.parentalGateClose)
        val request = mockk<AdResponse> {}
        every { sdkInfoType.version } returns "sdk_version"
        every { sdkInfoType.bundle } returns "sdk_bundle"
        every { numberGeneratorType.nextIntForCache() } returns 33
        every { connectionProviderType.findConnectionType() } returns ConnectionType.cellular4g
//        every { json.stringify<EventData>(any(), any()) } returns ""
        every { encoderType.encodeUri(any()) } returns "encoded_uri"

        // When
        val query = queryMaker.makeEventQuery(request, data)

        // Then
        assertEquals(10, query.placement)
        assertEquals("sdk_bundle", query.bundle)
        assertEquals(20, query.creative)
        assertEquals(30, query.line_item)
        assertEquals(ConnectionType.cellular4g, query.ct)
        assertEquals("sdk_version", query.sdkVersion)
        assertEquals(33, query.rnd)
        assertEquals(null, query.no_image)
        assertEquals("encoded_uri", query.data)
    }
}