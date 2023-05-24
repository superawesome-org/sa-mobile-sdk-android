package tv.superawesome.sdk.publisher.common.openmeasurement

import android.content.Context
import com.iab.omid.library.superawesome.ScriptInjector
import tv.superawesome.sdk.publisher.common.components.Logger
import tv.superawesome.sdk.publisher.common.openmeasurement.error.OmidJSNotLoadedThrowable

internal class OpenMeasurementJSInjector(
    private val jsLoader: OpenMeasurementJSLoaderType,
    private val logger: Logger,
): OpenMeasurementJSInjectorType {

    override fun injectJS(context: Context, adHtml: String): String {
        val omidJs = jsLoader.loadJSLibrary(context)
        if (omidJs == null) {
            val error = OmidJSNotLoadedThrowable()
            logger.error(error.message, error)
            return adHtml
        }

        return try {
            logger.success("The Open Measurement JS was injected")
            ScriptInjector.injectScriptContentIntoHtml(omidJs, adHtml)
        } catch (error: Throwable) {
            logger.error("Unable to inject the Open Measurement JS", error)
            adHtml
        }
    }
}
