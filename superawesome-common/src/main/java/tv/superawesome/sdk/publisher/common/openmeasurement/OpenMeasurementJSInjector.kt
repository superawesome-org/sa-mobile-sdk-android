package tv.superawesome.sdk.publisher.common.openmeasurement

import com.iab.omid.library.superawesome.ScriptInjector
import tv.superawesome.sdk.publisher.common.components.Logger
import tv.superawesome.sdk.publisher.common.openmeasurement.error.OmidJSNotLoadedThrowable

/**
 * OpenMeasurementJSInjector - Utility for injecting the OMID JavaScript string into a HTML string
 */
internal class OpenMeasurementJSInjector(
    private val jsLoader: OpenMeasurementJSLoaderType,
    private val logger: Logger,
): OpenMeasurementJSInjectorType {
    /**
     * injectJS - injects the JS string into the provided HTML string
     * @param adHtml - the HTML string for the ad
     * @return String - the HTML string for the ad with the OMID JS injected into it
     */
    override fun injectJS(adHtml: String): String {
        val omidJs = jsLoader.loadJSLibrary()
        if (omidJs.isNullOrEmpty()) {
            val error = OmidJSNotLoadedThrowable()
            logger.error(error.message, error)
            return adHtml
        }

        return try {
            val result = ScriptInjector.injectScriptContentIntoHtml(omidJs, adHtml)
            logger.success("The Open Measurement JS was injected")
            result
        } catch (error: Throwable) {
            logger.error("Unable to inject the Open Measurement JS", error)
            adHtml
        }
    }
}
