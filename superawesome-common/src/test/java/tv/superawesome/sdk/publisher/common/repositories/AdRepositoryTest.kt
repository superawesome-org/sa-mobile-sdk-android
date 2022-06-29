//package tv.superawesome.sdk.publisher.common.repositories
//
//import io.mockk.coEvery
//import io.mockk.impl.annotations.InjectMockKs
//import io.mockk.impl.annotations.MockK
//import io.mockk.mockk
//import kotlinx.coroutines.runBlocking
//import org.junit.Test
//import tv.superawesome.sdk.publisher.common.base.BaseTest
//import tv.superawesome.sdk.publisher.common.components.AdQueryMakerType
//import tv.superawesome.sdk.publisher.common.datasources.AwesomeAdsApiDataSourceType
//import tv.superawesome.sdk.publisher.common.network.DataResult
//import kotlin.test.assertEquals
//
//class AdRepositoryTest : BaseTest() {
//    @MockK
//    lateinit var adDataSourceType: AwesomeAdsApiDataSourceType
//
//    @MockK
//    lateinit var adQueryMakerType: AdQueryMakerType
//
//    @InjectMockKs
//    lateinit var adRepository: AdRepository
//
//    @Test
//    fun test_getAdCalled_validResponse_success() {
//        // Given
//        coEvery { adDataSourceType.getAd(any(), any()) } returns DataResult.Success(mockk())
//        coEvery { adQueryMakerType.makeAdQuery(any()) } returns mockk()
//
//        // When
//        val result = runBlocking { adRepository.getAd(1, mockk()) }
//
//        // Then
//        assertEquals(true, result.isSuccess)
//    }
//
//    @Test
//    fun test_getAdCalled_invalidResponse_failure() {
//        // Given
//        coEvery { adDataSourceType.getAd(any(), any()) } returns DataResult.Failure(mockk())
//        coEvery { adQueryMakerType.makeAdQuery(any()) } returns mockk()
//
//        // When
//        val result = runBlocking { adRepository.getAd(1, mockk()) }
//
//        // Then
//        assertEquals(true, result.isFailure)
//    }
//}