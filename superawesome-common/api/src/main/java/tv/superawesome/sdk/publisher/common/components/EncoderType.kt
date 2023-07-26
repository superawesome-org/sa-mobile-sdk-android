package tv.superawesome.sdk.publisher.common.components

/**
 * Describes an URI encoder.
 */
public interface EncoderType {

    /**
     * Encodes a string into valid URI.
     *
     * @param string a string to be encoded.
     * @return an encoded string.
     */
    public fun encodeUri(string: String?): String

    /**
     * Encodes URL parameters from a given object map.
     *
     * @param map objects to be encoded.
     * @return an encoded string.
     */
    public fun encodeUrlParamsFromObject(map: Map<String, Any?>): String
}
