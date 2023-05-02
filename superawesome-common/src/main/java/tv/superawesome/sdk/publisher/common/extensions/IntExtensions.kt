package tv.superawesome.sdk.publisher.common.extensions

import android.content.res.Resources

internal val Int.toPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()