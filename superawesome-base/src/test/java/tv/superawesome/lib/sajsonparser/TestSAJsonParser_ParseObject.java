package tv.superawesome.lib.sajsonparser;

import org.json.JSONObject;
import org.junit.Test;

import java.util.Arrays;

import tv.superawesome.lib.sajsonparser.mocks.SAMockEmployeeModel;
import tv.superawesome.lib.sajsonparser.mocks.SAMockLongHolderModel;
import tv.superawesome.lib.sajsonparser.mocks.SAMockPositionModel;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by gabriel.coman on 17/10/16.
 */
public class TestSAJsonParser_ParseObject {

    @Test
    public void test_SAJsonParser_ParseObject_WithSimpleObjects () {
        // given
        String given0 = "{ \"name\": \"Intern\", \"salary\": 0 }";
        String given1 = "{ \"name\": \"Junior Engineer\", \"salary\": 28000 }";
        String given = "{ \"name\": \"John\", \"age\": 32, \"isActive\": true, \"position\":"+given1+", \"previous\":["+given0+"] }";

        // when
        SAMockPositionModel expected0 = new SAMockPositionModel("Intern", 0);
        SAMockPositionModel expected1 = new SAMockPositionModel("Junior Engineer", 28000);
        SAMockEmployeeModel expected = new SAMockEmployeeModel("John", 32, true, expected1, Arrays.asList(expected0));

        // then
        JSONObject jsonObject = SAJsonParser.newObject(given);
        SAMockEmployeeModel result = new SAMockEmployeeModel(jsonObject);

        // assert
        assertNotNull(jsonObject);
        assertNotNull(result);
        assertEquals(result.name, expected.name);
        assertEquals(result.age, expected.age);
        assertEquals(result.isActive, expected.isActive);
        assertTrue(result.isValid());
        assertNotNull(result.position);
        assertNotNull(result.previous);
        assertEquals(result.previous.size(), 1);
        assertEquals(result.position.name, expected.position.name);
        assertEquals(result.position.salary, expected.position.salary);
    }

    @Test
    public void test_SAJsonParser_ParseObject_WithLongValues () {
        // given
        String given0 = "{ \"val1\": 128, \"val2\": 1478862037 }";

        // when
        SAMockLongHolderModel expected0 = new SAMockLongHolderModel(128, 1478862037);

        // then
        JSONObject jsonObject = SAJsonParser.newObject(given0);
        SAMockLongHolderModel result = new SAMockLongHolderModel(jsonObject);

        // assert
        assertNotNull(jsonObject);
        assertNotNull(result);
        assertEquals(result.val1, expected0.val1);
        assertEquals(result.val2, expected0.val2);
    }

    @Test
    public void test_SAJsonParser_ParseObject_WithNulls () {
        // given
        String given = null;

        // expected
        SAMockPositionModel expected = new SAMockPositionModel();

        // then
        JSONObject jsonObject = SAJsonParser.newObject(given);
        SAMockPositionModel result = new SAMockPositionModel(jsonObject);

        // assert
        assertNotNull(jsonObject);
        assertNotNull(result);
        assertEquals(result.name, expected.name);
        assertEquals(result.salary, expected.salary);
        assertFalse(result.isValid());
    }

    @Test
    public void test_SAJsonParser_ParseObject_WithMissingValues () {
        // given
        String given = "{ \"name\": \"CEO\" }";

        // expected
        SAMockPositionModel expected = new SAMockPositionModel("CEO", 0);

        // then
        JSONObject jsonObject = SAJsonParser.newObject(given);
        SAMockPositionModel result = new SAMockPositionModel(jsonObject);

        // assert
        assertNotNull(jsonObject);
        assertNotNull(result);
        assertEquals(result.name, expected.name);
        assertEquals(result.salary, expected.salary);
        assertTrue(result.isValid());
    }

    @Test
    public void test_SAJsonParser_ParseObject_WithBadJson () {
        // given
        String given1 = "";
        String given2 = "{ \"name: 48 ";
        String given3 = "{ \"name: 33} ";

        // expected

        // then
        JSONObject jsonObject1 = SAJsonParser.newObject(given1);
        JSONObject jsonObject2 = SAJsonParser.newObject(given2);
        JSONObject jsonObject3 = SAJsonParser.newObject(given3);

        SAMockPositionModel expected1 = new SAMockPositionModel(jsonObject1);
        SAMockPositionModel expected2 = new SAMockPositionModel(jsonObject2);
        SAMockPositionModel expected3 = new SAMockPositionModel(jsonObject3);

        // assert
        assertNotNull(jsonObject1);
        assertNotNull(jsonObject2);
        assertNotNull(jsonObject3);
        assertNotNull(expected1);
        assertNotNull(expected2);
        assertNotNull(expected3);
        assertFalse(expected1.isValid());
        assertFalse(expected2.isValid());
        assertFalse(expected3.isValid());
    }
}
