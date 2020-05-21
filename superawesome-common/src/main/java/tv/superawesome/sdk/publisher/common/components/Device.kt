package tv.superawesome.sdk.publisher.common.components

import android.util.DisplayMetrics
import kotlin.math.pow
import kotlin.math.sqrt

interface DeviceType {
    var genericType: DeviceCategory
}

enum class DeviceCategory {
    phone, tablet;
}

class Device(private val displayMetrics: DisplayMetrics) : DeviceType {

    override var genericType: DeviceCategory = deviceCategory

    private val systemSize: Double
        get() {
            val width = displayMetrics.widthPixels
            val height = displayMetrics.heightPixels
            val density = displayMetrics.densityDpi
            val widthDp = width.toDouble() / density.toDouble()
            val heightDp = height.toDouble() / density.toDouble()
            return sqrt(widthDp.pow(2.0) + heightDp.pow(2.0))
        }

    private val deviceCategory: DeviceCategory
        get() = if (systemSize <= PHONE_SCREEN_MAX) DeviceCategory.phone else DeviceCategory.tablet

    companion object {
        private const val PHONE_SCREEN_MAX = 6.2
    }
}
