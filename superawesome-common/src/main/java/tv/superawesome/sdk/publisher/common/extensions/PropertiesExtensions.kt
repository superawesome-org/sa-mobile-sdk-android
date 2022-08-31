package tv.superawesome.sdk.publisher.common.extensions

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.properties.Properties
import kotlinx.serialization.properties.encodeToMap

@ExperimentalSerializationApi
inline fun <reified T> Properties.encodeToMap(value: T,
                                              additionalMap: Map<String, Any>?): Map<String, Any> {
    val map = encodeToMap(value)
    val additionalMap = additionalMap ?: return map
    return map + additionalMap
}