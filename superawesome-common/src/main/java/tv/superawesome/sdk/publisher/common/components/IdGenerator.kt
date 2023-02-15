package tv.superawesome.sdk.publisher.common.components

import tv.superawesome.sdk.publisher.common.repositories.PreferencesRepositoryType
import kotlin.math.abs

/**
 * Interface that abstracts away generating a distinct ID called "DAU ID", which consists of:
 *  - the Advertising ID int
 *  - a random ID
 *  - the package name
 * each hashed and then XOR-ed together
 */
interface IdGeneratorType {
    fun findDauId(): Int
}

class IdGenerator(
    private val dateProvider: DateProviderType,
    private val preferencesRepository: PreferencesRepositoryType,
    private val sdkInfo: SdkInfoType,
    private val numberGenerator: NumberGeneratorType
) : IdGeneratorType {

    override fun findDauId(): Int {
        val firstPart = dateProvider.nowAsMonthYear()
        val secondPart = preferencesRepository.dauUniquePart ?: generateAndSavePartOfDau()
        val thirdPart = sdkInfo.bundle

        return abs(firstPart.hashCode() xor secondPart.hashCode() xor thirdPart.hashCode())
    }

    private fun generateAndSavePartOfDau(): String {
        val generatedNumber = numberGenerator.nextAlphanumericString(length = DAU_KEY_LENGTH)
        preferencesRepository.dauUniquePart = generatedNumber
        return generatedNumber
    }
}

private const val DAU_KEY_LENGTH = 32
