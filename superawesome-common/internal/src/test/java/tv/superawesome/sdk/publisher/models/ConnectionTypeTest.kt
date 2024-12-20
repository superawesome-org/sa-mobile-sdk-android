package tv.superawesome.sdk.publisher.models

import org.junit.Assert
import org.junit.Test

class ConnectionTypeTest {

    @Test
    fun `ConnectionType quality for CellularUnknown is minimum`() {
        // Given
        val sut = ConnectionType.CellularUnknown

        // Then
        Assert.assertEquals(ConnectionQuality.Minimum, sut.findQuality())
    }

    @Test
    fun `ConnectionType quality for Cellular2g is minimum`() {
        // Given
        val sut = ConnectionType.Cellular2g

        // Then
        Assert.assertEquals(ConnectionQuality.Minimum, sut.findQuality())
    }

    @Test
    fun `ConnectionType quality for Ethernet is maximum`() {
        // Given
        val sut = ConnectionType.Ethernet

        // Then
        Assert.assertEquals(ConnectionQuality.Maximum, sut.findQuality())
    }

    @Test
    fun `ConnectionType quality for Cellular4g is maximum`() {
        // Given
        val sut = ConnectionType.Cellular4g

        // Then
        Assert.assertEquals(ConnectionQuality.Maximum, sut.findQuality())
    }

    @Test
    fun `ConnectionType quality for Cellular5g is maximum`() {
        // Given
        val sut = ConnectionType.Cellular5g

        // Then
        Assert.assertEquals(ConnectionQuality.Maximum, sut.findQuality())
    }

    @Test
    fun `ConnectionType quality for Wifi is maximum`() {
        // Given
        val sut = ConnectionType.Wifi

        // Then
        Assert.assertEquals(ConnectionQuality.Maximum, sut.findQuality())
    }

    @Test
    fun `ConnectionType quality for Unknown is maximum`() {
        // Given
        val sut = ConnectionType.Unknown

        // Then
        Assert.assertEquals(ConnectionQuality.Maximum, sut.findQuality())
    }
}