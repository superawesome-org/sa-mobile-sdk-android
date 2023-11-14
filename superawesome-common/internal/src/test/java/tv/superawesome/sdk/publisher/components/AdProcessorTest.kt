package tv.superawesome.sdk.publisher.components

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import tv.superawesome.sdk.publisher.models.Constants
import tv.superawesome.sdk.publisher.models.CreativeFormatType
import tv.superawesome.sdk.publisher.models.VastType
import tv.superawesome.sdk.publisher.network.datasources.NetworkDataSourceType
import tv.superawesome.sdk.publisher.testutil.FakeFactory.exampleHtml
import tv.superawesome.sdk.publisher.testutil.FakeFactory.exampleParamString
import tv.superawesome.sdk.publisher.testutil.FakeFactory.exampleUrl
import tv.superawesome.sdk.publisher.testutil.FakeFactory.exampleVastUrl
import tv.superawesome.sdk.publisher.testutil.FakeFactory.makeFakeAd
import tv.superawesome.sdk.publisher.testutil.FakeFactory.makeFakeVpaidAd
import tv.superawesome.sdk.publisher.testutil.FakeFactory.makeVastAd
import kotlin.test.assertFails
import kotlin.test.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
internal class AdProcessorTest {

    @MockK
    private lateinit var htmlFormatter: HtmlFormatterType

    @MockK
    private lateinit var vastParser: VastParserType

    @MockK
    private lateinit var networkDataSource: NetworkDataSourceType

    @MockK
    private lateinit var encoder: EncoderType

    @MockK
    private lateinit var videoCache: VideoCache

