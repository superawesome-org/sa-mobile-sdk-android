@file:Suppress("MaxLineLength")
package tv.superawesome.sdk.publisher.components

import android.app.Activity
import android.graphics.Color
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.junit.Test
import tv.superawesome.sdk.publisher.ad.BannerAdConfig
import tv.superawesome.sdk.publisher.ad.InterstitialAdConfig
import tv.superawesome.sdk.publisher.ad.VideoAdConfig
import tv.superawesome.sdk.publisher.base.BaseTest
import tv.superawesome.sdk.publisher.models.Ad
import tv.superawesome.sdk.publisher.models.DefaultAdRequest
import tv.superawesome.sdk.publisher.models.AdResponse
import tv.superawesome.sdk.publisher.models.ConnectionType
import tv.superawesome.sdk.publisher.models.CreativeFormatType
import tv.superawesome.sdk.publisher.models.EventData
import tv.superawesome.sdk.publisher.models.EventType
import tv.superawesome.sdk.publisher.testutil.FakeFactory
import java.util.Locale
import kotlin.test.BeforeTest
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalSerializationApi::class)
internal class AdQueryMakerTest : BaseTest() {
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
    lateinit var json: Json

    @MockK
    lateinit var locale: Locale

    @InjectMockKs
    lateinit var queryMaker: AdQueryMaker

    private val initialOptions = mapOf("key1" to "value1", "key2" to 2)
    private val additionalOptions = mapOf("key3" to "value3", "key4" to 4)
    private val combinedOptions = mapOf(
        "key1" to "value1",
        "key2" to 2,
        "key3" to "value3",
        "key4" to 4
    )

    @BeforeTest
    fun prepare() {
        QueryAdditionalOptions.Companion.instance = null
        mockkStatic(Color::class)
        every { Color.rgb(any<Int>(), any<Int>(), any<Int>()) } returns 0
    }

    @Test
    fun test_adQuery() = runTest {
        // Given
        val request = DefaultAdRequest(false, 10, 20, 30, 40, 50, 60, 70)
        coEvery { idGeneratorType.findDauId() } returns 99
        every { sdkInfoType.version } returns "sdk_version"
        every { sdkInfoType.bundle } returns "sdk_bundle"
        every { sdkInfoType.name } returns "sdk_name"
        every { numberGeneratorType.nextIntForCache() } returns 33
        every { connectionProviderType.findConnectionType() } returns ConnectionType.Cellular4g
        every { deviceType.genericType } returns DeviceCategory.TABLET
        every { locale.toString() } returns "en_en"
        every { timeProvider.millis() } returns 12345678912345

        // When
        val baseQuery = queryMaker.makeAdQuery(request, InterstitialAdConfig()).parameters

        // Then
        assertEquals(false, baseQuery.test)
        assertEquals("sdk_version", baseQuery.sdkVersion)
        assertEquals(33, baseQuery.rnd)
        assertEquals("sdk_bundle", baseQuery.bundle)
        assertEquals("sdk_name", baseQuery.name)
        assertEquals(99, baseQuery.dauId)
        assertEquals(ConnectionType.Cellular4g.ordinal, baseQuery.ct)
        assertEquals("en_en", baseQuery.lang)
        assertEquals(DeviceCategory.TABLET.name, baseQuery.device)
        assertEquals(10, baseQuery.pos)
        assertEquals(20, baseQuery.skip)
        assertEquals(30, baseQuery.playbackMethod)
        assertEquals(40, baseQuery.startDelay)
        assertEquals(50, baseQuery.install)
        assertEquals(60, baseQuery.w)
        assertEquals(70, baseQuery.h)
        assertEquals(12345678912345, baseQuery.timestamp)
    }

    @Test
    fun test_adQuery_withOpenRtbPartnerId() = runTest {
        // Given
        val request = DefaultAdRequest(false, 10, 20, 30, 40, 50, 60, 70,  openRtbPartnerId = "12345")
        coEvery { idGeneratorType.findDauId() } returns 99
        every { sdkInfoType.version } returns "sdk_version"
        every { sdkInfoType.bundle } returns "sdk_bundle"
        every { sdkInfoType.name } returns "sdk_name"
        every { numberGeneratorType.nextIntForCache() } returns 33
        every { connectionProviderType.findConnectionType() } returns ConnectionType.Cellular4g
        every { deviceType.genericType } returns DeviceCategory.TABLET
        every { locale.toString() } returns "en_en"
        every { timeProvider.millis() } returns 12345678912345

        // When
        val baseQuery = queryMaker.makeAdQuery(request, InterstitialAdConfig()).parameters

        // Then
        assertEquals("12345", baseQuery.openRtbPartnerId)
    }

