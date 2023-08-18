package tv.superawesome.sdk.publisher.common.openmeasurement

import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.spyk
import io.mockk.verify
import org.junit.Test
import tv.superawesome.sdk.publisher.common.components.Logger
import tv.superawesome.sdk.publisher.common.utilities.FileWrapper
import java.io.IOException
import java.io.InputStream
import kotlin.test.assertEquals
import kotlin.test.assertNull

class OpenMeasurementJSLoaderTests {

    private val logger = spyk<Logger>()

    private val jsFile = mockk<FileWrapper>(relaxed = true).apply {
        every { readBytes() } returns "test js".toByteArray()
    }

    @Test
    fun `loadJSLibrary successfully returns the bundled javascript when the local JS file does not exist`() {
        // given
        val jsData = "<script>test</script>"
        val stubInputStream: InputStream = jsData.byteInputStream()
        val loader = OpenMeasurementJSLoader(
            logger = logger,
            jsFile = jsFile,
            jsInputStream = stubInputStream,
        )
        every { jsFile.exists() } returns false

        // when
        val result = loader.loadJSLibrary()

        // then
        assertEquals(jsData, result)
    }

    @Test
    fun `loadJSLibrary successfully returns the local javascript when the local JS file exists`() {
        // given
        val jsData = "<script>test</script>"
        val stubInputStream: InputStream = jsData.byteInputStream()
        val loader = OpenMeasurementJSLoader(
            logger = logger,
            jsFile = jsFile,
            jsInputStream = stubInputStream,
        )
        every { jsFile.exists() } returns true

        // when
        val result = loader.loadJSLibrary()

        // then
        assertEquals(jsData, result)
    }

    @Test
    fun `loadJSLibrary returns null and logs an string out of bounds error if no file exists`() {
        // given
        val stubInputStream: InputStream = "".byteInputStream()
        val loader = OpenMeasurementJSLoader(
            logger = logger,
            jsFile = jsFile,
            jsInputStream = stubInputStream,
        )

        // when
        val result = loader.loadJSLibrary()

        // then
        assertNull(result)
        verify {
            logger.error(
                message = "Unable to load OMSDK JS from local storage",
                error = ofType(StringIndexOutOfBoundsException::class),
            )
        }
        confirmVerified(logger)
    }

    @Test
    fun `loadJSLibrary returns null and logs an IOException error if no file exists`() {
        // given
        val mockInputStream = mockk<InputStream>(relaxed = true)
        every { mockInputStream.available() } throws IOException()
        val loader = OpenMeasurementJSLoader(
            logger = logger,
            jsFile = jsFile,
            jsInputStream = mockInputStream,
        )

        // when
        val result = loader.loadJSLibrary()

        // then
        assertNull(result)
        verify {
            logger.error(
                message = "Unable to load OMSDK JS from local storage",
                error = ofType(IOException::class),
            )
        }
        confirmVerified(logger)
    }
}