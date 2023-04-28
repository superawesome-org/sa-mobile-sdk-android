package tv.superawesome.sdk.publisher.common.components

import android.content.Context
import android.net.ConnectivityManager
import android.telephony.TelephonyManager
import tv.superawesome.sdk.publisher.common.models.ConnectionType

internal interface ConnectionProviderType {
    fun findConnectionType(): ConnectionType
}

internal class ConnectionProvider(private val context: Context) : ConnectionProviderType {
    override fun findConnectionType(): ConnectionType {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
                ?: return ConnectionType.Unknown

        return findConnectionTypeLegacy(connectivityManager)
    }

    private fun findCellularType(type: Int): ConnectionType = when (type) {
        TelephonyManager.NETWORK_TYPE_UNKNOWN -> ConnectionType.Unknown
        TelephonyManager.NETWORK_TYPE_GSM,
        TelephonyManager.NETWORK_TYPE_CDMA,
        TelephonyManager.NETWORK_TYPE_1xRTT,
        TelephonyManager.NETWORK_TYPE_IDEN,
        TelephonyManager.NETWORK_TYPE_GPRS,
        TelephonyManager.NETWORK_TYPE_EDGE -> ConnectionType.Cellular2g
        TelephonyManager.NETWORK_TYPE_UMTS,
        TelephonyManager.NETWORK_TYPE_EVDO_0,
        TelephonyManager.NETWORK_TYPE_EVDO_A,
        TelephonyManager.NETWORK_TYPE_EVDO_B,
        TelephonyManager.NETWORK_TYPE_HSPA,
        TelephonyManager.NETWORK_TYPE_HSDPA,
        TelephonyManager.NETWORK_TYPE_HSUPA,
        TelephonyManager.NETWORK_TYPE_EHRPD,
        TelephonyManager.NETWORK_TYPE_HSPAP,
        TelephonyManager.NETWORK_TYPE_TD_SCDMA -> ConnectionType.Cellular3g
        TelephonyManager.NETWORK_TYPE_LTE,
        TelephonyManager.NETWORK_TYPE_IWLAN -> ConnectionType.Cellular4g
        else -> ConnectionType.Unknown
    }

    @Suppress("DEPRECATION")
    private fun findConnectionTypeLegacy(connectivityManager: ConnectivityManager): ConnectionType {
        val info = connectivityManager.activeNetworkInfo

        if (info == null || !info.isConnected) return ConnectionType.Unknown
        if (info.type == ConnectivityManager.TYPE_WIFI) return ConnectionType.Wifi
        if (info.type == ConnectivityManager.TYPE_ETHERNET) return ConnectionType.Ethernet
        if (info.type == ConnectivityManager.TYPE_MOBILE) return findCellularType(info.subtype)

        return ConnectionType.Unknown
    }
}
