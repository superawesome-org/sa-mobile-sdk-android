package tv.superawesome.sdk.publisher.common.openmeasurement

import tv.superawesome.sdk.publisher.common.components.Logger
import tv.superawesome.sdk.publisher.common.utilities.FileWrapper
import java.io.File
import java.io.IOException
import java.io.InputStream

/**
 * Utility for loading the OMID JavaScript resource.
 */
internal class OpenMeasurementJSLoader(
    private val logger: Logger,
    private val jsFile: FileWrapper,
    private val jsInputStream: InputStream,
) : OpenMeasurementJSLoaderType {
    /**
     * Gets the Omid JS resource as a string.
     * @return The Omid JS resource as a string.
     */
    override fun loadJSLibrary(): String? =
        try {
            if (jsFile.exists()) {
                String(jsFile.readBytes())
            } else {
                jsInputStream.use { inputStream ->
                    val bytes = ByteArray(inputStream.available())
                    return String(bytes)
                }
            }
        } catch (error: OutOfMemoryError) {
            logger.error(
                "Unable to load OMSDK JS from local storage error: ${error.message}",
                error,
            )
            null

        } catch (error: IOException) {
            logger.error(
                "Unable to load OMSDK JS from local storage error: ${error.message}",
                error,
            )
            null
        } catch (error: StringIndexOutOfBoundsException) {
            logger.error(
                "Unable to load OMSDK JS from local storage error: ${error.message}",
                error,
            )
            null
        }
}
