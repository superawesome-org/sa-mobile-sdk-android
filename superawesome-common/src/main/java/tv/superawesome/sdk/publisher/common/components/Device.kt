package tv.superawesome.sdk.publisher.common.components

import android.content.res.Resources
import kotlin.math.pow
import kotlin.math.sqrt

interface DeviceType {
    var genericType: DeviceCategory
}

enum class DeviceCategory {
    phone, tablet;
}

class Device : DeviceType {
    override var genericType: DeviceCategory = getSystemSize()

    private fun getSystemSize(): DeviceCategory {
        val dm = Resources.getSystem().displayMetrics
        val width = dm.widthPixels
        val height = dm.heightPixels
        val dens = dm.densityDpi
        val wi = width.toDouble() / dens.toDouble()
        val hi = height.toDouble() / dens.toDouble()
        val x = wi.pow(2.0)
        val y = hi.pow(2.0)
        val screenInches = sqrt(x + y)
        return if (screenInches < 6) DeviceCategory.phone else DeviceCategory.tablet
    }
}