    private lateinit var adProcessor: AdProcessor

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        adProcessor = AdProcessor(
            htmlFormatter,
            vastParser,
            networkDataSource,
            videoCache,
            encoder
        )
        coEvery { encoder.encodeUrlParamsFromObject(any()) } returns exampleParamString
    }

    @Test
    fun `ensure image with link processes correctly`() = runTest {
        // Given
        coEvery { htmlFormatter.formatImageIntoHtml(any()) } returns exampleHtml

        // When
        val response = adProcessor.process(99, makeFakeAd(CreativeFormatType.ImageWithLink), null)

        // Then
        assertEquals(response.baseUrl, exampleUrl)
        assertEquals(response.html, exampleHtml)
        assertEquals(response.referral, exampleParamString)
        assertNull(response.ad.openRtbPartnerId)
    }

    @Test
    fun `ensure rich media processes correctly`() = runTest {
        // Given
        coEvery { htmlFormatter.formatRichMediaIntoHtml(99, any()) } returns exampleHtml

        // When
        val response = adProcessor.process(99, makeFakeAd(CreativeFormatType.RichMedia), null)

        // Then
        assertEquals(response.baseUrl, exampleUrl)
        assertEquals(response.html, exampleHtml)
        assertEquals(response.referral, exampleParamString)
    }

    @Test
    fun `ensure tag processes correctly`() = runTest {
        // Given
        coEvery { htmlFormatter.formatTagIntoHtml(any()) } returns exampleHtml

        // When
        val response = adProcessor.process(99, makeFakeAd(CreativeFormatType.Tag), null)

        // Then
        assertEquals(response.baseUrl, Constants.defaultSuperAwesomeUrl)
        assertEquals(response.html, exampleHtml)
        assertEquals(response.referral, exampleParamString)
    }

    @Test
    fun `ensure video processes correctly`() = runTest {
        // Given
        coEvery { videoCache.get(any()) } returns exampleVastUrl
        coEvery { networkDataSource.getData(any()) } returns Result.success("")
        coEvery { vastParser.parse(any()) } returns makeVastAd(exampleUrl)

        // When
        val response = adProcessor.process(99, makeFakeAd(CreativeFormatType.Video), null)

        // Then
        assertEquals(exampleUrl, response.baseUrl)
    }

    @Test
    fun `ensure vpaid processes correctly`() = runTest {
        // Given
        coEvery { videoCache.get(any()) } returns exampleVastUrl
        coEvery { networkDataSource.getData(any()) } returns Result.success("")

        // When
        val response = adProcessor.process(99, makeFakeVpaidAd(), null)

        // Then
        assertEquals("<html><script src='http://test.com/ad.js'/></html>", response.html)
        assertEquals("http://test.com", response.baseUrl)
    }

    @Test
    fun `video with no url returns failure`() = runTest {
        // Given
        coEvery { videoCache.get(any()) } returns exampleVastUrl
        coEvery { networkDataSource.getData(any()) } returns Result.success("")
        coEvery { vastParser.parse(any()) } returns makeVastAd(null)

        val exception = assertFails {
            // When
            adProcessor.process(99, makeFakeAd(CreativeFormatType.Video), null)
        }

        // Then
        assertEquals(exception.message, "Empty url")
    }

    // handle vast tests
    @Test
    fun `network request is failed then return failure`() = runTest {
        // Given
        val ad = makeFakeAd(CreativeFormatType.Video)
        coEvery { networkDataSource.getData(any()) } returns Result.failure(Exception())

        // Then
        assertFails {
            // When
            adProcessor.process(1, ad, null)
        }
    }

    @Test
    fun `network request successful with no redirect then return success`() = runTest {
        // Given
        val ad = makeFakeAd(CreativeFormatType.Video)
        val passedVastAd = makeVastAd()
        val filePath = "filePath"

        coEvery { videoCache.get(any()) } returns filePath
        coEvery { networkDataSource.getData(any()) } returns Result.success(filePath)

        coEvery { vastParser.parse(any()) } returns passedVastAd

        // When
        val result = adProcessor.process(1, ad, null)

        // Then
        assertEquals(result.filePath, filePath)
    }

    @Test
    fun `Given Wrapped vast ad then redirect url called`() = runTest {
        // Given
        val firstAdUrl = "wwww.first.com"
        val redirectUrl = "www.second.com"
        val ad = makeFakeAd(CreativeFormatType.Video, vastUrl = firstAdUrl)

        val firstVast = makeVastAd(type = VastType.Wrapper, redirect = redirectUrl)
        val redirectVast = makeVastAd(type = VastType.Wrapper, redirect = null)

        coEvery { videoCache.get(any()) } returns "filePath"

        coEvery { networkDataSource.getData(firstAdUrl) } returns Result.success(firstAdUrl)
        coEvery { networkDataSource.getData(redirectUrl) } returns Result.success(redirectUrl)

        coEvery { vastParser.parse(firstAdUrl) } returns firstVast
        coEvery { vastParser.parse(redirectUrl) } returns redirectVast

        // When
        adProcessor.process(1, ad, null)

        // Then
        coVerify { vastParser.parse(redirectUrl) }
        coVerify { networkDataSource.getData(firstAdUrl) }
        coVerify { networkDataSource.getData(redirectUrl) }
    }

    @Test
    fun `Given Inline vast ad then redirect url not called`() = runTest {
        // Given
        val firstAdUrl = "wwww.first.com"
        val redirectUrl = "www.second.com"
        val ad = makeFakeAd(CreativeFormatType.Video, vastUrl = firstAdUrl)

        val firstVast = makeVastAd(type = VastType.InLine, redirect = redirectUrl)
        val redirectVast = makeVastAd(type = VastType.Wrapper, redirect = null)

        coEvery { videoCache.get(any()) } returns "filePath"

        coEvery { networkDataSource.getData(firstAdUrl) } returns Result.success(firstAdUrl)
        coEvery { networkDataSource.getData(redirectUrl) } returns Result.success(redirectUrl)

        coEvery { vastParser.parse(firstAdUrl) } returns firstVast
        coEvery { vastParser.parse(redirectUrl) } returns redirectVast

        // When
        adProcessor.process(1, ad, null)

        // Then
        coVerify(exactly = 0) { vastParser.parse(redirectUrl) }
        coVerify { networkDataSource.getData(firstAdUrl) }
        coVerify(exactly = 0) { networkDataSource.getData(redirectUrl) }
    }

    @Test
    fun `given an openRTBPartnerId, then it should be included in the response ad`() = runTest {
        // Given
        coEvery { htmlFormatter.formatImageIntoHtml(any()) } returns exampleHtml

        // When
        val response = adProcessor.process(
            99,
            makeFakeAd(CreativeFormatType.ImageWithLink),
            null,
            "12345"
        )

        // Then
        assertEquals("12345", response.ad.openRtbPartnerId)
    }
}