    @Test
    fun test_clickQuery() {
        // Given
        val ad = mockk<Ad>(relaxed = true) {
            every { creative.id } returns 20
            every { lineItemId } returns 30
            every { random } returns "33"
            every { adRequestId } returns "test-id"
            every { openRtbPartnerId } returns "12345"
        }
        val request = AdResponse(10, ad)
        every { sdkInfoType.version } returns "sdk_version"
        every { sdkInfoType.bundle } returns "sdk_bundle"
        every { numberGeneratorType.nextIntForCache() } returns 33
        every { connectionProviderType.findConnectionType() } returns ConnectionType.Cellular4g

        // When
        val baseQuery = queryMaker.makeClickQuery(request).parameters

        // Then
        assertEquals(10, baseQuery.placement)
        assertEquals("sdk_bundle", baseQuery.bundle)
        assertEquals(20, baseQuery.creative)
        assertEquals(30, baseQuery.lineItem)
        assertEquals(ConnectionType.Cellular4g.ordinal, baseQuery.ct)
        assertEquals("sdk_version", baseQuery.sdkVersion)
        assertEquals("33", baseQuery.rnd)
        assertEquals(EventType.ImpressionDownloaded, baseQuery.type)
        assertEquals(true, baseQuery.noImage)
        assertEquals(null, baseQuery.data)
        assertEquals("test-id", baseQuery.adRequestId)
        assertEquals("12345", baseQuery.openRtbPartnerId)
    }

    @Test
    fun test_videoClickQuery() {
        // Given
        val ad = mockk<Ad>(relaxed = true) {
            every { creative.id } returns 20
            every { lineItemId } returns 30
            every { random } returns "33"
            every { adRequestId } returns "test-id"
            every { openRtbPartnerId } returns "12345"
        }
        val request = AdResponse(10, ad)
        every { sdkInfoType.version } returns "sdk_version"
        every { sdkInfoType.bundle } returns "sdk_bundle"
        every { numberGeneratorType.nextIntForCache() } returns 33
        every { connectionProviderType.findConnectionType() } returns ConnectionType.Cellular4g

        // When
        val baseQuery = queryMaker.makeVideoClickQuery(request).parameters

        // Then
        assertEquals(10, baseQuery.placement)
        assertEquals("sdk_bundle", baseQuery.bundle)
        assertEquals(20, baseQuery.creative)
        assertEquals(30, baseQuery.lineItem)
        assertEquals(ConnectionType.Cellular4g.ordinal, baseQuery.ct)
        assertEquals("sdk_version", baseQuery.sdkVersion)
        assertEquals("33", baseQuery.rnd)
        assertEquals(null, baseQuery.type)
        assertEquals(null, baseQuery.noImage)
        assertEquals(null, baseQuery.data)
        assertEquals("test-id", baseQuery.adRequestId)
        assertEquals("12345", baseQuery.openRtbPartnerId)
    }

    @Test
    fun test_eventQuery() {
        // Given
        val ad = mockk<Ad>(relaxed = true) {
            every { creative.id } returns 20
            every { lineItemId } returns 30
            every { random } returns "33"
            every { adRequestId } returns "test-id"
            every { openRtbPartnerId } returns "12345"
        }
        val request = AdResponse(10, ad)
        val data = EventData(10, 30, 20, EventType.ParentalGateClose)
        every { sdkInfoType.version } returns "sdk_version"
        every { sdkInfoType.bundle } returns "sdk_bundle"
        every { numberGeneratorType.nextIntForCache() } returns 33
        every { connectionProviderType.findConnectionType() } returns ConnectionType.Cellular4g
        every { json.encodeToString(EventData.serializer(), data) } returns "encoded_uri"

        // When
        val baseQuery = queryMaker.makeEventQuery(request, data).parameters

        // Then
        assertEquals(10, baseQuery.placement)
        assertEquals("sdk_bundle", baseQuery.bundle)
        assertEquals(20, baseQuery.creative)
        assertEquals(30, baseQuery.lineItem)
        assertEquals(ConnectionType.Cellular4g.ordinal, baseQuery.ct)
        assertEquals("sdk_version", baseQuery.sdkVersion)
        assertEquals("33", baseQuery.rnd)
        assertEquals(null, baseQuery.noImage)
        assertEquals("encoded_uri", baseQuery.data)
        assertEquals("test-id", baseQuery.adRequestId)
        assertEquals("12345", baseQuery.openRtbPartnerId)
    }

