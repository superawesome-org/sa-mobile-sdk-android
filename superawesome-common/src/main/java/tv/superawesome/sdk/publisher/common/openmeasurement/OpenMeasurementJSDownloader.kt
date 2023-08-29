package tv.superawesome.sdk.publisher.common.openmeasurement

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tv.superawesome.sdk.publisher.common.components.Logger
import tv.superawesome.sdk.publisher.common.network.DataResult
import tv.superawesome.sdk.publisher.common.network.datasources.NetworkDataSourceType
import java.io.File

/**
 * Downloader for the JS from AASDK S3 and saves it in on device for use
 * to ensure the latest OMSDK js is used as required by the IAB.
 */
internal class OpenMeasurementJSDownloader(
    private val networking: NetworkDataSourceType,
    private val logger: Logger,
    private val jsFileUrl: String,
    private val destDirectory: File,
) : OpenMeasurementJSDownloaderType {

    private val scope = CoroutineScope(Dispatchers.IO)

    /**
     * Downloads the OMSDK JS file.
     */
    override fun downloadJS() {
        scope.launch {
            when(val result = networking.downloadFile(jsFileUrl, destDirectory = destDirectory)) {
                is DataResult.Failure -> logger.error(
                    "Exception downloading OMSDK JS error: ${result.error.message}",
                    result.error,
                )
                is DataResult.Success -> logger.success("Successfully downloaded OMSDK JS")
            }
        }
    }

}
