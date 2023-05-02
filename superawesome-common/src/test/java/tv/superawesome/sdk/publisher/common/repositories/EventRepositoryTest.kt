package tv.superawesome.sdk.publisher.common.repositories

import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import tv.superawesome.sdk.publisher.common.base.BaseTest
import tv.superawesome.sdk.publisher.common.components.AdQueryMakerType
import tv.superawesome.sdk.publisher.common.datasources.AwesomeAdsApiDataSourceType
import tv.superawesome.sdk.publisher.common.models.*
import tv.superawesome.sdk.publisher.common.network.DataResult
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
internal class EventRepositoryTest : BaseTest() {
    @MockK
    lateinit var adDataSourceType: AwesomeAdsApiDataSourceType

    @MockK
    lateinit var adQueryMakerType: AdQueryMakerType

    @InjectMockKs
    lateinit var repository: EventRepository

    @Test
    fun test_impression_success() = runTest {
        // Given
        val ad = mockk<Ad>(relaxed = true)
        val adResponse = AdResponse(1, ad)
        coEvery { adQueryMakerType.makeAdQuery(any()) } returns mockk(relaxed = true)
        coEvery { adDataSourceType.impression(any()) } returns DataResult.Success(Unit)

        // When
        val result = repository.impression(adResponse)

        // Then
        assertEquals(true, result.isSuccess)
    }

    @Test
    fun test_click_success() = runTest {
        // Given
        val ad = mockk<Ad>(relaxed = true)
        val adResponse = AdResponse(1, ad)
        coEvery { adQueryMakerType.makeAdQuery(any()) } returns mockk(relaxed = true)
        coEvery { adDataSourceType.click(any()) } returns DataResult.Success(Unit)

        // When
        val result = repository.click(adResponse)

        // Then
        assertEquals(true, result.isSuccess)
    }

    @Test
    fun test_videoClick_success() = runTest {
        // Given
        val ad = mockk<Ad>(relaxed = true)
        val adResponse = AdResponse(1, ad)
        coEvery { adQueryMakerType.makeAdQuery(any()) } returns mockk(relaxed = true)
        coEvery { adDataSourceType.videoClick(any()) } returns DataResult.Success(Unit)

        // When
        val result = repository.videoClick(adResponse)

        // Then
        assertEquals(true, result.isSuccess)
    }

    @Test
    fun test_parentalGateOpen_success() = runTest {
        // Given
        val ad = mockk<Ad>(relaxed = true)
        val adResponse = AdResponse(1, ad)
        val slot = slot<EventData>()
        coEvery {
            adQueryMakerType.makeEventQuery(any(), capture(slot))
        } returns mockk(relaxed = true)
        coEvery { adDataSourceType.event(any()) } returns DataResult.Success(Unit)

        // When
        val result = repository.parentalGateOpen(adResponse)

        // Then
        assertEquals(EventType.ParentalGateOpen, slot.captured.type)
        assertEquals(true, result.isSuccess)
    }

    @Test
    fun test_parentalGateClose_success() = runTest {
        // Given
        val ad = mockk<Ad>(relaxed = true)
        val adResponse = AdResponse(1, ad)
        val slot = slot<EventData>()
        coEvery {
            adQueryMakerType.makeEventQuery(any(), capture(slot))
        } returns mockk(relaxed = true)
        coEvery { adDataSourceType.event(any()) } returns DataResult.Success(Unit)

        // When
        val result = repository.parentalGateClose(adResponse)

        // Then
        assertEquals(EventType.ParentalGateClose, slot.captured.type)
        assertEquals(true, result.isSuccess)
    }

    @Test
    fun test_parentalGateSuccess_success() = runTest {
        // Given
        val ad = mockk<Ad>(relaxed = true)
        val adResponse = AdResponse(1, ad)
        val slot = slot<EventData>()
        coEvery {
            adQueryMakerType.makeEventQuery(any(), capture(slot))
        } returns mockk(relaxed = true)
        coEvery { adDataSourceType.event(any()) } returns DataResult.Success(Unit)

        // When
        val result = repository.parentalGateSuccess(adResponse)

        // Then
        assertEquals(EventType.ParentalGateSuccess, slot.captured.type)
        assertEquals(true, result.isSuccess)
    }

    @Test
    fun test_parentalGateFail_success() = runTest {
        // Given
        val ad = mockk<Ad>(relaxed = true)
        val adResponse = AdResponse(1, ad)
        val slot = slot<EventData>()
        coEvery {
            adQueryMakerType.makeEventQuery(any(), capture(slot))
        } returns mockk(relaxed = true)
        coEvery { adDataSourceType.event(any()) } returns DataResult.Success(Unit)

        // When
        val result = repository.parentalGateFail(adResponse)

        // Then
        assertEquals(EventType.ParentalGateFail, slot.captured.type)
        assertEquals(true, result.isSuccess)
    }

    @Test
    fun test_viewableImpression_success() = runTest {
        // Given
        val ad = mockk<Ad>(relaxed = true)
        val adResponse = AdResponse(1, ad)
        val slot = slot<EventData>()
        coEvery {
            adQueryMakerType.makeEventQuery(any(), capture(slot))
        } returns mockk(relaxed = true)
        coEvery { adDataSourceType.event(any()) } returns DataResult.Success(Unit)

        // When
        val result = repository.viewableImpression(adResponse)

        // Then
        assertEquals(EventType.ViewableImpression, slot.captured.type)
        assertEquals(true, result.isSuccess)
    }

    @Test
    fun test_oneSecondDwellTime_success() = runTest {
        // Given
        val ad = mockk<Ad>(relaxed = true)
        val adResponse = AdResponse(1, ad)
        val slot = slot<EventData>()
        coEvery {
            adQueryMakerType.makeEventQuery(any(), capture(slot))
        } returns mockk(relaxed = true)
        coEvery { adDataSourceType.event(any()) } returns DataResult.Success(Unit)

        // When
        val result = repository.oneSecondDwellTime(adResponse)

        // Then
        assertEquals(EventType.DwellTime, slot.captured.type)
        assertEquals(true, result.isSuccess)
    }
}