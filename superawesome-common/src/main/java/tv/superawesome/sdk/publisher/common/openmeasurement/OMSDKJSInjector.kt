package tv.superawesome.sdk.publisher.common.openmeasurement

import android.content.Context
import com.iab.omid.library.superawesome.ScriptInjector

interface OMSDKJSInjectorType {
    fun injectOmsdkJavascript(context: Context, adHtml: String): String?
}

class OMSDKJSInjector: OMSDKJSInjectorType {

    override fun injectOmsdkJavascript(context: Context, adHtml: String): String? {
        val omidJavascript = OmidJsLoader.getOmidJs(context)
        return ScriptInjector.injectScriptContentIntoHtml(omidJavascript, adHtml)
    }
}
