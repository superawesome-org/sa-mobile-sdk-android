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

class AdQueryMakerTest : BaseTest() {
    @MockK
    lateinit var timeProvider: TimeProviderType

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
        every { connectionProviderType.findConnectionType() } returns ConnectionType.Cellular4g
        every { deviceType.genericType } returns DeviceCategory.tablet
        every { locale.toString() } returns "en_en"
        every { timeProvider.millis() } returns 12345678912345

        // When
        val query = runBlocking { queryMaker.makeAdQuery(request) }

        // Then
        assertEquals(false, query.test)
        assertEquals("sdk_version", query.sdkVersion)
        assertEquals(33, query.rnd)
        assertEquals("sdk_bundle", query.bundle)
        assertEquals("sdk_name", query.name)
        assertEquals(99, query.dauId)
        assertEquals(ConnectionType.Cellular4g, query.ct)
        assertEquals("en_en", query.lang)
        assertEquals(DeviceCategory.tablet.name, query.device)
        assertEquals(10, query.pos)
        assertEquals(20, query.skip)
        assertEquals(30, query.playbackMethod)
        assertEquals(40, query.startDelay)
        assertEquals(50, query.install)
        assertEquals(60, query.w)
        assertEquals(70, query.h)
        assertEquals(12345678912345, query.timestamp)
    }

    @Test
    fun test_clickQuery() {
        // Given
        val ad = mockk<Ad>(relaxed = true) {
            every { creative.id } returns 20
            every { lineItemId } returns 30
        }
        val request = AdResponse(10, ad)
        every { sdkInfoType.version } returns "sdk_version"
        every { sdkInfoType.bundle } returns "sdk_bundle"
        every { numberGeneratorType.nextIntForCache() } returns 33
        every { connectionProviderType.findConnectionType() } returns ConnectionType.Cellular4g

        // When
        val query = queryMaker.makeClickQuery(request)

        // Then
        assertEquals(10, query.placement)
        assertEquals("sdk_bundle", query.bundle)
        assertEquals(20, query.creative)
        assertEquals(30, query.lineItem)
        assertEquals(ConnectionType.Cellular4g, query.ct)
        assertEquals("sdk_version", query.sdkVersion)
        assertEquals(33, query.rnd)
        assertEquals(EventType.ImpressionDownloaded, query.type)
        assertEquals(true, query.noImage)
        assertEquals(null, query.data)
    }

    @Test
    fun test_videoClickQuery() {
        // Given
        val ad = mockk<Ad>(relaxed = true) {
            every { creative.id } returns 20
            every { lineItemId } returns 30
        }
        val request = AdResponse(10, ad)
        every { sdkInfoType.version } returns "sdk_version"
        every { sdkInfoType.bundle } returns "sdk_bundle"
        every { numberGeneratorType.nextIntForCache() } returns 33
        every { connectionProviderType.findConnectionType() } returns ConnectionType.Cellular4g

        // When
        val query = queryMaker.makeVideoClickQuery(request)

        // Then
        assertEquals(10, query.placement)
        assertEquals("sdk_bundle", query.bundle)
        assertEquals(20, query.creative)
        assertEquals(30, query.lineItem)
        assertEquals(ConnectionType.Cellular4g, query.ct)
        assertEquals("sdk_version", query.sdkVersion)
        assertEquals(33, query.rnd)
        assertEquals(null, query.type)
        assertEquals(null, query.noImage)
        assertEquals(null, query.data)
    }

    @Test
    fun test_eventQuery() {
        // Given
        val ad = mockk<Ad>(relaxed = true) {
            every { creative.id } returns 20
            every { lineItemId } returns 30
        }
        val request = AdResponse(10, ad)
        val data = EventData(10, 30, 20, EventType.ParentalGateClose)
        every { sdkInfoType.version } returns "sdk_version"
        every { sdkInfoType.bundle } returns "sdk_bundle"
        every { numberGeneratorType.nextIntForCache() } returns 33
        every { connectionProviderType.findConnectionType() } returns ConnectionType.Cellular4g
        every { encoderType.encodeUri(any()) } returns "encoded_uri"

        // When
        val query = queryMaker.makeEventQuery(request, data)

        // Then
        assertEquals(10, query.placement)
        assertEquals("sdk_bundle", query.bundle)
        assertEquals(20, query.creative)
        assertEquals(30, query.lineItem)
        assertEquals(ConnectionType.Cellular4g, query.ct)
        assertEquals("sdk_version", query.sdkVersion)
        assertEquals(33, query.rnd)
        assertEquals(null, query.noImage)
        assertEquals("encoded_uri", query.data)
    }
}