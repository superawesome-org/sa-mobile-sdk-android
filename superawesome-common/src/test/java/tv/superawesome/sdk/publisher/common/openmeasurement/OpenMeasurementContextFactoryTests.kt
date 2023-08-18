@file:Suppress("MaxLineLength")
package tv.superawesome.sdk.publisher.common.openmeasurement

import android.webkit.WebView
import com.iab.omid.library.superawesome.adsession.AdSessionContextType
import com.iab.omid.library.superawesome.adsession.Partner
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.Test
import tv.superawesome.sdk.publisher.common.components.Logger
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class OpenMeasurementContextFactoryTests {

    private val logger = spyk<Logger>()
    private val adUrl = "https://wwww.some.ad.com"
    private val webView = mockk<WebView>(relaxed = true) {
        every { url } returns adUrl
    }
    private val adType = OpenMeasurementAdType.HTMLDisplay
    private val partner = mockk<Partner>(relaxed = true)
    private val contextBuilder = OpenMeasurementContextFactory(
        logger = logger,
    )

    @Test
    fun `sessionContext successfully returns a context when ad type is HTMLDisplay`() {
        // given
        val customReferenceData: String? = null

        // when
        val result = contextBuilder.sessionContext(
            adView = webView,
            adType = adType,
            partner = partner,
            customReferenceData = customReferenceData,
        )

        // then
        assertNotNull(result)
        assertEquals(partner, result.partner)
        assertEquals(webView, result.webView)
        assertEquals(AdSessionContextType.HTML, result.adSessionContextType)
        assertEquals(adUrl, result.contentUrl)
        assertNull(result.customReferenceData)
    }

    @Test
    fun `sessionContext successfully returns a context when ad type is HTMLDisplay and customReferenceData is present`() {
        // given
        val customReferenceData = "some stuff"

        // when
        val result = contextBuilder.sessionContext(
            adView = webView,
            adType = adType,
            partner = partner,
            customReferenceData = customReferenceData,
        )

        // then
        assertNotNull(result)
        assertEquals(partner, result.partner)
        assertEquals(webView, result.webView)
        assertEquals(AdSessionContextType.HTML, result.adSessionContextType)
        assertEquals(adUrl, result.contentUrl)
        assertEquals(customReferenceData, result.customReferenceData)
    }

    @Test
    fun `sessionContext returns null when ad type is Video`() {
        // given
        val videoAdType = OpenMeasurementAdType.Video
        val customReferenceData: String? = null

        // when
        val result = contextBuilder.sessionContext(
            adView = webView,
            adType = videoAdType,
            partner = partner,
            customReferenceData = customReferenceData,
        )

        // then
        assertNull(result)
    }

    @Test
    fun `sessionContext logs an error and returns null when reference data is too long`() {
        // given
        val customReferenceData = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Vivamus urna magna, ornare ac vestibulum et, commodo in tortor. Donec nec " +
                "ultricies lectus. Proin non placerat augue. Nunc vitae congue ligula. Curabitur " +
                "congue fermentum est, feugiat luctus turpis molestie eget. Duis fermentum enim " +
                "porttitor tempus finibus. Praesent a lorem finibus, semper felis feugiat, " +
                "dignissim diam. Nullam aliquam eros orci, sit amet hendrerit nunc ultrices id. " +
                "Sed ut tempor dolor. Proin porttitor tempor ligula, in mattis ante ornare nec."

        // when
        val result = contextBuilder.sessionContext(
            adView = webView,
            adType = adType,
            partner = partner,
            customReferenceData = customReferenceData,
        )

        // then
        verify {
            logger.error(
                "Unable to create session context",
                ofType(IllegalArgumentException::class),
            )
        }
        confirmVerified(logger)
        assertNull(result)
    }
}
