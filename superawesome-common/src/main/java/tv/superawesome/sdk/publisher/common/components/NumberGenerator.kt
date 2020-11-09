package tv.superawesome.sdk.publisher.common.components

import java.util.*


interface NumberGeneratorType {
    /**
     * @return Random percentage between 0.0 and 1.0
     */
    fun nextDoubleForMoat(): Double
    fun nextIntForCache(): Int
    fun nextIntForParentalGate(): Int
    fun nextAlphanumericString(length: Int): String
}

class NumberGenerator : NumberGeneratorType {
    private val cacheBoundMin: Int = 1000000
    private val cacheBoundMax: Int = 1500000

    private val parentalGateMin: Int = 50
    private val parentalGateMax: Int = 99

    private val moatMin: Int = 0
    private val moatMax: Int = 100

    override fun nextDoubleForMoat(): Double = (moatMin..moatMax).random() / 100.0

    override fun nextIntForCache(): Int = (cacheBoundMin..cacheBoundMax).random()
    override fun nextIntForParentalGate(): Int = (parentalGateMin..parentalGateMax).random()

    override fun nextAlphanumericString(length: Int): String = UUID.randomUUID().toString()
}
