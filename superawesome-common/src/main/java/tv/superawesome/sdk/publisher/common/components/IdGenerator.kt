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
    suspend fun findDauId(): Int
}

class IdGenerator(private val googleAdvertisingProxy: GoogleAdvertisingProxyType,
                  private val preferencesRepository: PreferencesRepositoryType,
                  private val sdkInfo: SdkInfoType,
                  private val numberGenerator: NumberGeneratorType) : IdGeneratorType {
    private object Keys {
        const val initial = -1
        const val noTracking = 0
        const val dauLength = 32
    }

    private var dauId: Int = Keys.initial

    override suspend fun findDauId(): Int {
        if (dauId == Keys.initial) {
            dauId = composeDauId()
        }
        return dauId
    }

    private suspend fun composeDauId(): Int {
        val firstPart = googleAdvertisingProxy.advertisingId.await() ?: return Keys.noTracking
        val secondPart = preferencesRepository.dauUniquePart ?: generateAndSavePartOfDau()
        val thirdPart = sdkInfo.bundle

        return abs(firstPart.hashCode() xor secondPart.hashCode() xor thirdPart.hashCode())
    }

    private fun generateAndSavePartOfDau(): String {
        val generatedNumber = numberGenerator.nextAlphanumericString(length = Keys.dauLength)
        preferencesRepository.dauUniquePart = generatedNumber
        return generatedNumber
    }
}
