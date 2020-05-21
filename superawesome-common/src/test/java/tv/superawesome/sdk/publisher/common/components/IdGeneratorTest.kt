package tv.superawesome.sdk.publisher.common.components

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import tv.superawesome.sdk.publisher.common.base.BaseTest
import tv.superawesome.sdk.publisher.common.repositories.PreferencesRepositoryType
import kotlin.test.assertEquals

class IdGeneratorTest : BaseTest()  {
    @MockK
    lateinit var googleAdvertisingProxyType: GoogleAdvertisingProxyType

    @MockK
    lateinit var preferencesRepositoryType: PreferencesRepositoryType

    @MockK
    lateinit var sdkInfoType: SdkInfoType

    @MockK
    lateinit var numberGeneratorType: NumberGeneratorType

    @InjectMockKs
    lateinit var idGenerator: IdGenerator

    @Test
    fun test_googleServicesNotAvailable_returnsNoTracking() {
        // Given
        coEvery { googleAdvertisingProxyType.findAdvertisingId() } returns null

        // When
        val result = runBlocking { idGenerator.findDauId() }

        // Then
        assertEquals(IdGenerator.Keys.noTracking, result)
    }

    @Test
    fun test_dauId_advertisingDisabled_returnsNoTracking() {
        // Given
        coEvery { googleAdvertisingProxyType.findAdvertisingId() } returns null
        every { preferencesRepositoryType.dauUniquePart } returns "savedPart123"
        every { sdkInfoType.bundle } returns "sdkBundle123"

        // When
        val result = runBlocking { idGenerator.findDauId() }

        // Then
        assertEquals(IdGenerator.Keys.noTracking, result)
    }

    @Test
    fun test_dauId_advertisingEnabled_returnsUniqueDauId() {
        // Given
        coEvery { googleAdvertisingProxyType.findAdvertisingId() } returns "googleId123"
        every { preferencesRepositoryType.dauUniquePart } returns "savedPart123"
        every { sdkInfoType.bundle } returns "sdkBundle123"

        // When
        val result = runBlocking { idGenerator.findDauId() }

        // Then
        assertEquals(220091408, result)
    }

    @Test
    fun test_dauId_advertisingEnabledNoSavedPart_returnsUniqueDauId() {
        // Given
        coEvery { googleAdvertisingProxyType.findAdvertisingId() } returns "googleId123"
        every { preferencesRepositoryType.dauUniquePart } returns null
        every { numberGeneratorType.nextAlphanumericString(any()) } returns "randomAbc"
        every { sdkInfoType.bundle } returns "sdkBundle123"

        // When
        val result = runBlocking { idGenerator.findDauId() }

        // Then
        assertEquals(59445321, result)
    }
}