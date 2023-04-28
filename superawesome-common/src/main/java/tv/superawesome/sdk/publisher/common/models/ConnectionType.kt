package tv.superawesome.sdk.publisher.common.models

internal enum class ConnectionType {
    Unknown, Ethernet, Wifi, CellularUnknown, Cellular2g, Cellular3g, Cellular4g;

    fun findQuality(): ConnectionQuality {
        return when (this) {
            CellularUnknown, Cellular2g -> ConnectionQuality.Minimum
            Ethernet, Cellular4g, Wifi, Unknown -> ConnectionQuality.Maximum
            else -> ConnectionQuality.Medium
        }
    }
}

internal enum class ConnectionQuality {
    Minimum, Medium, Maximum
}