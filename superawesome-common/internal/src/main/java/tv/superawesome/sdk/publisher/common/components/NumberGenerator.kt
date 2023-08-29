package tv.superawesome.sdk.publisher.common.components

import java.util.UUID

interface NumberGeneratorType {
    /**
     * @return Random percentage between 0.0 and 1.0
     */
    fun nextIntForCache(): Int

    /**
     * @return Random number for the parental gate puzzle
     * between [NumberGenerator.PARENTAL_GATE_MIN] and [NumberGenerator.PARENTAL_GATE_MAX]
     */
    fun nextIntForParentalGate(): Int

    /**
     * @return Random alphanumeric string with the given [length]
     */
    fun nextAlphanumericString(length: Int): String
}

class NumberGenerator : NumberGeneratorType {

    override fun nextIntForCache(): Int = (CACHE_BOUND_MIN..CACHE_BOUND_MAX).random()
    override fun nextIntForParentalGate(): Int = (PARENTAL_GATE_MIN..PARENTAL_GATE_MAX).random()

    override fun nextAlphanumericString(length: Int): String = UUID.randomUUID().toString()

    companion object {
        private const val CACHE_BOUND_MIN: Int = 1_000_000
        private const val CACHE_BOUND_MAX: Int = 1_500_000

        private const val PARENTAL_GATE_MIN: Int = 50
        private const val PARENTAL_GATE_MAX: Int = 99
    }
}
