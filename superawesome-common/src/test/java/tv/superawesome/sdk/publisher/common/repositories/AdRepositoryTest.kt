package tv.superawesome.sdk.publisher.common.repositories

import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import tv.superawesome.sdk.publisher.common.base.BaseTest
import tv.superawesome.sdk.publisher.common.components.AdProcessorType
import tv.superawesome.sdk.publisher.common.components.AdQueryMakerType
import tv.superawesome.sdk.publisher.common.datasources.AwesomeAdsApiDataSourceType
import tv.superawesome.sdk.publisher.common.models.Ad
import tv.superawesome.sdk.publisher.common.models.AdResponse
import tv.superawesome.sdk.publisher.common.network.DataResult
import kotlin.test.assertEquals

class AdRepositoryTest : BaseTest() {
    @MockK
    lateinit var adDataSourceType: AwesomeAdsApiDataSourceType

    @MockK
    lateinit var adQueryMakerType: AdQueryMakerType

    @MockK
    lateinit var adProcessorType: AdProcessorType

    @InjectMockKs
    lateinit var adRepository: AdRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_getAdCalled_validResponse_success() = runTest {
        // Given
        val ad = mockk<Ad>(relaxed = true)
        val adResponse = AdResponse(1, ad)
        coEvery { adQueryMakerType.makeAdQuery(any()) } returns mockk()
        coEvery { adProcessorType.process(any(), any(), any()) } returns DataResult.Success(adResponse)
        coEvery { adDataSourceType.getAd(any(), any()) } returns DataResult.Success(ad)

        // When
        val result = adRepository.getAd(1, mockk())

        // Then
        assertEquals(true, result.isSuccess)
    }

    @Test
    fun test_getAdCalled_invalidResponse_failure() = runTest {
        // Given
        coEvery { adDataSourceType.getAd(any(), any()) } returns DataResult.Failure(mockk())
        coEvery { adQueryMakerType.makeAdQuery(any()) } returns mockk()

        // When
        val result = adRepository.getAd(1, mockk())

        // Then
        assertEquals(true, result.isFailure)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_getAdMultiIdCalled_validResponse_success() = runTest {
        // Given
        val ad = mockk<Ad>(relaxed = true)
        val adResponse = AdResponse(1, ad)
        coEvery { adQueryMakerType.makeAdQuery(any()) } returns mockk()
        coEvery { adProcessorType.process(any(), any(), any()) } returns DataResult.Success(adResponse)
        coEvery { adDataSourceType.getAd(any(), any(), any(), any()) } returns DataResult.Success(ad)

        // When
        val result = adRepository.getAd(1, 2, 3, mockk())

        // Then
        assertEquals(true, result.isSuccess)
    }

    @Test
    fun test_getAdMultiIdCalled_invalidResponse_failure() = runTest {
        // Given
        coEvery { adDataSourceType.getAd(any(), any(), any(), any()) } returns DataResult.Failure(mockk())
        coEvery { adQueryMakerType.makeAdQuery(any()) } returns mockk()

        // When
        val result = adRepository.getAd(1, 2, 3, mockk())

        // Then
        assertEquals(true, result.isFailure)
    }
}