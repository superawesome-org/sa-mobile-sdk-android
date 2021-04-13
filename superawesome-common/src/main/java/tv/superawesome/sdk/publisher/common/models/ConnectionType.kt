package tv.superawesome.sdk.publisher.common.models

enum class ConnectionType(val value: Int) {
    unknown(0),
    ethernet(1),
    wifi(2),
    cellularUnknown(3),
    cellular2g(4),
    cellular3g(5),
    cellular4g(6);

    fun findQuality(): ConnectionQuality {
        return when (this) {
            cellularUnknown, cellular2g -> ConnectionQuality.Minimum
            ethernet, cellular4g, wifi, unknown -> ConnectionQuality.Maximum
            else -> ConnectionQuality.Medium
        }
    }
}

enum class ConnectionQuality {
    Minimum, Medium, Maximum
}
