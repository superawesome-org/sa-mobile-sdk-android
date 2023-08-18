package tv.superawesome.sdk.publisher.common.openmeasurement

import android.content.Context
import com.iab.omid.library.superawesome.ScriptInjector
import tv.superawesome.sdk.publisher.common.components.Logger

internal class OpenMeasurementJSInjector(
    private val jsLoader: OpenMeasurementJSLoaderType,
    private val logger: Logger,
): OpenMeasurementJSInjectorType {

    override fun injectJS(context: Context, adHtml: String): String {
        val omidJs = jsLoader.loadJSLibrary(context)
        if (omidJs == null) {
            val error = OpenMeasurementError.OmidJSNotLoaded()
            logger.error(error.message, error)
            return adHtml
        }

        return try {
            logger.success("The Open Measurement JS was injected")
            ScriptInjector.injectScriptContentIntoHtml(omidJs, adHtml)
        } catch (error: IllegalArgumentException) {
            logger.error("Unable to inject the Open Measurement JS error: ${error.message}", error)
            adHtml
        }
    }
}