    @Test
    fun test_adQuery_with_no_options() = runBlocking {
        // Given
        val request = DefaultAdRequest(
            false,
            10,
            20,
            30,
            40,
            50,
            60,
            70,
            null
        )
        every { connectionProviderType.findConnectionType() } returns ConnectionType.Cellular4g

        // When
        val query = queryMaker.makeAdQuery(request, InterstitialAdConfig())

        // Then
        assertTrue(query.options.isNullOrEmpty())
    }

    @Test
    fun test_adQuery_with_initial_options_only() = runTest {
        // Given
        QueryAdditionalOptions.Companion.instance =
            QueryAdditionalOptions(initialOptions)

        val request = DefaultAdRequest(
            false,
            10,
            20,
            30,
            40,
            50,
            60,
            70,
            null
        )

        every { connectionProviderType.findConnectionType() } returns ConnectionType.Cellular4g

        // When
        val query = queryMaker.makeAdQuery(request, InterstitialAdConfig())

        // Then
        verifyOptions(query.options!!, initialOptions)
    }

    @Test
    fun test_adQuery_with_additional_options_only() = runTest {
        // Given
        val request = DefaultAdRequest(
            false,
            10,
            20,
            30,
            40,
            50,
            60,
            70,
            additionalOptions
        )
        every { connectionProviderType.findConnectionType() } returns ConnectionType.Cellular4g

        // When
        val query = queryMaker.makeAdQuery(request, InterstitialAdConfig())

        // Then
        verifyOptions(query.options!!, additionalOptions)
    }

    @Test
    fun test_adQuery_with_initial_options_and_additional_options() = runTest {
        // Given
        QueryAdditionalOptions.Companion.instance =
            QueryAdditionalOptions(initialOptions)

        val request = DefaultAdRequest(
            false,
            10,
            20,
            30,
            40,
            50,
            60,
            70,
            additionalOptions
        )
        every { connectionProviderType.findConnectionType() } returns ConnectionType.Cellular4g

        // When
        val query = queryMaker.makeAdQuery(request, InterstitialAdConfig())

        // Then
        verifyOptions(query.options!!, combinedOptions)
    }

    @Test
    fun test_adQuery_additional_options_can_override_initial_options_when_keys_conflict() = runTest {

        // Given
        QueryAdditionalOptions.Companion.instance =
            QueryAdditionalOptions(initialOptions)

        val additionalOptions = mapOf("key1" to "x")

        val request = DefaultAdRequest(
            false,
            10,
            20,
            30,
            40,
            50,
            60,
            70,
            additionalOptions
        )
        every { connectionProviderType.findConnectionType() } returns ConnectionType.Cellular4g

        // When
        val query = queryMaker.makeAdQuery(request, InterstitialAdConfig())

        // Then
        val expectedOptions = mapOf("key1" to "x", "key2" to 2)
        verifyOptions(query.options!!, expectedOptions)
    }

    @Test
    fun test_adQuery_unsuitable_types_are_not_included() = runTest {

        // Given
        QueryAdditionalOptions.Companion.instance =
            QueryAdditionalOptions(initialOptions)

        val additionalOptions = mapOf("key3" to Activity(), "key4" to 4)

        val request = DefaultAdRequest(
            false,
            10,
            20,
            30,
            40,
            50,
            60,
            70,
            additionalOptions
        )
        every { connectionProviderType.findConnectionType() } returns ConnectionType.Cellular4g

        // When
        val query = queryMaker.makeAdQuery(request, InterstitialAdConfig())

        // Then
        val expectedOptions = mapOf("key1" to "value1", "key2" to 2, "key4" to 4)
        verifyOptions(query.options!!, expectedOptions)
    }

