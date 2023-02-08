package tv.superawesome.sdk.publisher.common.models

data class QueryAdditionalOptions(val options: Map<String, Any>) {
    companion object {
        var instance: QueryAdditionalOptions? = null
    }
}