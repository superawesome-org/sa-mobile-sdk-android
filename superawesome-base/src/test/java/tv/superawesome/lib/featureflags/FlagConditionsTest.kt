package tv.superawesome.lib.featureflags

import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class FlagConditionsTest {

    @Test
    fun `if any of placementId, lineItemId or creativeId don't match, then it should not match`() {
        val conditions = FlagConditions(
            placementIds = FlagCondition.PlacementIds(listOf(1,2,3)),
            lineItemIds = FlagCondition.LineItemIds(listOf(4,5,6)),
            creativeIds = FlagCondition.CreativeIds(listOf(7,8,9)),
            percentage = null,
        )

        assertFalse(
            conditions.match(2, 4, 10, 40),
            "Ids (2,4,10) are not matching placements[1,2,3] or lineItems[4,5,6] or creatives[7,8,9]",
        )
    }

    @Test
    fun `if all ids match, then it should match`() {
        val conditions = FlagConditions(
            placementIds = FlagCondition.PlacementIds(listOf(1,2,3)),
            lineItemIds = FlagCondition.LineItemIds(listOf(4,5,6)),
            creativeIds = FlagCondition.CreativeIds(listOf(7,8,9)),
            percentage = null,
        )

        assertTrue(
            conditions.match(1,4,7, 40),
            "No ids are matching placements[1,2,3] or lineItems[4,5,6] or creatives[7,8,9]",
        )
    }

    @Test
    fun `if ids match, but user value does not, then it does not match`() {
        val conditions = FlagConditions(
            placementIds = FlagCondition.PlacementIds(listOf(1,2,3)),
            lineItemIds = FlagCondition.LineItemIds(listOf(4,5,6)),
            creativeIds = FlagCondition.CreativeIds(listOf(7,8,9)),
            percentage = FlagCondition.Percentage(50),
        )

        assertFalse(
            conditions.match(1,4,7, 80),
            "User rolled value 80 should not match at 50% rollout",
        )
    }

    @Test
    fun `if conditions are not present, it should match`() {
        val conditions = FlagConditions(
            placementIds = null,
            lineItemIds = null,
            creativeIds = null,
            percentage = null,
        )

        assertTrue(
            conditions.match(1, 4, 7, 80),
            "No conditions are present, it should always match",
        )
    }

    @Test
    fun `if conditions are present, but empty, it should match`() {
        val conditions = FlagConditions(
            placementIds = FlagCondition.PlacementIds(emptyList()),
            lineItemIds = FlagCondition.LineItemIds(emptyList()),
            creativeIds = FlagCondition.CreativeIds(emptyList()),
            percentage = null,
        )

        assertTrue(
            conditions.match(1, 4, 7, 80),
            "Conditions are empty, it should always match",
        )
    }

    @Test
    fun `if ids are not present, match if user value is in range`() {
       val conditions = FlagConditions(
           placementIds = null,
           lineItemIds = null,
           creativeIds = null,
           percentage = FlagCondition.Percentage(50)
       )

       assertTrue(
           conditions.match(1, 4, 7, 40),
           "User rolled value 40 should match 50% rollout",
       )
    }

    @Test
    fun `if placementId is present, don't match if id don't match`() {
        val conditions = FlagConditions(
            placementIds = FlagCondition.PlacementIds(listOf(1)),
            lineItemIds = null,
            creativeIds = null,
            percentage = null,
        )

        assertFalse(
            conditions.match(2, 4, 7, 80),
            "Placement Id 2 is not matching placementIds[1]",
        )
    }

    @Test
    fun `if lineItemId is present, don't match if id don't match`() {
        val conditions = FlagConditions(
            placementIds = null,
            lineItemIds = FlagCondition.LineItemIds(listOf(4)),
            creativeIds = null,
            percentage = null,
        )

        assertFalse(
            conditions.match(1, 10, 7, 80),
            "LineItem Id 10 is not matching lineItemsIds[4]",
        )
    }

    @Test
    fun `if creativeId is present, don't match if id don't match`() {
        val conditions = FlagConditions(
            placementIds = null,
            lineItemIds = null,
            creativeIds = FlagCondition.CreativeIds(listOf(7)),
            percentage = null,
        )

        assertFalse(
            conditions.match(1, 5, 8, 80),
            "Creative Id 8 is not matching creativeIds[7]",
        )
    }
}