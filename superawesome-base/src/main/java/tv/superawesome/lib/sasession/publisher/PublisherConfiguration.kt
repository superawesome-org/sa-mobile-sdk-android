@file:Suppress("UndocumentedPublicClass", "UndocumentedPublicFunction", "UndocumentedPublicProperty", "MaxLineLength", "LongParameterList")
package tv.superawesome.lib.sasession.publisher

import android.net.Uri
import org.json.JSONObject

open class PublisherConfiguration(
    val parentalGateOn: Boolean,
    val bumperPageOn: Boolean,
    val closeWarning: Boolean?,
    val orientation: Int?,
    val closeAtEnd: Boolean?,
    val muteOnStart: Boolean?,
    val showMore: Boolean?,
    val startDelay: Int?,
    val closeButtonState: Int?,
) {

    open fun toJsonString(): String =
        Uri.encode(
            JSONObject().apply {
                put("parentalGateOn", parentalGateOn)
                put("bumperPageOn", bumperPageOn)
                closeWarning?.let { put("closeWarning", closeWarning) }
                orientation?.let { put("orientation", orientation) }
                closeAtEnd?.let { put("closeAtEnd", closeAtEnd) }
                muteOnStart?.let { put("muteOnStart", muteOnStart) }
                showMore?.let { put("showMore", showMore) }
                startDelay?.let { put( "startDelay", startDelay) }
                closeButtonState?.let { put("closeButtonState", closeButtonState) }
            }.toString()
        )
}
