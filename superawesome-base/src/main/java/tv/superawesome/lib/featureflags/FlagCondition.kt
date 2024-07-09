package tv.superawesome.lib.featureflags

/**
 * Defines conditions to enable a feature flag.
 */
sealed interface FlagCondition {

    /**
     * Matches a input value to the flag condition.
     * @param input the input value (a placement, lineItem, creative ID or the user's rolled value).
     * @return `true` if the value matches, `false` otherwise.
     */
    fun match(input: Int): Boolean

    /**
     * Placement ids.
     * @property ids a list of placement ids.
     */
    data class PlacementIds(val ids: List<Int>): FlagCondition {
        override fun match(input: Int) = ids.contains(input)
    }

    /**
     * LineItem ids.
     * @property ids a list of line item ids.
     */
    data class LineItemIds(val ids: List<Int>): FlagCondition {
        override fun match(input: Int) = ids.contains(input)
    }

    /**
     * Creative ids.
     * @property ids a list of creative ids.
     */
    data class CreativeIds(val ids: List<Int>): FlagCondition {
        override fun match(input : Int) = ids.contains(input)
    }

    /**
     * Percentage.
     * @property value the percentage value (0-100) of the flag rollout.
     */
    data class Percentage(val value: Int): FlagCondition {
        override fun match(input: Int) = input <= value
    }
}
