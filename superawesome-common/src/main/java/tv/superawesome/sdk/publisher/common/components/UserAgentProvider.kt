package tv.superawesome.sdk.publisher.common.components

import android.content.Context
import android.os.Build
import android.webkit.WebSettings
import android.webkit.WebView

interface UserAgentProviderType {
    val name: String
}

class UserAgentProvider(context: Context) : UserAgentProviderType {
    override val name: String by lazy {
        if (Build.VERSION.SDK_INT >= 17) {
            WebSettings.getDefaultUserAgent(context)
        } else {
            WebView(context).settings.userAgentString
        }
    }
}