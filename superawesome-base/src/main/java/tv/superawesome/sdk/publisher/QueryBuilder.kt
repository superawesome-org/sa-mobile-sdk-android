package tv.superawesome.sdk.publisher

import org.json.JSONObject

class QueryBuilder {

    fun merge(new: Map<String, Any>, original: JSONObject) {
        new.forEach { (key, value) ->
            when (value){
                is String -> original.put(key, value)
                is Int -> original.put(key, value)
            }
        }
    }
}