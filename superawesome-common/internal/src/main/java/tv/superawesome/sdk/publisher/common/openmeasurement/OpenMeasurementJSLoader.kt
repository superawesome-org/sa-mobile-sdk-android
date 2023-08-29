package tv.superawesome.sdk.publisher.common.openmeasurement

import tv.superawesome.sdk.publisher.common.components.Logger
import tv.superawesome.sdk.publisher.common.extensions.readAllBytesLegacy
import tv.superawesome.sdk.publisher.common.utilities.FileWrapper
import java.io.IOException
import java.io.InputStream

/**
 * Utility for loading the OMID JavaScript resource.
 */
class OpenMeasurementJSLoader(
    private val logger: Logger,
    private val jsFile: FileWrapper,
    private val jsInputStream: InputStream,
) : OpenMeasurementJSLoaderType {
    /**
     * Gets the Omid JS resource as a string.
     * @return The Omid JS resource as a string.
     */
    override fun loadJSLibrary(): String? {
        val errorMessage = "Unable to load OMSDK JS from local storage error: "
        return try {
            if (jsFile.exists()) {
                String(jsFile.readBytes())
            } else {
                jsInputStream.use { inputStream ->
                    return String(inputStream.readAllBytesLegacy())
                }
            }
        } catch (error: OutOfMemoryError) {
            logger.error(errorMessage + error.message, error)
            null
        } catch (error: IOException) {
            logger.error(errorMessage + error.message, error)
            null
        } catch (error: StringIndexOutOfBoundsException) {
            logger.error(errorMessage + error.message, error)
            null
        }
    }
}
