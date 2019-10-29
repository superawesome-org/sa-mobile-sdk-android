package tv.superawesome.lib.sajsonparser;

import org.junit.Test;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONAssert;

/**
 * Created by gabriel.coman on 17/10/16.
 */
public class TestSAJsonParser_ParseArray {

    @Test
    public void test_SAJsonParser_ParseArray_WithJsonStringOfArrayOfNumbers () throws Exception {
        // given
        String given = "[ 23, 5, 2, 8 ]";

        // when
        JSONArray result = SAJsonParser.newArray(given);

        // then
        JSONArray expected = new JSONArray();
        expected.put(23).put(5).put(2).put(8);

        JSONAssert.assertEquals(expected, result, false);
    }

    @Test
    public void test_SAJsonParser_ParseArray_WithJsonStringofArrayOfDifferentObjects () throws Exception {
        // given
        String given = "[ 23, 5, \"papa\", null ]";

        // when
        JSONArray result = SAJsonParser.newArray(given);

        // then
        JSONArray expected = new JSONArray();
        expected.put(23).put(5).put("papa").put(JSONObject.NULL);

        JSONAssert.assertEquals(expected, result, false);
    }

    @Test
    public void test_SAJsonParser_ParseArray_WithJsonStringOfComplexObjects () throws Exception {
        // given
        String given = "[ 23, \"papa\", { \"name\": \"john\", \"age\": 23 }, { \"name\": \"theresa\" } ]";

        // when
        JSONArray result = SAJsonParser.newArray(given);

        // then

        JSONObject e1 = new JSONObject();
        JSONObject e2 = new JSONObject();
        try {
            e1.put("name", "john");
            e1.put("age", 23);
            e2.put("name", "theresa");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray expected = new JSONArray();
        expected.put(23).put("papa").put(e1).put(e2);

        JSONAssert.assertEquals(expected, result, false);
    }

    @Test
    public void test_SAJsonParser_ParseArray_WithEmptyJsonString() throws Exception {
        // given
        String given = "";

        // when
        JSONArray result = SAJsonParser.newArray(given);

        // then
        JSONArray expected = new JSONArray();

        JSONAssert.assertEquals(expected, result, false);
    }

    @Test
    public void test_SAJsonParser_ParseArray_WithNullJsonString () throws Exception {
        // given
        String given = null;

        // when
        JSONArray result = SAJsonParser.newArray(given);

        // then
        JSONArray expected = new JSONArray();
        JSONAssert.assertEquals(expected, result, false);
    }

    @Test
    public void test_SAJsonParser_ParseArray_WithInvalidJsonString () throws Exception {
        // given
        String given1 = "[ 3, 3, \" abc ";
        String given2 = "[ 3, 3, \" abc ";
        String given3 = " 3, 3";

        // when
        JSONArray result1 = SAJsonParser.newArray(given1);
        JSONArray result2 = SAJsonParser.newArray(given2);
        JSONArray result3 = SAJsonParser.newArray(given3);

        // then
        JSONArray expected = new JSONArray();

        JSONAssert.assertEquals(expected, result1, false);
        JSONAssert.assertEquals(expected, result2, false);
        JSONAssert.assertEquals(expected, result3, false);
    }

    @Test
    public void test_SAJsonParser_ParseArray_WithJsonStringOfObjectInsteadOfArray () throws Exception {
        // given
        String given = "{ \"name\": \"John\", \"age\": 35 }";

        // when
        JSONArray result = SAJsonParser.newArray(given);

        // then
        JSONArray expected = new JSONArray();

        JSONAssert.assertEquals(expected, result, false);
    }
}
