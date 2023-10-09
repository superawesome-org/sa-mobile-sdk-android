package tv.superawesome.sdk.publisher.models

import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class DefaultAdRequestTest {

    @Test
    fun `DefaultAdRequest propertyString contains expected values `() = runTest {
        // Given
        val sut = DefaultAdRequest(
            false,
            10,
            20,
            30,
            40,
            50,
            60,
            70
        )

        // When
        val map = sut.propertyString

        // Then
        Assert.assertEquals(map["pos"], sut.pos)
        Assert.assertEquals(map["skip"], sut.skip)
        Assert.assertEquals(map["playbackmethod"], sut.playbackMethod)
        Assert.assertEquals(map["startdelay"], sut.startDelay)
        Assert.assertEquals(map["instl"], sut.install)
        Assert.assertEquals(map["w"], sut.w)
        Assert.assertEquals(map["h"], sut.h)
    }
}