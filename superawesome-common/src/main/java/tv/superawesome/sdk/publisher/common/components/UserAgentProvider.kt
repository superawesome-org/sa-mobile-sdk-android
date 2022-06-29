package tv.superawesome.sdk.publisher.common.components

import android.content.Context
import android.os.Build
import android.webkit.WebSettings

interface UserAgentProviderType {
    val name: String
}

class UserAgentProvider(context: Context) : UserAgentProviderType {
    override val name: String by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            WebSettings.getDefaultUserAgent(context)
        } else {
            System.getProperty("http.agent")
        }
    }
}
