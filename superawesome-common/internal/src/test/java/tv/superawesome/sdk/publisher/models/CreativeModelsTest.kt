package tv.superawesome.sdk.publisher.models

import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class CreativeModelsTest {

    @Test
    fun `CreativeReferral toMap contains expected values`() = runTest {
        // Given
        val sut = CreativeReferral(
            utmSource = 1,
            utmCampaign = 2,
            utmTerm = 3,
            utmContent = 4,
            utmMedium = 5
        )

        // When
        val map = sut.toMap()

        // Then
        Assert.assertEquals(map["utm_source"], sut.utmSource)
        Assert.assertEquals(map["utm_campaign"], sut.utmCampaign)
        Assert.assertEquals(map["utm_term"], sut.utmTerm)
        Assert.assertEquals(map["utm_content"], sut.utmContent)
        Assert.assertEquals(map["utm_medium"], sut.utmMedium)
    }
}