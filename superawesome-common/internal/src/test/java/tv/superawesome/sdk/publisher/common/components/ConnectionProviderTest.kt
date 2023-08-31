package tv.superawesome.sdk.publisher.common.components

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.telephony.TelephonyManager
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.Test
import tv.superawesome.sdk.publisher.common.base.BaseTest
import tv.superawesome.sdk.publisher.common.models.ConnectionType
import kotlin.test.assertEquals

internal class ConnectionProviderTest : BaseTest() {
    @MockK
    lateinit var context: Context

    @MockK
    lateinit var network: Network

    @MockK
    lateinit var connectivityManager: ConnectivityManager

    @MockK
    lateinit var networkCapabilities: NetworkCapabilities

    @InjectMockKs
    lateinit var connectionProvider: ConnectionProvider

    @MockK
    lateinit var telephonyManager: TelephonyManager

    @MockK
    lateinit var networkInfo: NetworkInfo

    @Test
    fun test_connectionType_noSystemServiceAvailable_unknown() {
        // When
        val result = connectionProvider.findConnectionType()

        // Then
        assertEquals(ConnectionType.Unknown, result)
    }

    @Test
    fun test_connectionType_HasWifiCapability_wifi() {
        whenNetworkType(ConnectivityManager.TYPE_WIFI, ConnectionType.Wifi)
    }

    @Test
    fun test_connectionType_HasEthernetCapability_ethernet() {
        whenNetworkType(ConnectivityManager.TYPE_ETHERNET, ConnectionType.Ethernet)
    }

    @Test
    fun test_connectionType_HasCellularCapability_cellular() {
        whenTelephonyCapability(TelephonyManager.NETWORK_TYPE_UNKNOWN, ConnectionType.Unknown)
        whenTelephonyCapability(TelephonyManager.NETWORK_TYPE_GSM, ConnectionType.Cellular2g)
        whenTelephonyCapability(TelephonyManager.NETWORK_TYPE_HSPA, ConnectionType.Cellular3g)
        whenTelephonyCapability(TelephonyManager.NETWORK_TYPE_LTE, ConnectionType.Cellular4g)
    }

    private fun whenNetworkType(networkCapability: Int, expected: ConnectionType) {
        // Given
        every { context.getSystemService(Context.CONNECTIVITY_SERVICE) } returns connectivityManager
        every { connectivityManager.getNetworkCapabilities(any()) } returns networkCapabilities
        every { connectivityManager.activeNetworkInfo } returns networkInfo
        every { networkInfo.isConnected } returns true
        every { networkInfo.type } returns networkCapability
        every { networkInfo.subtype } returns networkCapability
        every { networkCapabilities.hasTransport(networkCapability) } returns true

        // When
        val result = connectionProvider.findConnectionType()

        // Then
        assertEquals(expected, result)
    }

    private fun whenTelephonyCapability(networkType: Int, expected: ConnectionType) {
        // Given
        every { context.getSystemService(Context.CONNECTIVITY_SERVICE) } returns connectivityManager
        every { context.getSystemService(Context.TELEPHONY_SERVICE) } returns telephonyManager
        every { connectivityManager.getNetworkCapabilities(any()) } returns networkCapabilities
        every { networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) } returns true
        every { telephonyManager.networkType } returns networkType
        every { connectivityManager.activeNetworkInfo } returns networkInfo
        every { networkInfo.isConnected } returns true
        every { networkInfo.type } returns ConnectivityManager.TYPE_MOBILE
        every { networkInfo.subtype } returns networkType

        // When
        val result = connectionProvider.findConnectionType()

        // Then
        assertEquals(expected, result)
    }
}