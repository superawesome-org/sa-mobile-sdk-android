package tv.superawesome.sdk.publisher.models

enum class ConnectionType {
    Unknown, Ethernet, Wifi, CellularUnknown, Cellular2g, Cellular3g, Cellular4g, Cellular5g;

    fun findQuality(): ConnectionQuality {
        return when (this) {
            CellularUnknown, Cellular2g -> ConnectionQuality.Minimum
            Ethernet, Cellular4g, Cellular5g, Wifi, Unknown -> ConnectionQuality.Maximum
            else -> ConnectionQuality.Medium
        }
    }
}

enum class ConnectionQuality {
    Minimum, Medium, Maximum
}
