package tv.superawesome.sdk.publisher.common.components

import java.util.UUID

internal interface NumberGeneratorType {
    /**
     * @return Random percentage between 0.0 and 1.0
     */
    fun nextIntForCache(): Int
    fun nextIntForParentalGate(): Int
    fun nextDoubleForMoat(): Double
    fun nextAlphanumericString(length: Int): String
}

internal class NumberGenerator : NumberGeneratorType {

    override fun nextIntForCache(): Int = (CACHE_BOUND_MIN..CACHE_BOUND_MAX).random()
    override fun nextIntForParentalGate(): Int = (PARENTAL_GATE_MIN..PARENTAL_GATE_MAX).random()
    override fun nextDoubleForMoat(): Double =
        (MOAT_MIN..MOAT_MAX).random().toDouble() / MOAT_MAX.toDouble()
    override fun nextAlphanumericString(length: Int): String = UUID.randomUUID().toString()

    companion object {
        private const val CACHE_BOUND_MIN: Int = 1000000
        private const val CACHE_BOUND_MAX: Int = 1500000
        private const val PARENTAL_GATE_MIN: Int = 50
        private const val PARENTAL_GATE_MAX: Int = 99
        private const val MOAT_MIN: Int = 0
        private const val MOAT_MAX: Int = 1000
    }
}
