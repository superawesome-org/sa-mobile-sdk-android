package tv.superawesome.sdk.publisher.common.repositories

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import tv.superawesome.sdk.publisher.common.components.AdProcessorType
import tv.superawesome.sdk.publisher.common.components.AdQueryMakerType
import tv.superawesome.sdk.publisher.common.datasources.AwesomeAdsApiDataSourceType
import tv.superawesome.sdk.publisher.common.models.Ad
import tv.superawesome.sdk.publisher.common.models.AdResponse
import tv.superawesome.sdk.publisher.common.network.DataResult
import kotlin.test.assertEquals

class AdRepositoryTest {
    @MockK
    private lateinit var adDataSourceType: AwesomeAdsApiDataSourceType

    @MockK
    private lateinit var adQueryMakerType: AdQueryMakerType

    @MockK
    private lateinit var adProcessor: AdProcessorType

    private lateinit var adRepository: AdRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        adRepository =
            AdRepository(adDataSourceType, adQueryMakerType, adProcessor)
    }

    @Test
    fun test_getAdCalled_validResponse_success() = runBlocking {
        // Given
        val ad = mockk<Ad>()
        val dataResult = DataResult.Success(ad)
        val response = DataResult.Success(mockk<AdResponse>())

        coEvery { adDataSourceType.getAd(any(), any()) } returns dataResult
        coEvery { adQueryMakerType.makeAdQuery(any()) } returns mockk()
        coEvery { adProcessor.process(any(), any()) } returns response

        // When
        val result = adRepository.getAd(1, mockk())

        // Then
        assertEquals(true, result.isSuccess)
    }

    @Test
    fun test_getAdCalled_invalidResponse_failure() = runBlocking {
        // Given
        coEvery { adDataSourceType.getAd(any(), any()) } returns DataResult.Failure(mockk())
        coEvery { adQueryMakerType.makeAdQuery(any()) } returns mockk()

        // When
        val result = adRepository.getAd(1, mockk())

        // Then
        assertEquals(true, result.isFailure)
    }
}
