package tv.superawesome.sdk.publisher.common.components

import java.util.*


interface NumberGeneratorType {
    fun nextIntForCache(): Int
    fun nextAlphanumericString(length: Int): String
}

class NumberGenerator : NumberGeneratorType {
    private val cacheBoundMin: Int = 1000000
    private val cacheBoundMax: Int = 1500000

    override fun nextIntForCache(): Int = (cacheBoundMin..cacheBoundMax).random()

    override fun nextAlphanumericString(length: Int): String = UUID.randomUUID().toString()
}
