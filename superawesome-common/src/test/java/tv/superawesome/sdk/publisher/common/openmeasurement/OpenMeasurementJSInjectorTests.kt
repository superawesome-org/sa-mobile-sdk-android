package tv.superawesome.sdk.publisher.common.openmeasurement

import android.text.TextUtils
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.spyk
import io.mockk.verify
import org.junit.Test
import tv.superawesome.sdk.publisher.common.components.Logger
import tv.superawesome.sdk.publisher.common.openmeasurement.error.OmidJSNotLoadedThrowable
import kotlin.test.assertEquals

class OpenMeasurementJSInjectorTests {

    private val logger = spyk<Logger>()

    @Test
    fun `injectJS successfully injects the javascript`() {
        // given
        val js = "some js"
        val adHtml = "<html><head></head><body><a href=\"https://www.superawesome.com\">" +
                "<img src=\"https://wwww.image.com\"></a></body></html>"
        val adHtmlWithJs = "<html><head><script type=\"text/javascript\">$js</script>" +
                "</head><body><a href=\"https://www.superawesome.com\">" +
                "<img src=\"https://wwww.image.com\"></a></body></html>"

        val loader = mockk<OpenMeasurementJSLoaderType>(relaxed = true)
        every { loader.loadJSLibrary() } returns js
        mockkStatic(TextUtils::class)
        every { TextUtils.isEmpty(any()) } answers { arg<String?>(0).isNullOrEmpty() }
        val injector = OpenMeasurementJSInjector(jsLoader = loader, logger = logger)

        // when
        val result = injector.injectJS(adHtml = adHtml)

        // then
        verify { logger.success("The Open Measurement JS was injected") }
        confirmVerified(logger)
        assertEquals(adHtmlWithJs, result)
    }

    @Test
    fun `injectJS logs an error and returns unmodified html if js from loader is empty`() {
        // given
        val adHtml = "<html><head></head><body><a href=\"https://www.superawesome.com\">" +
                "<img src=\"https://wwww.image.com\"></a></body></html>"

        val loader = mockk<OpenMeasurementJSLoaderType>(relaxed = true)
        every { loader.loadJSLibrary() } returns ""
        val injector = OpenMeasurementJSInjector(jsLoader = loader, logger = logger)

        // when
        val result = injector.injectJS(adHtml = adHtml)

        // then
        verify {
            logger.error(
                "The omidJS was not loaded",
                ofType(OmidJSNotLoadedThrowable::class),
            )
        }
        confirmVerified(logger)
        assertEquals(adHtml, result)
    }

    @Test
    fun `injectJS logs an error and returns unmodified html if injector fails`() {
        // given
        val js = "some js"
        val adHtml = "<html><head></head><body><a href=\"https://www.superawesome.com\">" +
                "<img src=\"https://wwww.image.com\"></a></body></html>"

        val loader = mockk<OpenMeasurementJSLoaderType>(relaxed = true)
        every { loader.loadJSLibrary() } returns js
        val injector = OpenMeasurementJSInjector(jsLoader = loader, logger = logger)

        // when
        val result = injector.injectJS(adHtml = adHtml)

        // then
        verify {
            logger.error(
                "Unable to inject the Open Measurement JS",
                any(),
            )
        }
        confirmVerified(logger)
        assertEquals(adHtml, result)
    }
}
