package tv.superawesome.sdk.publisher.common.components

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import tv.superawesome.sdk.publisher.common.datasources.NetworkDataSourceType
import tv.superawesome.sdk.publisher.common.models.Ad
import tv.superawesome.sdk.publisher.common.models.Constants
import tv.superawesome.sdk.publisher.common.models.Creative
import tv.superawesome.sdk.publisher.common.models.CreativeDetail
import tv.superawesome.sdk.publisher.common.models.CreativeFormatType
import tv.superawesome.sdk.publisher.common.models.CreativeReferral
import tv.superawesome.sdk.publisher.common.models.VastAd
import tv.superawesome.sdk.publisher.common.network.DataResult
import kotlin.test.assertTrue

class AdProcessorTest {

    @MockK
    private lateinit var htmlFormatter: HtmlFormatterType

    @MockK
    private lateinit var vastParser: VastParserType

    @MockK
    private lateinit var networkDataSource: NetworkDataSourceType

    @MockK
    private lateinit var encoder: EncoderType

    private lateinit var subject: AdProcessor

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        subject = AdProcessor(htmlFormatter, vastParser, networkDataSource, encoder)
        coEvery { encoder.encodeUrlParamsFromObject(any()) } returns exampleParamString
    }

    @Test
    fun `ensure image with link processes correctly`() = runBlocking {
        // arrange
        coEvery { htmlFormatter.formatImageIntoHtml(any()) } returns exampleHtml

        // act
        val response = subject.process(99, makeFakeAd(CreativeFormatType.image_with_link))

        // assert
        assertEquals(response.isSuccess, true)
        assertEquals(response.optValue?.baseUrl, exampleUrl)
        assertEquals(response.optValue?.html, exampleHtml)
        assertEquals(response.optValue?.referral, exampleParamString)
    }

    @Test
    fun `ensure rich media processes correctly`() = runBlocking {
        // arrange
        coEvery { htmlFormatter.formatRichMediaIntoHtml(99, any()) } returns exampleHtml

        // act
        val response = subject.process(99, makeFakeAd(CreativeFormatType.rich_media))

        // assert
        assertEquals(response.isSuccess, true)
        assertEquals(response.optValue?.baseUrl, exampleUrl)
        assertEquals(response.optValue?.html, exampleHtml)
        assertEquals(response.optValue?.referral, exampleParamString)
    }

    @Test
    fun `ensure tag processes correctly`() = runBlocking {
        // arrange
        coEvery { htmlFormatter.formatTagIntoHtml(any()) } returns exampleHtml

        // act
        val response = subject.process(99, makeFakeAd(CreativeFormatType.tag))

        // assert
        assertEquals(response.isSuccess, true)
        assertEquals(response.optValue?.baseUrl, Constants.defaultSuperAwesomeUrl)
        assertEquals(response.optValue?.html, exampleHtml)
        assertEquals(response.optValue?.referral, exampleParamString)
    }

    @Test
    fun `ensure video processes correctly`() = runBlocking {
        // arrange
        coEvery { networkDataSource.downloadFile(any()) } returns DataResult.Success(exampleVastUrl)
        coEvery { networkDataSource.getData(any()) } returns DataResult.Success("")
        coEvery { vastParser.parse(any()) } returns VastAd(url = exampleUrl)

        // act
        val response = subject.process(99, makeFakeAd(CreativeFormatType.video))

        // assert
        assertTrue(response.isSuccess)
        assertEquals(exampleUrl, response.optValue?.baseUrl)
    }

    // handle vast tests
    @Test
    fun `get data is fail initial is returned`() = runBlocking {
        // arrange
        val initialVastAd = VastAd("www.here.com")
        coEvery { networkDataSource.getData(any()) } returns DataResult.Failure(Exception())

        // act
        val vastAd = subject.handleVast("", initialVastAd)

        // assert
        assertEquals(initialVastAd, vastAd)
    }

    @Test
    fun `get data is success is passed by passer, redirect null`() = runBlocking {
        // arrange
        val vastString = "pass string"
        val initialVastAd = VastAd("www.here.com")
        val passedVastAd = VastAd()
        coEvery { networkDataSource.getData(any()) } returns DataResult.Success(vastString)
        coEvery { vastParser.parse(any()) } returns passedVastAd

        // act
        val actual = subject.handleVast("", initialVastAd)

        // assert
        assertEquals(passedVastAd, actual)
    }

    @Test
    fun `get data is success is passed by passer with redirect `() = runBlocking {
        // arrange
        val vastString = "pass string"
        val initialVastAd = VastAd("www.here.com")
        val passedVastAd = VastAd(redirect = "www.amp.co.uk")
        coEvery { networkDataSource.getData(any()) } returns DataResult.Success(vastString)
        coEvery { vastParser.parse(any()) } returns passedVastAd

        // act
        val actual = subject.handleVast("", initialVastAd)

        // assert
        assertEquals(passedVastAd, actual)
    }

    private fun makeFakeAd(type: CreativeFormatType) = Ad(
        advertiserId = null,
        publisherId = 123,
        moat = 10.0f,
        is_fill = false,
        is_fallback = false,
        campaign_type = 123,
        campaign_id = 123,
        is_house = false,
        safe_ad_approved = false,
        show_padlock = false,
        line_item_id = 123,
        test = false,
        app = 123,
        device = "android",
        creative = Creative(
            id = 10,
            format = type,
            referral = CreativeReferral(),
            details = CreativeDetail(
                url = exampleUrl,
                video = "",
                placement_format = "",
                width = 1,
                height = 1,
                duration = 1,
                image = exampleUrl,
                vast = exampleVastUrl
            )
        )
    )

    private val exampleUrl = "https://www.superAwesome.com"
    private val exampleHtml = "<test></test>"
    private val exampleParamString = "id=12&name=tester"
    private val exampleVastUrl = "https://www.superAwesome.com"
    private val fakeValidVast = "fakeValidVast"
}
