package tv.superawesome.lib.featureflags

sealed class FlagCondition {

    data class PlacementIds(val ids: List<Int>): FlagCondition()
    data class LineItemIds(val ids: List<Int>): FlagCondition()
    data class CreativeIds(val ids: List<Int>): FlagCondition()
    data class Percentage(val value: Int): FlagCondition()
}