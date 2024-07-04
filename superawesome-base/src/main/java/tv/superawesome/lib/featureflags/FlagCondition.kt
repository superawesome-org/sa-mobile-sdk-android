package tv.superawesome.lib.featureflags

/**
 * Defines conditions to enable a feature flag.
 */
sealed class FlagCondition {

    /**
     * Placement ids.
     * @property ids a list of placement ids.
     */
    data class PlacementIds(val ids: List<Int>): FlagCondition()

    /**
     * LineItem ids.
     * @property ids a list of line item ids.
     */
    data class LineItemIds(val ids: List<Int>): FlagCondition()

    /**
     * Creative ids.
     * @property ids a list of creative ids.
     */
    data class CreativeIds(val ids: List<Int>): FlagCondition()

    /**
     * Percentage.
     * @property value the percentage value (0-100) of the flag rollout.
     */
    data class Percentage(val value: Int): FlagCondition()
}
