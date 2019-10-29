/**
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.lib.sajsonparser;

/**
 * Interface that is used by the JSON parser to define, in place, how a certain List of
 * objects is going to be translated to a JSONArray object
 *
 * @param <A> Param A is the type of object that will be included in the JSONArray object
 * @param <B> Param B is the type of object that is trying to be transformed into the JSONArray
 */
public interface SAListToJson <A, B> {

    /**
     * The only method this interface has, which needs to be implemented in order to know how
     * to translate param of generic type B to generic type A
     *
     * @param param     the callback parameter
     * @return          the return parameter
     */
    A traverseItem(B param);
}
