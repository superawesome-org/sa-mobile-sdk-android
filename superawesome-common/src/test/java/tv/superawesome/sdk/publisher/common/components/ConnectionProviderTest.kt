package tv.superawesome.sdk.publisher.common.components

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.telephony.TelephonyManager
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.Test
import tv.superawesome.sdk.publisher.common.base.BaseTest
import tv.superawesome.sdk.publisher.common.models.ConnectionType
import tv.superawesome.sdk.publisher.common.testutil.BuildUtil
import kotlin.test.assertEquals

class ConnectionProviderTest : BaseTest() {
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

    @Test
    fun test_connectionType_noSystemServiceAvailable_unknown() {
        // When
        val result = connectionProvider.findConnectionType()

        // Then
        assertEquals(ConnectionType.Unknown, result)
    }

    @Test
    fun test_connectionType_HasWifiCapability_wifi() {
        whenNetworkCapability(NetworkCapabilities.TRANSPORT_WIFI, ConnectionType.Wifi)
    }

    @Test
    fun test_connectionType_HasEthernetCapability_ethernet() {
        whenNetworkCapability(NetworkCapabilities.TRANSPORT_ETHERNET, ConnectionType.Ethernet)
    }

    @Test
    fun test_connectionType_HasCellularCapability_cellular() {
        whenTelephonyCapability(TelephonyManager.NETWORK_TYPE_UNKNOWN, ConnectionType.Unknown)
        whenTelephonyCapability(TelephonyManager.NETWORK_TYPE_GSM, ConnectionType.Cellular2g)
        whenTelephonyCapability(TelephonyManager.NETWORK_TYPE_HSPA, ConnectionType.Cellular3g)
        whenTelephonyCapability(TelephonyManager.NETWORK_TYPE_LTE, ConnectionType.Cellular4g)
    }

    private fun whenNetworkCapability(networkCapability: Int, expected: ConnectionType) {
        // Given
        every { context.getSystemService(Context.CONNECTIVITY_SERVICE) } returns connectivityManager
        every { connectivityManager.getNetworkCapabilities(any()) } returns networkCapabilities
        every { networkCapabilities.hasTransport(networkCapability) } returns true
        BuildUtil.mockSdkInt(30)

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
        BuildUtil.mockSdkInt(30)

        // When
        val result = connectionProvider.findConnectionType()

        // Then
        assertEquals(expected, result)
    }
}
