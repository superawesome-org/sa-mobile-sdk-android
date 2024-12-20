package tv.superawesome.sdk.publisher.models

import android.graphics.Color
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import tv.superawesome.sdk.publisher.ad.VideoAdConfig
import tv.superawesome.sdk.publisher.base.BaseTest
import tv.superawesome.sdk.publisher.components.AdQueryMaker
import tv.superawesome.sdk.publisher.components.ConnectionProviderType
import tv.superawesome.sdk.publisher.components.DeviceCategory
import tv.superawesome.sdk.publisher.components.DeviceType
import tv.superawesome.sdk.publisher.components.IdGeneratorType
import tv.superawesome.sdk.publisher.components.NumberGeneratorType
import tv.superawesome.sdk.publisher.components.SdkInfoType
import tv.superawesome.sdk.publisher.components.TimeProviderType
import java.util.Locale

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalSerializationApi::class)
class AdModelsTest: BaseTest() {

    @MockK
    lateinit var sdkInfoType: SdkInfoType

    @MockK
    lateinit var timeProvider: TimeProviderType

    @MockK
    lateinit var deviceType: DeviceType

    @MockK
    lateinit var connectionProviderType: ConnectionProviderType

    @MockK
    lateinit var numberGeneratorType: NumberGeneratorType

    @MockK
    lateinit var idGeneratorType: IdGeneratorType

    @MockK
    lateinit var json: Json

    @MockK
    lateinit var locale: Locale

    @InjectMockKs
    lateinit var queryMaker: AdQueryMaker

    @Before
    fun setup() {
        mockkStatic(Color::class)
        every { Color.rgb(any<Int>(), any<Int>(), any<Int>()) } returns 0
    }

    @Test
    fun `Ad isCPICampaign is true when expected`() {
        // Given
        val creative = mockk<Creative>(relaxed = true)
        val sut = Ad(
            campaignType = 1,
            showPadlock = false,
            lineItemId = 1,
            test = false,
            creative = creative,
            random = "123"
        )

        // Then
        assertTrue(sut.isCPICampaign())
    }

    @Test
    fun `Ad isCPICampaign is false when expected`() {
        // Given
        val creative = mockk<Creative>(relaxed = true)
        val sut = Ad(
            campaignType = 2,
            showPadlock = false,
            lineItemId = 1,
            test = false,
            creative = creative,
            random = "123"
        )

        // Then
        assertFalse(sut.isCPICampaign())
    }

    @Test
    fun `AdQueryBundle merges options to query correctly`() = runTest {
        // Given
        val options = mapOf("key1" to "value1", "key2" to 2)
        val request = DefaultAdRequest(
            false,
            10,
            20,
            30,
            40,
            50,
            60,
            70,
            options
        )
        coEvery { idGeneratorType.findDauId() } returns 99
        every { sdkInfoType.version } returns "sdk_version"
        every { sdkInfoType.bundle } returns "sdk_bundle"
        every { sdkInfoType.name } returns "sdk_name"
        every { numberGeneratorType.nextIntForCache() } returns 1
        every { connectionProviderType.findConnectionType() } returns ConnectionType.Cellular5g
        every { deviceType.genericType } returns DeviceCategory.PHONE
        every { locale.toString() } returns "en_en"
        every { timeProvider.millis() } returns 12345

        // When
        val sut = queryMaker.makeAdQuery(request, VideoAdConfig())

        // Then
        assertEquals("" +
            "{test=false, " +
            "sdkVersion=sdk_version, " +
            "rnd=1, " +
            "bundle=sdk_bundle, " +
            "name=sdk_name, " +
            "dauid=99, " +
            "ct=7, " +
            "lang=en_en, " +
            "device=PHONE, " +
            "pos=10, " +
            "skip=20, " +
            "playbackmethod=30, " +
            "startdelay=40, " +
            "instl=50, " +
            "w=60, " +
            "h=70, " +
            "timestamp=12345, " +
            "publisherConfiguration={\"parentalGateOn\":false,\"bumperPageOn\":false,\"closeWarning\":false,\"orientation\":0,\"closeAtEnd\":true,\"muteOnStart\":false,\"showMore\":false,\"startDelay\":0,\"closeButtonState\":2}, " +
            "key1=value1, " +
            "key2=2}",
            sut.build().toString()
        )
    }

    @Test
    fun `AdResponse isVpaid is true when expected`() {
        // Given
        val ad = mockk<Ad>(relaxed = true)
        val sut = AdResponse(10, ad)

        // When
        every { ad.isVpaid } returns true

        // Then
        assertTrue(sut.isVpaid())
    }

    @Test
    fun `AdResponse isVpaid is false when expected`() {
        // Given
        val ad = mockk<Ad>(relaxed = true)
        val sut = AdResponse(10, ad)

        // When
        every { ad.isVpaid } returns false

        // Then
        assertFalse(sut.isVpaid())
    }

    @Test
    fun `AdResponse isVideo is true when expected`() {
        // Given
        val ad = mockk<Ad>(relaxed = true)
        val sut = AdResponse(10, ad)

        // When
        every { ad.creative.format } returns CreativeFormatType.Video

        // Then
        assertTrue(sut.isVideo())
    }

    @Test
    fun `AdResponse isVideo is false when expected`() {
        // Given
        val ad = mockk<Ad>(relaxed = true)
        val sut = AdResponse(10, ad)

        // When
        every { ad.creative.format } returns CreativeFormatType.ImageWithLink

        // Then
        assertFalse(sut.isVideo())
    }

    @Test
    fun `AdResponse showPadlock is true when expected`() {
        // Given
        val ad = mockk<Ad>(relaxed = true)
        val sut = AdResponse(10, ad)

        // When
        every { ad.showPadlock } returns true

        // Then
        assertTrue(sut.shouldShowPadlock())
    }

    @Test
    fun `AdResponse showPadlock is false when expected`() {
        // Given
        val ad = mockk<Ad>(relaxed = true)
        val sut = AdResponse(10, ad)

        // When
        every { ad.showPadlock } returns false

        // Then
        assertFalse(sut.shouldShowPadlock())
    }

    @Test
    fun `AdResponse dataPair returns expected result`() {
        // Given
        val ad = mockk<Ad>(relaxed = true)
        val sut = AdResponse(10, ad)

        // When
        sut.baseUrl = "http://123.com"
        sut.html = "<html>123</html>"

        // Then
        val dataPair = sut.getDataPair()
        assertEquals(dataPair?.first, "http://123.com")
        assertEquals(dataPair?.second, "<html>123</html>")
    }
}