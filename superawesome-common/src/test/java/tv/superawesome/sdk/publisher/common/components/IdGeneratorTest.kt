package tv.superawesome.sdk.publisher.common.components

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Test
import tv.superawesome.sdk.publisher.common.base.BaseTest
import tv.superawesome.sdk.publisher.common.repositories.PreferencesRepositoryType
import kotlin.test.assertEquals

class IdGeneratorTest : BaseTest() {
    @MockK
    lateinit var preferencesRepositoryType: PreferencesRepositoryType

    @MockK
    lateinit var sdkInfoType: SdkInfoType

    @MockK
    lateinit var dateProviderType: DateProviderType

    @MockK
    lateinit var numberGeneratorType: NumberGeneratorType

    @InjectMockKs
    lateinit var idGenerator: IdGenerator

    @Test
    fun test_dauId_savedPart_returnsUniqueDauId() {
        // Given
        every { dateProviderType.nowAsMonthYear() } returns "022023"
        every { preferencesRepositoryType.dauUniquePart } returns "savedPart123"
        every { sdkInfoType.bundle } returns "sdkBundle123"

        // When
        val result = runBlocking { idGenerator.findDauId() }

        // Then
        assertEquals(1290765711, result)
    }

    @Test
    fun test_dauId_NoSavedPart_returnsUniqueDauId() {
        // Given
        every { dateProviderType.nowAsMonthYear() } returns "022023"
        every { preferencesRepositoryType.dauUniquePart } returns null
        every { numberGeneratorType.nextAlphanumericString(any()) } returns "randomAbc"
        every { sdkInfoType.bundle } returns "sdkBundle123"

        // When
        val result = runBlocking { idGenerator.findDauId() }

        // Then
        assertEquals(1115341256, result)
    }
}