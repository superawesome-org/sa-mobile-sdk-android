package tv.superawesome.lib.featureflags

/**
 * Holds all the flag conditions in one struct. Allows checking if input values matches the
 * conditions.
 *
 * @property placementIds the placementId condition.
 * @property lineItemIds the lineItemId condition.
 * @property creativeIds the creativeId condition.
 * @property percentage the percentage rollout condition.
 */
data class FlagConditions(
    val placementIds: FlagCondition.PlacementIds? = null,
    val lineItemIds: FlagCondition.LineItemIds? = null,
    val creativeIds: FlagCondition.CreativeIds? = null,
    val percentage: FlagCondition.Percentage? = null,
) {

    /**
     * Matches the input values (placementId, lineItemId, creativeId and user value) to the
     * conditions.
     * @return whether the input matches against the conditions.
     */
    fun match(placementId: Int, lineItemId: Int, creativeId: Int, userValue: Int): Boolean {
        val placementMatches = placementIds?.match(placementId) ?: true
        val lineItemMatches = lineItemIds?.match(lineItemId) ?: true
        val creativeMatches = creativeIds?.match(creativeId) ?: true
        val userValueMatches = percentage?.match(userValue) ?: true

        return placementMatches && lineItemMatches && creativeMatches && userValueMatches
    }
}
