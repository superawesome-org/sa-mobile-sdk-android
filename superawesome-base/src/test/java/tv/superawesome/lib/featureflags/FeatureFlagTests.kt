package tv.superawesome.lib.featureflags

import org.json.JSONException
import org.json.JSONObject
import org.junit.Assert.assertFalse
import org.junit.Assert.assertThrows
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class FeatureFlagTests {

    @Test
    fun `can parse a full JSONObject successfully`() {
        val json = JSONObject(JSON_STRING)

        val featureFlag = FeatureFlag.fromJson<Int>(json, "flag1")

        assertNotNull(featureFlag)
        assertEquals(5, featureFlag.value)
        assertEquals(4, featureFlag.conditions.size)
        assertTrue(featureFlag.conditions[0] is FlagCondition.PlacementIds)
        assertEquals(listOf(1,2,3), (featureFlag.conditions[0] as? FlagCondition.PlacementIds)?.ids)
        assertTrue(featureFlag.conditions[1] is FlagCondition.LineItemIds)
        assertEquals(listOf(1,2,3), (featureFlag.conditions[1] as? FlagCondition.LineItemIds)?.ids)
        assertTrue(featureFlag.conditions[2] is FlagCondition.CreativeIds)
        assertEquals(listOf(1,2,3), (featureFlag.conditions[2] as? FlagCondition.CreativeIds)?.ids)
        assertTrue(featureFlag.conditions[3] is FlagCondition.Percentage)
        assertEquals(80, (featureFlag.conditions[3] as? FlagCondition.Percentage)?.value)
    }

    @Test
    fun `can parse a JSONObject if any conditions are missing`() {
        val json = JSONObject(JSON_STRING)

        val featureFlag = FeatureFlag.fromJson<Int>(json, "flag2")

        assertNotNull(featureFlag)
        assertEquals(10, featureFlag.value)
        assertEquals(0, featureFlag.conditions.size)
    }

    @Test
    fun `can parse a JSONObject if the conditions obj is missing`() {
        val json = JSONObject(JSON_STRING)

        val featureFlag = FeatureFlag.fromJson<Int>(json, "flag3")

        assertNotNull(featureFlag)
        assertEquals(10, featureFlag.value)
        assertEquals(0, featureFlag.conditions.size)
    }

    @Test
    fun `can parse a JSONObject if the value is missing (on-off flag)`() {
        val json = JSONObject(JSON_STRING)

        val featureFlag = FeatureFlag.fromJson<Boolean>(json, "flag4")

        assertNotNull(featureFlag)
        assertEquals(1, featureFlag.conditions.size)
        assertTrue(featureFlag.conditions[0] is FlagCondition.Percentage)
        assertEquals(60, (featureFlag.conditions[0] as? FlagCondition.Percentage)?.value)
    }

    @Test
    fun `can parse a JSONObject if the value is double`() {
        val json = JSONObject(JSON_STRING)

        val featureFlag = FeatureFlag.fromJson<Double>(json, "flag5")

        assertNotNull(featureFlag)
        assertEquals(3.9, featureFlag.value)
        assertEquals(0, featureFlag.conditions.size)
    }

    @Test
    fun `can parse a JSONObject if the value is boolean`() {
        val json = JSONObject(JSON_STRING)

        val featureFlag = FeatureFlag.fromJson<Boolean>(json, "flag6")

        assertNotNull(featureFlag)
        assertEquals(true, featureFlag.value)
        assertEquals(0, featureFlag.conditions.size)
    }

    @Test
    fun `throws if a JSONObject has an unsupported value in value field`() {
        val json = JSONObject(JSON_STRING)

        assertThrows(JSONException::class.java) {
            FeatureFlag.fromJson<Boolean>(json, "flag5")
        }
    }

    @Test
    fun `throws if feature flag doesn't exist in JSON`() {
        val json = JSONObject(JSON_STRING)

        assertThrows(JSONException::class.java) {
            FeatureFlag.fromJson<Unit>(json, "flagNull")
        }
    }

    @Test
    fun `flag is enabled if conditions are met`() {
        val json = JSONObject(JSON_STRING)

        val isEnabled = FeatureFlag.fromJson<Int>(json, "flag1").isEnabled(1, 1, 1, 50)

        assertTrue(isEnabled)
    }

    @Test
    fun `flag is not enabled if one of the conditions (placement) is not met`() {
        val json = JSONObject(JSON_STRING)

        val isEnabled = FeatureFlag.fromJson<Int>(json, "flag1").isEnabled(6, 1, 1, 50)

        assertFalse(isEnabled)
    }

    @Test
    fun `flag is not enabled if one of the conditions (lineitem) is not met`() {
        val json = JSONObject(JSON_STRING)

        val isEnabled = FeatureFlag.fromJson<Int>(json, "flag1").isEnabled(1, 4, 1, 50)

        assertFalse(isEnabled)
    }

    @Test
    fun `flag is not enabled if one of the conditions (creative) is not met`() {
        val json = JSONObject(JSON_STRING)

        val isEnabled = FeatureFlag.fromJson<Int>(json, "flag1").isEnabled(1, 1, 4, 50)

        assertFalse(isEnabled)
    }

    @Test
    fun `flag is not enabled if one of the conditions (percentage) is not met`() {
        val json = JSONObject(JSON_STRING)

        val isEnabled = FeatureFlag.fromJson<Int>(json, "flag1").isEnabled(1, 1, 1, 90)

        assertFalse(isEnabled)
    }

    companion object {
        const val JSON_STRING = """
        {
            "flag1": {
                "value": 5,
                "conditions": {
                    "placementIds": [1,2,3],
                    "lineItemIds": [1,2,3],
                    "creativeIds": [1,2,3],
                    "percentage": 80
                }
            },
            "flag2": {
                "value": 10,
                "conditions": {}
            },
            "flag3": {
                "value": 10
            },
            "flag4": {
                "value": false,
                "conditions": {
                    "percentage": 60
                }
            },
            "flag5": {
                "value": 3.9
            },
            "flag6": {
                "value": true
            }
        }
        """
    }
}