package tv.superawesome.lib.featureflags

import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class FlagConditionTests {

    @Test
    fun `if id is in placementIds, it should match`() {
        val id = 1
        val condition = FlagCondition.PlacementIds(listOf(1,2,3))

        assertTrue(condition.match(id))
    }

    @Test
    fun `if id is not in placementIds, it should not match`() {
        val id = 4
        val condition = FlagCondition.PlacementIds(listOf(1,2,3))

        assertFalse(condition.match(id))
    }

    @Test
    fun `if placementIds is empty, it should always match`() {
        val id = 4
        val condition = FlagCondition.PlacementIds(emptyList())

        assertTrue(condition.match(id))
    }

    @Test
    fun `if id is in lineItemIds, it should match`() {
        val id = 1
        val condition = FlagCondition.LineItemIds(listOf(1,2,3))

        assertTrue(condition.match(id))
    }

    @Test
    fun `if id is not in lineItemIds, it should not match`() {
        val id = 4
        val condition = FlagCondition.LineItemIds(listOf(1,2,3))

        assertFalse(condition.match(id))
    }

    @Test
    fun `if lineItemIds is empty, it should always match`() {
        val id = 4
        val condition = FlagCondition.LineItemIds(emptyList())

        assertTrue(condition.match(id))
    }

    @Test
    fun `if id is in creativeIds, it should match`() {
        val id = 1
        val condition = FlagCondition.CreativeIds(listOf(1,2,3))

        assertTrue(condition.match(id))
    }

    @Test
    fun `if id is not in creativeIds, it should not match`() {
        val id = 4
        val condition = FlagCondition.CreativeIds(listOf(1,2,3))

        assertFalse(condition.match(id))
    }

    @Test
    fun `if creativeIds is empty, it should always match`() {
        val id = 4
        val condition = FlagCondition.CreativeIds(emptyList())

        assertTrue(condition.match(id))
    }

    @Test
    fun `if value is in range, it should match`() {
        val userValue = 50
        val condition = FlagCondition.Percentage(80)

        assertTrue(condition.match(userValue))
    }

    @Test
    fun `if value is not in range, it should not match`() {
        val userValue = 50
        val condition = FlagCondition.Percentage(40)

        assertFalse(condition.match(userValue))
    }
}