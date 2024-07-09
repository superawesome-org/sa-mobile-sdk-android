package tv.superawesome.lib.featureflags

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * Defines a feature flag.
 *
 * @param T type of flag: [Boolean], [Int], [Long].
 * @property value the value of the flag. Usually a number of a boolean. E.g.
 * ```json
 * {
 *    "isExoPlayerEnabled": {
 *      "value": true
 *    }
 *  }
 * ```
 * @property conditions a list of [FlagCondition] to enable this flag. The flag is considered
 * to be "enabled" if, and only if, ALL the conditions are met. E.g.:
 * If we have a [FlagCondition.PlacementIds] condition, only ads with those placements will have
 * the flag enabled, all others will receive the [defaultValue].
 * @property defaultValue the value to default in case the flag is considered to be disabled.
 */
data class FeatureFlag<T>(
    val value: T,
    val conditions: FlagConditions,
    private val defaultValue: T,
) {

    /**
     * Gets the value of the flag for the given [placementId], [lineItemId], [creativeId] and
     * [userValue]. [userValue] is the value that is rolled for each SDK initialization and decides
     * whether or not the flag is enabled for the user based on the [FlagCondition.Percentage] rollout.
     */
    @Suppress("CognitiveComplexMethod", "ReturnCount")
    fun getValue(placementId: Int, lineItemId: Int, creativeId: Int, userValue: Int): T =
        if (conditions.match(placementId, lineItemId, creativeId, userValue)) {
            value
        } else {
            defaultValue
        }

    companion object {

        /**
         * Creates a [FeatureFlag] object from the given pair of [JSONObject] and [name], which is the
         * key that identifies the flag inside the json object. Also requires the [defaultValue]
         * for the flag.
         */
        @Suppress("StringLiteralDuplication", "MaxLineLength")
        inline fun <reified T> fromJson(json: JSONObject, name: String, defaultValue: T): FeatureFlag<T> {
            val keyObj = json.getJSONObject(name)

            val conditions = keyObj.optJSONObject("conditions")?.let { c ->
                FlagConditions(
                    placementIds = c.optJSONArray("placementIds")?.mapToInt()?.let { ids ->
                        FlagCondition.PlacementIds(ids)
                    },
                    lineItemIds = c.optJSONArray("lineItemIds")?.mapToInt()?.let { ids ->
                        FlagCondition.LineItemIds(ids)
                    },
                    creativeIds = c.optJSONArray("creativeIds")?.mapToInt()?.let { ids ->
                        FlagCondition.CreativeIds(ids)
                    },
                    percentage = c.optInt("percentage").takeIf { it > 0 }?.let { pct ->
                        FlagCondition.Percentage(pct)
                    }
                )
            } ?: FlagConditions()

            return if (keyObj.has("value")) {
                val v = when (T::class) {
                    Int::class -> keyObj.getInt("value")
                    Long::class -> keyObj.getLong("value")
                    Boolean::class -> keyObj.getBoolean("value")
                    Double::class -> keyObj.getDouble("value")
                    else -> throw JSONException("Illegal value type in value field: expected ${T::class.simpleName}, found ${keyObj.get("value")::class.simpleName}")
                }

                FeatureFlag(
                    value = v as T,
                    conditions = conditions,
                    defaultValue = defaultValue,
                )
            } else {
                throw JSONException("Missing value field for flag $name")
            }
        }

        /**
         * Extension for JSONArray to map entries (ids) into integers.
         */
        fun JSONArray.mapToInt(): List<Int> {
            val list = mutableListOf<Int>()
            for (i in 0 until length()) {
                list.add(getInt(i))
            }
            return list
        }
    }
}
