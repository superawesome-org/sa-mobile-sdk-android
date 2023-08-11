package tv.superawesome.sdk.publisher.common.openmeasurement

import com.iab.omid.library.superawesome.ScriptInjector
import tv.superawesome.sdk.publisher.common.components.Logger

/**
 * Utility for injecting the OMID JavaScript string into a HTML string
 */
internal class OpenMeasurementJSInjector(
    private val jsLoader: OpenMeasurementJSLoaderType,
    private val logger: Logger,
): OpenMeasurementJSInjectorType {
    /**
     * Injects the JS string into the provided HTML string
     * @param adHtml The HTML string for the ad
     * @return The HTML string for the ad with the OMID JS injected into it
     */
    override fun injectJS(adHtml: String): String {
        val omidJs = jsLoader.loadJSLibrary()
        if (omidJs.isNullOrEmpty()) {
            val error = OpenMeasurementError.OmidJSNotLoaded()
            logger.error(error.message, error)
            return adHtml
        }

        return try {
            val result = ScriptInjector.injectScriptContentIntoHtml(omidJs, adHtml)
            logger.success("The Open Measurement JS was injected")
            result
        } catch (error: Exception) {
            logger.error("Unable to inject the Open Measurement JS", error)
            adHtml
        }
    }
}
