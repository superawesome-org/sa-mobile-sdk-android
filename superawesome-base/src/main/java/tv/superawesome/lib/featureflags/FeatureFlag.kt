package tv.superawesome.lib.featureflags

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

data class FeatureFlag<T>(
    val value: T,
    val conditions: List<FlagCondition> = emptyList(),
) {

    fun isEnabled(placementId: Int, lineItemId: Int, creativeId: Int, userValue: Int): Boolean {
        for (condition in conditions) {
            when (condition) {
                is FlagCondition.PlacementIds -> {
                    if (placementId !in condition.ids) return false
                }
                is FlagCondition.LineItemIds -> {
                    if (lineItemId !in condition.ids) return false
                }
                is FlagCondition.CreativeIds -> {
                    if (creativeId !in condition.ids) return false
                }
                is FlagCondition.Percentage -> {
                    if (userValue > condition.value) return false
                }
            }
        }
        return true
    }

    companion object {
        inline fun <reified T> fromJson(json: JSONObject, name: String): FeatureFlag<T> {
            val keyObj = json.getJSONObject(name)

            val conditions = keyObj.optJSONObject("conditions")?.let { c ->
                listOf<FlagCondition>() +
                        c.optJSONArray("placementIds")?.mapToInt()?.let { ids ->
                          FlagCondition.PlacementIds(ids)
                        } +
                        c.optJSONArray("lineItemIds")?.mapToInt()?.let { ids ->
                            FlagCondition.LineItemIds(ids)
                        } +
                        c.optJSONArray("creativeIds")?.mapToInt()?.let { ids ->
                            FlagCondition.CreativeIds(ids)
                        } +
                        c.optInt("percentage").takeIf { it > 0 }?.let { pct ->
                            FlagCondition.Percentage(pct)
                        }
            }?.filterNotNull() ?: emptyList()

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
                )
            } else {
                throw JSONException("Missing value field for flag $name")
            }
        }

        fun JSONArray.mapToInt(): List<Int> {
            val list = mutableListOf<Int>()
            for (i in 0 until length()) {
                list.add(getInt(i))
            }
            return list
        }
    }
}
