package tv.superawesome.lib.sajsonparser;

import org.json.JSONObject;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class TestSAJsonParser_WriteDictionary {

    @Test
    public void test_SAJsonParser_WriteDictionary_WithEmptyObjects () {

        JSONObject given1 = SAJsonParser.newObject();
        JSONObject given2 = SAJsonParser.newObject("key");
        JSONObject given3 = SAJsonParser.newObject((Object[])null);
        JSONObject given4 = SAJsonParser.newObject("key", null);

        // expected
        String expected = "{}";
        String expected2 = "{\"key\":null}";

        // then
        String result1 = given1.toString();
        String result2 = given2.toString();
        String result3 = given3.toString();
        String result4 = given4.toString();

        assertEquals(result1, expected);
        assertEquals(result2, expected);
        assertEquals(result3, expected);
        assertEquals(result4, expected);

    }

    @Test
    public void test_SAJsonParser_WriteDictionary_WithSimpleObject () {

        JSONObject given = SAJsonParser.newObject("given", 23);

        // expected
        String expected = "{\"given\":23}";

        // then
        String result = given.toString();

        assertEquals(result, expected);
    }

    @Test
    public void test_SAJsonParser_WriteDictionary_WithMoreComplexObject () throws Exception {
        // given
        JSONObject given = SAJsonParser.newObject(
                "given", 23,
                "name", "John",
                "isOK", true);

        // expected
        String expected = "{\"isOK\":true,\"given\":23,\"name\":\"John\"}";

        // then
        String result = given.toString();

        JSONObject expectedJson = new JSONObject(expected);
        JSONObject resultJson = new JSONObject(result);
        assertNotNull(expectedJson);
        assertNotNull(resultJson);
        JSONAssert.assertEquals(expectedJson, resultJson, false);
    }

    @Test
    public void test_SAJsonParser_WriteDictionary_WithComplexObjectContainingNulls () throws Exception{
        // given
        JSONObject given = SAJsonParser.newObject(
                "given", 23,
                "name", null,
                "value", 3.5f,
                "isOK", true);

        // expected
        String expected = "{\"isOK\":true,\"value\":3.5,\"given\":23}";

        // then
        String result = given.toString();

        JSONObject expectedJson = new JSONObject(expected);
        JSONObject resultJson = new JSONObject(result);
        assertNotNull(expectedJson);
        assertNotNull(resultJson);
        JSONAssert.assertEquals(expectedJson, resultJson, false);
    }

    @Test
    public void test_SAJsonParser_WriteDictionary_WithNestedObjects () throws Exception {
        // given
        JSONObject given = SAJsonParser.newObject(
                "field", 33,
                "name", "Smith",
                "school", SAJsonParser.newObject(
                        "name", "St. Mary",
                        "start", 2008,
                        "end", 2010));

        // expected
        String expected = "{\"field\":33,\"school\":{\"start\":2008,\"end\":2010,\"name\":\"St. Mary\"},\"name\":\"Smith\"}";

        // then
        String result = given.toString();

        JSONObject expectedJson = new JSONObject(expected);
        JSONObject resultJson = new JSONObject(result);
        assertNotNull(expectedJson);
        assertNotNull(resultJson);
        JSONAssert.assertEquals(expectedJson, resultJson, false);
    }

    @Test
    public void test_SAJsonParser_WriteDictionary_WithObjectContainingArray () throws Exception {
        // given
        JSONObject given = SAJsonParser.newObject(
                "name", "Smith",
                "grades", SAJsonParser.newArray(6, 7, 8, "pass"));

        // expected
        String expected = "{\"grades\":[6,7,8,\"pass\"],\"name\":\"Smith\"}";

        // then
        String result = given.toString();

        JSONObject expectedJson = new JSONObject(expected);
        JSONObject resultJson = new JSONObject(result);
        assertNotNull(expectedJson);
        assertNotNull(resultJson);
        JSONAssert.assertEquals(expectedJson, resultJson, false);
    }

    @Test
    public void test_SAJsonParser_WriteDictionary_WithObjectContainingArrayWithNulls () throws Exception {
        // given
        JSONObject given = SAJsonParser.newObject(
                "name", "Smith",
                "grades", SAJsonParser.newArray(6, 7, null, "pass"));

        // expected
        String expected = "{\"grades\":[6,7,null,\"pass\"],\"name\":\"Smith\"}";

        // then
        String result = given.toString();

        JSONObject expectedJson = new JSONObject(expected);
        JSONObject resultJson = new JSONObject(result);
        assertNotNull(expectedJson);
        assertNotNull(resultJson);
        JSONAssert.assertEquals(expectedJson, resultJson, false);
    }

    @Test
    public void test_SAJsonParser_WriteDictionary_WithInvalidFormattedInput () throws Exception {
        // given
        JSONObject given = SAJsonParser.newObject("name", "Smith", "age");

        // expected
        String expected = "{}";

        // then
        String result = given.toString();

        JSONObject expectedJson = new JSONObject(expected);
        JSONObject resultJson = new JSONObject(result);
        assertNotNull(expectedJson);
        assertNotNull(resultJson);
        JSONAssert.assertEquals(expectedJson, resultJson, false);
    }

    @Test
    public void test_SAJsonParser_WriteDictionary_WithNullInput () throws Exception {
        // given
        JSONObject given1 = SAJsonParser.newObject((String) null);
        JSONObject given2 = SAJsonParser.newObject((Object[]) null);

        // expected
        String expected = "{}";

        // then
        String result1 = given1.toString();
        String result2 = given2.toString();

        JSONObject expectedJson = new JSONObject(expected);
        JSONObject resultJson1 = new JSONObject(result1);
        JSONObject resultJson2 = new JSONObject(result2);
        assertNotNull(expectedJson);
        assertNotNull(resultJson1);
        assertNotNull(resultJson2);
        JSONAssert.assertEquals(expectedJson, resultJson1, false);
        JSONAssert.assertEquals(expectedJson, resultJson2, false);
    }
}