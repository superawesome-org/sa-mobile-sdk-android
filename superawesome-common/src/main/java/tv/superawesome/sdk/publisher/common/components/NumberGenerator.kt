package tv.superawesome.sdk.publisher.common.components

import java.util.UUID

internal interface NumberGeneratorType {
    /**
     * @return Random percentage between 0.0 and 1.0
     */
    fun nextIntForCache(): Int

    /**
     * @return Random number for the parental gate puzzle
     * between [NumberGenerator.parentalGateMin] and [NumberGenerator.parentalGateMax]
     */
    fun nextIntForParentalGate(): Int

    /**
     * @return Random alphanumeric string with the given [length]
     */
    fun nextAlphanumericString(length: Int): String
}

internal class NumberGenerator : NumberGeneratorType {
    private val cacheBoundMin: Int = 1000000
    private val cacheBoundMax: Int = 1500000

    private val parentalGateMin: Int = 50
    private val parentalGateMax: Int = 99

    override fun nextIntForCache(): Int = (cacheBoundMin..cacheBoundMax).random()
    override fun nextIntForParentalGate(): Int = (parentalGateMin..parentalGateMax).random()

    override fun nextAlphanumericString(length: Int): String = UUID.randomUUID().toString()
}
