package tv.superawesome.sdk.publisher.common.openmeasurement

import android.content.Context
import com.iab.omid.library.superawesome.ScriptInjector

interface OMSDKJSInjectorType {
    fun injectOmsdkJavascript(context: Context, adHtml: String): String?
}

class OMSDKJSInjector: OMSDKJSInjectorType {

    override fun injectOmsdkJavascript(context: Context, adHtml: String): String? {
        val omidJavascript = OmidJsLoader.getOmidJs(context)
        val updatedHTML = ScriptInjector.injectScriptContentIntoHtml(omidJavascript, adHtml)
        return replaceVerificationURL(updatedHTML)
    }

    private fun replaceVerificationURL(input: String?): String? =
        input?.replace(RESOURCE_PATTERN, VERIFICATION_URL)

    companion object {
        private const val RESOURCE_PATTERN = "[INSERT RESOURCE URL]"
        // NOTE: Replace this is a link pointing back to the AA server copy of the script.
        private const val VERIFICATION_URL =
            "https://omsdk-public.herokuapp.com/omid-validation-verification-script-v1.js"
    }
}
