package tv.superawesome.sdk.publisher.common.components

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import tv.superawesome.sdk.publisher.common.network.datasources.NetworkDataSourceType
import tv.superawesome.sdk.publisher.common.models.Constants
import tv.superawesome.sdk.publisher.common.models.CreativeFormatType
import tv.superawesome.sdk.publisher.common.models.VastType
import tv.superawesome.sdk.publisher.common.network.DataResult
import tv.superawesome.sdk.publisher.common.testutil.FakeFactory.exampleHtml
import tv.superawesome.sdk.publisher.common.testutil.FakeFactory.exampleParamString
import tv.superawesome.sdk.publisher.common.testutil.FakeFactory.exampleUrl
import tv.superawesome.sdk.publisher.common.testutil.FakeFactory.exampleVastUrl
import tv.superawesome.sdk.publisher.common.testutil.FakeFactory.makeFakeAd
import tv.superawesome.sdk.publisher.common.testutil.FakeFactory.makeVastAd
import kotlin.test.assertTrue

internal class AdProcessorTest {

    @MockK
    private lateinit var htmlFormatter: HtmlFormatterType

    @MockK
    private lateinit var vastParser: VastParserType

    @MockK
    private lateinit var networkDataSource: NetworkDataSourceType

    @MockK
    private lateinit var encoder: EncoderType

    private lateinit var adProcessor: AdProcessor

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        adProcessor = AdProcessor(htmlFormatter, vastParser, networkDataSource, encoder)
        coEvery { encoder.encodeUrlParamsFromObject(any()) } returns exampleParamString
    }

    @Test
    fun `ensure image with link processes correctly`() = runBlocking {
        // Given
        coEvery { htmlFormatter.formatImageIntoHtml(any(), any()) } returns exampleHtml

        // When
        val response = adProcessor.process(99, makeFakeAd(CreativeFormatType.ImageWithLink), null)

        // Then
        assertEquals(response.isSuccess, true)
        assertEquals(response.optValue?.baseUrl, exampleUrl)
        assertEquals(response.optValue?.html, exampleHtml)
        assertEquals(response.optValue?.referral, exampleParamString)
    }

    @Test
    fun `ensure rich media processes correctly`() = runBlocking {
        // Given
        coEvery { htmlFormatter.formatRichMediaIntoHtml(99, any()) } returns exampleHtml

        // When
        val response = adProcessor.process(99, makeFakeAd(CreativeFormatType.RichMedia), null)

        // Then
        assertEquals(response.isSuccess, true)
        assertEquals(response.optValue?.baseUrl, exampleUrl)
        assertEquals(response.optValue?.html, exampleHtml)
        assertEquals(response.optValue?.referral, exampleParamString)
    }

    @Test
    fun `ensure tag processes correctly`() = runBlocking {
        // Given
        coEvery { htmlFormatter.formatTagIntoHtml(any()) } returns exampleHtml

        // When
        val response = adProcessor.process(99, makeFakeAd(CreativeFormatType.Tag), null)

        // Then
        assertEquals(response.isSuccess, true)
        assertEquals(response.optValue?.baseUrl, Constants.defaultSuperAwesomeUrl)
        assertEquals(response.optValue?.html, exampleHtml)
        assertEquals(response.optValue?.referral, exampleParamString)
    }

    @Test
    fun `ensure video processes correctly`() = runBlocking {
        // Given
        coEvery { networkDataSource.downloadFile(any()) } returns DataResult.Success(exampleVastUrl)
        coEvery { networkDataSource.getData(any()) } returns DataResult.Success("")
        coEvery { vastParser.parse(any()) } returns makeVastAd(exampleUrl)

        // When
        val response = adProcessor.process(99, makeFakeAd(CreativeFormatType.Video), null)

        // Then
        assertTrue(response.isSuccess)
        assertEquals(exampleUrl, response.optValue?.baseUrl)
    }

    @Test
    fun `video with no url returns failure`() = runBlocking {
        // Given
        coEvery { networkDataSource.downloadFile(any()) } returns DataResult.Success(exampleVastUrl)
        coEvery { networkDataSource.getData(any()) } returns DataResult.Success("")
        coEvery { vastParser.parse(any()) } returns makeVastAd(null)

        // When
        val response = adProcessor.process(99, makeFakeAd(CreativeFormatType.Video), null)

        // Then
        assertTrue(response.isFailure)
        assertEquals((response as DataResult.Failure).error.message, "empty url")
    }

    // handle vast tests
    @Test
    fun `network request is failed then return failure`() = runBlocking {
        // Given
        val ad = makeFakeAd(CreativeFormatType.Video)
        coEvery { networkDataSource.getData(any()) } returns DataResult.Failure(Exception())

        // When
        val result = adProcessor.process(1, ad, null)

        // Then
        assertTrue(result.isFailure)
    }

    @Test
    fun `network request successful with no redirect then return success`() = runBlocking {
        // Given
        val ad = makeFakeAd(CreativeFormatType.Video)
        val passedVastAd = makeVastAd()
        val filePath = "filePath"

        coEvery { networkDataSource.downloadFile(any()) } returns DataResult.Success(filePath)
        coEvery { networkDataSource.getData(any()) } returns DataResult.Success(filePath)

        coEvery { vastParser.parse(any()) } returns passedVastAd

        // When
        val result = adProcessor.process(1, ad, null)

        // Then
        assertTrue(result.isSuccess)
    }

    @Test
    fun `Given Wrapped vast ad then redirect url called`() = runBlocking {
        // Given
        val firstAdUrl = "wwww.first.com"
        val redirectUrl = "www.second.com"
        val ad = makeFakeAd(CreativeFormatType.Video, vastUrl = firstAdUrl)

        val firstVast = makeVastAd(type = VastType.Wrapper, redDirect = redirectUrl)
        val redirectVast = makeVastAd(type = VastType.Wrapper, redDirect = null)

        coEvery { networkDataSource.downloadFile(any()) } returns DataResult.Success("filePath")

        coEvery { networkDataSource.getData(firstAdUrl) } returns DataResult.Success(firstAdUrl)
        coEvery { networkDataSource.getData(redirectUrl) } returns DataResult.Success(redirectUrl)

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
    fun `Given Inline vast ad then redirect url not called`() = runBlocking {
        // Given
        val firstAdUrl = "wwww.first.com"
        val redirectUrl = "www.second.com"
        val ad = makeFakeAd(CreativeFormatType.Video, vastUrl = firstAdUrl)

        val firstVast = makeVastAd(type = VastType.InLine, redDirect = redirectUrl)
        val redirectVast = makeVastAd(type = VastType.Wrapper, redDirect = null)

        coEvery { networkDataSource.downloadFile(any()) } returns DataResult.Success("filePath")

        coEvery { networkDataSource.getData(firstAdUrl) } returns DataResult.Success(firstAdUrl)
        coEvery { networkDataSource.getData(redirectUrl) } returns DataResult.Success(redirectUrl)

        coEvery { vastParser.parse(firstAdUrl) } returns firstVast
        coEvery { vastParser.parse(redirectUrl) } returns redirectVast

        // When
        adProcessor.process(1, ad, null)

        // Then
        coVerify(exactly = 0) { vastParser.parse(redirectUrl) }
        coVerify { networkDataSource.getData(firstAdUrl) }
        coVerify(exactly = 0) { networkDataSource.getData(redirectUrl) }
    }
}
