package tv.superawesome.sdk.publisher.common.models

data class QueryAdditionalOptions(val options: Map<String, String>) {
    companion object {
        var instance: QueryAdditionalOptions? = null
    }
}