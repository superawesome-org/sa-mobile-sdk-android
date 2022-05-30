package tv.superawesome.sdk.publisher

import org.json.JSONObject

data class QueryAdditionalOptions(val map: Map<String, String>) {
    fun appendTo(jsonObject: JSONObject) {
        map.forEach { (key, value) ->
            jsonObject.put(key, value)
        }
    }

    companion object {
        var default: QueryAdditionalOptions? = null

        fun appendTo(jsonObject: JSONObject) {
            default?.appendTo(jsonObject)
        }
    }
}