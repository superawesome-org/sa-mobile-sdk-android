package tv.superawesome.sdk.publisher.common.repositories

import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import tv.superawesome.sdk.publisher.common.base.BaseTest
import tv.superawesome.sdk.publisher.common.network.datasources.NetworkDataSourceType
import tv.superawesome.sdk.publisher.common.models.*
import tv.superawesome.sdk.publisher.common.network.DataResult
import tv.superawesome.sdk.publisher.common.testutil.FakeFactory

@OptIn(ExperimentalCoroutinesApi::class)
internal class VastEventRepositoryTest : BaseTest() {
    @MockK
    lateinit var vastAd: VastAd

    @MockK
    lateinit var dataSource: NetworkDataSourceType

    @InjectMockKs
    lateinit var repository: VastEventRepository

    private val listOfUrls = listOf(
        FakeFactory.exampleUrl, FakeFactory.exampleVastUrl
    )

    @Before
    fun setup() {
        coEvery { dataSource.getData(any()) } returns DataResult.Success("")
    }

    @Test
    fun test_clickThrough() = runTest {
        // Given
        every { vastAd.clickThroughUrl } returns FakeFactory.exampleUrl

        // When
        repository.clickThrough()

        // Then
        coVerify { dataSource.getData(FakeFactory.exampleUrl) }
    }

    private fun verifyDataSourceCalledForUrls() {
        listOfUrls.forEach {
            coVerify { dataSource.getData(it) }
        }
    }

    @Test
    fun test_error() = runTest {
        // Given
        every { vastAd.errorEvents } returns listOfUrls

        // When
        repository.error()

        // Then
        verifyDataSourceCalledForUrls()
    }

    @Test
    fun test_impression() = runTest {
        // Given
        every { vastAd.impressionEvents } returns listOfUrls

        // When
        repository.impression()

        // Then
        verifyDataSourceCalledForUrls()
    }

    @Test
    fun test_creativeView() = runTest {
        // Given
        every { vastAd.creativeViewEvents } returns listOfUrls

        // When
        repository.creativeView()

        // Then
        verifyDataSourceCalledForUrls()
    }

    @Test
    fun test_start() = runTest {
        // Given
        every { vastAd.startEvents } returns listOfUrls

        // When
        repository.start()

        // Then
        verifyDataSourceCalledForUrls()
    }

    @Test
    fun test_firstQuartile() = runTest {
        // Given
        every { vastAd.firstQuartileEvents } returns listOfUrls

        // When
        repository.firstQuartile()

        // Then
        verifyDataSourceCalledForUrls()
    }

    @Test
    fun test_midPoint() = runTest {
        // Given
        every { vastAd.midPointEvents } returns listOfUrls

        // When
        repository.midPoint()

        // Then
        verifyDataSourceCalledForUrls()
    }

    @Test
    fun test_thirdQuartile() = runTest {
        // Given
        every { vastAd.thirdQuartileEvents } returns listOfUrls

        // When
        repository.thirdQuartile()

        // Then
        verifyDataSourceCalledForUrls()
    }

    @Test
    fun test_complete() = runTest {
        // Given
        every { vastAd.completeEvents } returns listOfUrls

        // When
        repository.complete()

        // Then
        verifyDataSourceCalledForUrls()
    }

    @Test
    fun test_clickTracking() = runTest {
        // Given
        every { vastAd.clickTrackingEvents } returns listOfUrls

        // When
        repository.clickTracking()

        // Then
        verifyDataSourceCalledForUrls()
    }
}