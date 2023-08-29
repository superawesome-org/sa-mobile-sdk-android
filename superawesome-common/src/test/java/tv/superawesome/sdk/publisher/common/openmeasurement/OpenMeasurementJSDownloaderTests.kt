package tv.superawesome.sdk.publisher.common.openmeasurement

import io.mockk.coEvery
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Test
import tv.superawesome.sdk.publisher.common.components.Logger
import tv.superawesome.sdk.publisher.common.network.DataResult
import tv.superawesome.sdk.publisher.common.network.datasources.NetworkDataSourceType
import java.io.File
import java.io.IOException

class OpenMeasurementJSDownloaderTests {

    private val networking = mockk<NetworkDataSourceType>()
    private val logger = spyk<Logger>()
    private val jsFileUrl = "https://somefile.com/test.js"
    private val destDirectory = mockk<File>()
    private val downloader = OpenMeasurementJSDownloader(
        networking = networking,
        logger = logger,
        jsFileUrl = jsFileUrl,
        destDirectory = destDirectory,
    )

    @Test
    fun`downloadJS saves a file to disk if network request was successful`() = runTest {
        // given
        val result = DataResult.Success("success")
        coEvery { networking.downloadFile(jsFileUrl, destDirectory = destDirectory) } returns result

        // when
        downloader.downloadJS()

        // then
        verify { logger.success("Successfully downloaded OMSDK JS") }
        confirmVerified(logger)
    }

    @Test
    fun`downloadJS logs an error if the network request was unsuccessful`() = runTest {
        // given
        val exception = IOException("something")
        val result = DataResult.Failure(exception)
        coEvery { networking.downloadFile(jsFileUrl, destDirectory = destDirectory) } returns result

        // when
        downloader.downloadJS()

        // then
        verify { logger.error("Exception downloading OMSDK JS error: something", exception) }
        confirmVerified(logger)
    }
}
