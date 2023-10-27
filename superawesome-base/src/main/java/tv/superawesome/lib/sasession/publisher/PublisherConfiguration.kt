@file:Suppress("UndocumentedPublicClass", "UndocumentedPublicFunction", "UndocumentedPublicProperty", "MaxLineLength")
package tv.superawesome.lib.sasession.publisher

import android.net.Uri

open class PublisherConfiguration(
    val closeButtonState: Int?,
    val orientation: Int?,
    val parentalGateOn: Boolean,
    val bumperPageOn: Boolean,
) {

    open fun toJsonString() =
        Uri.encode(
        """{"closeButton":$closeButtonState, "orientation":"$orientation", "parentalGateOn":$parentalGateOn, "bumperPageOn":$bumperPageOn}"""
        ).toString()
}
