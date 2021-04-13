package tv.superawesome.sdk.publisher.common.repositories

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import tv.superawesome.sdk.publisher.common.components.AdProcessorType
import tv.superawesome.sdk.publisher.common.components.AdQueryMakerType
import tv.superawesome.sdk.publisher.common.components.DispatcherProviderType
import tv.superawesome.sdk.publisher.common.datasources.AwesomeAdsApiDataSourceType
import tv.superawesome.sdk.publisher.common.models.Ad
import tv.superawesome.sdk.publisher.common.network.DataResult
import kotlin.test.assertEquals

class AdRepositoryTest {
    @MockK
    private lateinit var adDataSourceType: AwesomeAdsApiDataSourceType

    @MockK
    private lateinit var adQueryMakerType: AdQueryMakerType

    @MockK
    private lateinit var adProcessor: AdProcessorType

    @MockK
    private lateinit var dispatcherProvider: DispatcherProviderType

    private lateinit var adRepository: AdRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        every { dispatcherProvider.io } returns Dispatchers.Unconfined
        adRepository =
            AdRepository(adDataSourceType, adQueryMakerType, adProcessor, dispatcherProvider)
    }

    @Test
    fun test_getAdCalled_validResponse_success() = runBlocking {
        // Given
        val dataResult: DataResult.Success<Ad> = mockk()
        every { dataResult.isSuccess } returns true
        coEvery { dataResult.value } returns mockk()
        coEvery { adDataSourceType.getAd(any(), any()) } returns dataResult
        coEvery { adQueryMakerType.makeAdQuery(any()) } returns mockk()
        coEvery { adProcessor.process(any(), any()) } returns mockk()

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