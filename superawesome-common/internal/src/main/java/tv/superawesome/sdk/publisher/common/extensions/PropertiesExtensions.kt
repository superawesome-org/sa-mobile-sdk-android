package tv.superawesome.sdk.publisher.common.extensions

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.properties.Properties
import kotlinx.serialization.properties.encodeToMap

/**
 * Encodes a type to a map and optionally appends another map.
 *
 * @param T type of value to be encoded.
 * @param value the type to encode
 * @param additionalMap an optional map to merge with the encoded type
 * @return the encoded map merged with an additional map if one is passed.
 */
@ExperimentalSerializationApi
inline fun <reified T> Properties.mergeToMap(
    value: T,
    additionalMap: Map<String, Any>?
): Map<String, Any> {
    val map = encodeToMap(value)
    return map + (additionalMap ?: emptyMap())
}