    @Test
    fun test_built_adQuery_without_additional_options_builds_original_adQuery_only() = runTest {
        // Given
        val request = DefaultAdRequest(
            false,
            10,
            20,
            30,
            40,
            50,
            60,
            70
        )
        every { connectionProviderType.findConnectionType() } returns ConnectionType.Cellular4g
        every { locale.toString() } returns "en_en"

        // When
        val query = queryMaker.makeAdQuery(request, VideoAdConfig()).build()

        // Then
        assertEquals(
            "" +
                    "{test=false, " +
                    "sdkVersion=, " +
                    "rnd=0, " +
                    "bundle=, " +
                    "name=, " +
                    "dauid=0, " +
                    "ct=6, " +
                    "lang=en_en, " +
                    "device=, " +
                    "pos=10, " +
                    "skip=20, " +
                    "playbackmethod=30, " +
                    "startdelay=40, " +
                    "instl=50, " +
                    "w=60, " +
                    "h=70, " +
                    "timestamp=0, " +
                    "publisherConfiguration={\"parentalGateOn\":false,\"bumperPageOn\":false,\"closeWarning\":false,\"orientation\":0,\"closeAtEnd\":true,\"muteOnStart\":false,\"showMore\":false,\"startDelay\":0,\"closeButtonState\":2}" +
                    "}",
            query.toString()
        )
    }

    @Test
    fun test_built_adQuery_with_additional_options_builds_original_adQuery_with_additional_options() = runTest {
        // Given
        val request = DefaultAdRequest(
            false,
            10,
            20,
            30,
            40,
            50,
            60,
            70,
            options = additionalOptions
        )
        every { connectionProviderType.findConnectionType() } returns ConnectionType.Cellular4g
        every { locale.toString() } returns "en_en"

        // When
        val query = queryMaker.makeAdQuery(request, VideoAdConfig()).build()

        // Then
        assertEquals(
            "" +
                    "{test=false, " +
                    "sdkVersion=, " +
                    "rnd=0, " +
                    "bundle=, " +
                    "name=, " +
                    "dauid=0, " +
                    "ct=6, " +
                    "lang=en_en, " +
                    "device=, " +
                    "pos=10, " +
                    "skip=20, " +
                    "playbackmethod=30, " +
                    "startdelay=40, " +
                    "instl=50, " +
                    "w=60, " +
                    "h=70, " +
                    "timestamp=0, " +
                    "publisherConfiguration={\"parentalGateOn\":false,\"bumperPageOn\":false,\"closeWarning\":false,\"orientation\":0,\"closeAtEnd\":true,\"muteOnStart\":false,\"showMore\":false,\"startDelay\":0,\"closeButtonState\":2}, " +
                    "key3=value3, " +
                    "key4=4}",
            query.toString()
        )
    }

    @Test
    fun test_built_adQuery_with_initial_and_additional_options_builds_original_adQuery_with_initial_and_additional_options() = runTest {
        // Given
        QueryAdditionalOptions.Companion.instance =
            QueryAdditionalOptions(initialOptions)
        val request = DefaultAdRequest(
            false,
            10,
            20,
            30,
            40,
            50,
            60,
            70,
            options = additionalOptions
        )
        every { connectionProviderType.findConnectionType() } returns ConnectionType.Cellular4g
        every { locale.toString() } returns "en_en"

        // When
        val query = queryMaker.makeAdQuery(request, VideoAdConfig()).build()

        // Then
        assertEquals(
            "" +
                    "{test=false, " +
                    "sdkVersion=, " +
                    "rnd=0, " +
                    "bundle=, " +
                    "name=, " +
                    "dauid=0, " +
                    "ct=6, " +
                    "lang=en_en, " +
                    "device=, " +
                    "pos=10, " +
                    "skip=20, " +
                    "playbackmethod=30, " +
                    "startdelay=40, " +
                    "instl=50, " +
                    "w=60, " +
                    "h=70, " +
                    "timestamp=0, " +
                    "publisherConfiguration={\"parentalGateOn\":false,\"bumperPageOn\":false,\"closeWarning\":false,\"orientation\":0,\"closeAtEnd\":true,\"muteOnStart\":false,\"showMore\":false,\"startDelay\":0,\"closeButtonState\":2}, " +
                    "key1=value1, " +
                    "key2=2, " +
                    "key3=value3, " +
                    "key4=4}",
            query.toString()
        )
    }

