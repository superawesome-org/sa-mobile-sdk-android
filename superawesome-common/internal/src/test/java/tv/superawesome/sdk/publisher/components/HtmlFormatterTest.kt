package tv.superawesome.sdk.publisher.components

import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import tv.superawesome.sdk.publisher.models.Ad
import tv.superawesome.sdk.publisher.models.Creative
import tv.superawesome.sdk.publisher.models.CreativeDetail
import tv.superawesome.sdk.publisher.models.CreativeFormatType
import tv.superawesome.sdk.publisher.testutil.ResourceReader
import kotlin.test.assertEquals

class HtmlFormatterTest {

    private val numberGenerator = mockk<NumberGeneratorType>()
    private val encoder = Encoder()

    private val sut = HtmlFormatter(numberGenerator, encoder)

    private val ad = Ad(
        campaignType = 0,
        showPadlock = false,
        lineItemId = 0,
        test = false,
        creative = Creative(
            id = 100,
            name = "test",
            format = CreativeFormatType.ImageWithLink,
            clickUrl = null,
            details = CreativeDetail(
                url = "http://www.superawesome.tv/img.jpg",
                image = "http://www.superawesome.tv/img.jpg",
                video = null,
                placementFormat = "",
                tag = null,
                width = 640,
                height = 480,
                duration = 0,
                vast = null,
            ),
            bumper = false,
            referral = null,
        ),
        isVpaid = false,
        random = null,
        adRequestId = null,
        openRtbPartnerId = null,
    )

    @Test
    fun `formatter can format image into html`() {
        // arrange
        val imageCreative = CreativeDetail(
            url = "http://www.superawesome.tv/img.jpg",
            image = "http://www.superawesome.tv/img.jpg",
            video = null,
            placementFormat = "",
            tag = null,
            width = 640,
            height = 480,
            duration = 0,
            vast = null,
        )

        val imageAd = ad.copy(creative = ad.creative.copy(details = imageCreative))

        // act
        val output = sut.formatImageIntoHtml(imageAd)

        // assert
        assertEquals("<img src='http://www.superawesome.tv/img.jpg' width='100%' height='100%' style='object-fit: contain;'/>", output)
    }

    @Test
    fun `formatter can format image into html, adding link if there is clickUrl`() {
        // arrange
        val imageCreative = CreativeDetail(
            url = "http://www.superawesome.tv/img.jpg",
            image = "http://www.superawesome.tv/img.jpg",
            video = null,
            placementFormat = "",
            tag = null,
            width = 640,
            height = 480,
            duration = 0,
            vast = null,
        )

        val imageAd = ad.copy(creative = ad.creative.copy(details = imageCreative, clickUrl = "http://test.com"))

        // act
        val output = sut.formatImageIntoHtml(imageAd)

        // assert
        assertEquals("<a href='http://test.com' target='_blank'><img src='http://www.superawesome.tv/img.jpg' width='100%' height='100%' style='object-fit: contain;'/></a>", output)
    }

    @Test
    fun `formatter can format rich media into html`() {
        // arrange
        every { numberGenerator.nextIntForCache() } returns 1

        val richMediaAd = ad.copy(
            creative = ad.creative.copy(
                format = CreativeFormatType.RichMedia,
            )
        )

        val url = "${richMediaAd.creative.details.url}?placement=1234&line_item=${richMediaAd.lineItemId}&creative=${richMediaAd.creative.id}&rnd=${numberGenerator.nextIntForCache()}"

        // act
        val output = sut.formatRichMediaIntoHtml(1234, richMediaAd)

        // assert
        assertEquals("<iframe style='padding:0;border:0;' width='100%' height='100%' src='$url'></iframe>", output)
    }

    @Test
    fun `formatter can format tag into html`() {
        // arrange
        val tag = ResourceReader.readResource("mock_tag.txt")
        val tagAd = ad.copy(
            creative = ad.creative.copy(
                format = CreativeFormatType.Tag,
                details = ad.creative.details.copy(
                    tag = tag
                )
            )
        )

        val expected = tag.replace("\\t", "")
            .replace("\\n", "")

        // act
        val output = sut.formatTagIntoHtml(tagAd)

        // assert
        assertEquals(expected, output)
    }
}