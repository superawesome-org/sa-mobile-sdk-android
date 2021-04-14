package tv.superawesome.sdk.publisher.common.components

import android.content.Context
import android.webkit.WebSettings

interface UserAgentProviderType {
    val name: String
}

class UserAgentProvider(context: Context) : UserAgentProviderType {
    override val name: String by lazy {
        WebSettings.getDefaultUserAgent(context)
    }
}
