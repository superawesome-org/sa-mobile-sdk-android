package tv.superawesome.lib.sajsonparser;

import org.json.JSONArray;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by gabriel.coman on 17/10/16.
 */
public class TestSAJsonParser_WriteArray {

    @Test
    public void test_SAJsonParser_WriteArray_WithSimpleData () throws Exception {
        // when
        JSONArray result = SAJsonParser.newArray(3, 2, "name", null);

        // then
        String expected = "[3,2,\"name\",null]";

        JSONArray expectedArray = new JSONArray(expected);
        JSONArray resultArray = new JSONArray(result.toString());
        assertNotNull(expectedArray);
        assertNotNull(resultArray);
        JSONAssert.assertEquals(expectedArray, resultArray, false);
    }

    @Test
    public void test_SAJsonParser_WriteArray_WithCompleData () throws Exception {
        // when
        JSONArray result = SAJsonParser.newArray(
                3,
                "name",
                SAJsonParser.newObject(
                        "name", "John",
                        "age", 32),
                SAJsonParser.newObject(
                        "name", "mary",
                        "age", null));

        // then
        String expected = "[3,\"name\",{\"age\":32,\"name\":\"John\"},{\"name\":\"mary\"}]";

        JSONArray expectedArray = new JSONArray(expected);
        JSONArray resultArray = new JSONArray(result.toString());
        assertNotNull(expectedArray);
        assertNotNull(resultArray);
        JSONAssert.assertEquals(expectedArray, resultArray, false);
    }

    @Test
    public void test_SAJsonParser_WriteArray_WithNullString () throws Exception {
        // when
        JSONArray result = SAJsonParser.newArray((String)null);

        // then
        String expected = "[]";

        JSONArray expectedArray = new JSONArray(expected);
        JSONArray resultArray1 = new JSONArray(result.toString());
        assertNotNull(expectedArray);
        assertNotNull(resultArray1);
        JSONAssert.assertEquals(expectedArray, resultArray1, false);

    }

    @Test
    public void test_SAJsonParser_WriteArray_WithNullObject () throws Exception {
        // when
        JSONArray result = SAJsonParser.newArray((Object[]) null);

        // then
        String expected = "[]";

        JSONArray expectedArray = new JSONArray(expected);
        JSONArray resultArray2 = new JSONArray(result.toString());
        assertNotNull(resultArray2);
        JSONAssert.assertEquals(expectedArray, resultArray2, false);
    }
}
