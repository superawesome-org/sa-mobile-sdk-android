package tv.superawesome.sdk.publisher

import org.json.JSONObject

class QueryBuilder {

    /**
     * @param new an optional dictionary to be merged with an existing dictionary
     * where only String or Int values are supported.
     * @param original: the original dictionary to merge with.
     */
    fun merge(new: Map<String, Any>?, original: MutableMap<String, Any>) {
        new?.forEach { (key, value) ->
            when (value){
                is String -> original[key] = value
                is Int -> original[key] = value
            }
        }
    }

    /**
     * @param new an optional dictionary to be merged with an existing JSONObject
     * where only String or Int values are supported.
     * @param original: the original JSONObject to merge with.
     */
    fun merge(new: Map<String, Any>?, original: JSONObject) {
        new?.forEach { (key, value) ->
            when (value){
                is String -> original.put(key, value)
                is Int -> original.put(key, value)
            }
        }
    }
}