    @Test
    fun test_built_adQuery_with_interstitial_adConfig() = runTest {
        // Given
        QueryAdditionalOptions.Companion.instance =
            QueryAdditionalOptions(initialOptions)
        val request = DefaultAdRequest(
            false,
            10,
            20,
            30,
            40,
            50,
            60,
            70,
            options = additionalOptions
        )
        every { connectionProviderType.findConnectionType() } returns ConnectionType.Cellular4g
        every { locale.toString() } returns "en_en"

        // When
        val query = queryMaker.makeAdQuery(request, InterstitialAdConfig()).build()

        // Then
        assertEquals(
            "" +
                    "{test=false, " +
                    "sdkVersion=, " +
                    "rnd=0, " +
                    "bundle=, " +
                    "name=, " +
                    "dauid=0, " +
                    "ct=6, " +
                    "lang=en_en, " +
                    "device=, " +
                    "pos=10, " +
                    "skip=20, " +
                    "playbackmethod=30, " +
                    "startdelay=40, " +
                    "instl=50, " +
                    "w=60, " +
                    "h=70, " +
                    "timestamp=0, " +
                    "publisherConfiguration={\"parentalGateOn\":false,\"bumperPageOn\":false,\"orientation\":0,\"closeButtonState\":2}, " +
                    "key1=value1, " +
                    "key2=2, " +
                    "key3=value3, " +
                    "key4=4}",
            query.toString()
        )
    }

    @Test
    fun test_built_adQuery_with_banner_adConfig() = runTest {
        // Given
        QueryAdditionalOptions.Companion.instance =
            QueryAdditionalOptions(initialOptions)
        val request = DefaultAdRequest(
            false,
            10,
            20,
            30,
            40,
            50,
            60,
            70,
            options = additionalOptions
        )
        every { connectionProviderType.findConnectionType() } returns ConnectionType.Cellular4g
        every { locale.toString() } returns "en_en"

        // When
        val query = queryMaker.makeAdQuery(request, BannerAdConfig()).build()

        // Then
        assertEquals(
            "" +
                    "{test=false, " +
                    "sdkVersion=, " +
                    "rnd=0, " +
                    "bundle=, " +
                    "name=, " +
                    "dauid=0, " +
                    "ct=6, " +
                    "lang=en_en, " +
                    "device=, " +
                    "pos=10, " +
                    "skip=20, " +
                    "playbackmethod=30, " +
                    "startdelay=40, " +
                    "instl=50, " +
                    "w=60, " +
                    "h=70, " +
                    "timestamp=0, " +
                    "publisherConfiguration={\"parentalGateOn\":false,\"bumperPageOn\":false}, " +
                    "key1=value1, " +
                    "key2=2, " +
                    "key3=value3, " +
                    "key4=4}",
            query.toString()
        )
    }

    @Test
    fun `makePerformanceTags returns PerformanceMetricTags`() {
        // Arrange
        val adResponse = FakeFactory.makeFakeAdResponse()

        // Act
        val output = queryMaker.makePerformanceTags(adResponse)

        // Assert
        assertEquals("1", output.placementId)
        assertEquals("123", output.lineItemId)
        assertEquals("10", output.creativeId)
        assertEquals(CreativeFormatType.Tag, output.format)
        assertEquals("0", output.connectionType)
    }

    @Test
    fun `makeImpressionQuery returns an EventQueryBundle`() {
        // Arrange
        val adResponse = FakeFactory.makeFakeAdResponse()

        // Act
        val output = queryMaker.makeImpressionQuery(adResponse)

        // Assert
        assertEquals(10, output.parameters.creative)
        assertEquals(123, output.parameters.lineItem)
        assertEquals(1, output.parameters.placement)
    }

    private fun verifyOptions(options: Map<String, Any>, expectedOptions: Map<String, Any>) {

        assertEquals(options.size, expectedOptions.size)

        expectedOptions.forEach { (key, value) ->
            assertEquals(value, options[key])
        }
    }
}
