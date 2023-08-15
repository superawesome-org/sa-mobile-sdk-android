package tv.superawesome.sdk.publisher.common.openmeasurement

import tv.superawesome.sdk.publisher.common.components.Logger
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

/**
 * Utility for loading the OMID JavaScript resource.
 */
internal class OpenMeasurementJSLoader(
    private val logger: Logger,
    private val jsInputStream: InputStream,
) : OpenMeasurementJSLoaderType {
    /**
     * Gets the Omid JS resource as a string.
     * @return The Omid JS resource as a string.
     */
    override fun loadJSLibrary(): String? =
        try {
            jsInputStream.use { inputStream ->
                val bytes = ByteArray(inputStream.available())
                val bytesRead = inputStream.read(bytes)
                return String(bytes, 0, bytesRead, Charset.forName("UTF-8"))
            }
        } catch (error: IOException) {
            logger.error("Unable to load OMSDK JS from local storage", error)
            null
        } catch (error: StringIndexOutOfBoundsException) {
            logger.error("Unable to load OMSDK JS from local storage", error)
            null
        }
}
