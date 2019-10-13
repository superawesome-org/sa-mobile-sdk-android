/**
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.lib.sajsonparser;

/**
 * Interface that is used by the JSON parser to define, in place, how a certain JSONArray of
 * objects is going to be translated to a List of objects
 *
 * @param <A> Param A is the type of object that will be returned by the transformation. It could
 *            any type of object, but should mostly be a complex model of sorts.
 * @param <B> Param B is the type of object that is going to be found in the JSONArray. This can
 *            be a string, integer, complex JSONObject, etc
 */
public interface SAJsonToList<A, B> {

    /**
     * The only method this interface has, which needs to be implemented in order to know how
     * to translate param of generic type B to generic type A
     *
     * @param param     the callback parameter
     * @return          the return parameter
     */
    A traverseItem(B param);
}
