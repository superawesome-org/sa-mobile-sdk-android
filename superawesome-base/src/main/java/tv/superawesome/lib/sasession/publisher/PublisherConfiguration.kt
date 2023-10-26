@file:Suppress("UndocumentedPublicClass", "UndocumentedPublicFunction", "UndocumentedPublicProperty", "MaxLineLength")
package tv.superawesome.lib.sasession.publisher

import android.net.Uri
import tv.superawesome.sdk.publisher.SAOrientation

open class PublisherConfiguration(
    val closeButtonState: Int?,
    val orientation: SAOrientation?,
    val parentalGateOn: Boolean,
    val bumperPageOn: Boolean,
) {

    open fun toJsonString() =
        Uri.encode(
        """{"closeButton":$closeButtonState, "orientation":"${orientation?.name?.lowercase()}", "parentalGateOn":$parentalGateOn, "bumperPageOn":$bumperPageOn}"""
        ).toString()
}
