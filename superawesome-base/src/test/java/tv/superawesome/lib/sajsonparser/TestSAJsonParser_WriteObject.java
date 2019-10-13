package tv.superawesome.lib.sajsonparser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.Arrays;

import tv.superawesome.lib.sajsonparser.mocks.SAMockCompanyModel;
import tv.superawesome.lib.sajsonparser.mocks.SAMockEmployeeModel;
import tv.superawesome.lib.sajsonparser.mocks.SAMockPositionModel;

/**
 * Created by gabriel.coman on 17/10/16.
 */
public class TestSAJsonParser_WriteObject {

    @Test
    public void test_SAJsonparser_WriteObject_WithSimpleObject () {
        // given
        SAMockPositionModel position = new SAMockPositionModel("CEO", 100000);

        // when
        JSONObject expected = new JSONObject();
        try {
            expected.
                    put("name", "CEO").
                    put("salary", 100000);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // then
        JSONObject result = position.writeToJson();

        try {
            JSONAssert.assertEquals(expected, result, false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_SAJsonparser_WriteObject_WithNestedObject () throws Exception {
        // given
        SAMockPositionModel given1 = new SAMockPositionModel("Junior Engineer", 28000);
        SAMockPositionModel given2 = new SAMockPositionModel("Engineer", 35000);
        SAMockEmployeeModel given = new SAMockEmployeeModel("Jim", 23, true, given2, Arrays.asList(given1));

        // when
        JSONObject ex1 = new JSONObject();
        ex1.put("name", "Junior Engineer").put("salary", 28000);

        JSONObject ex2 = new JSONObject();
        ex2.put("name", "Engineer").put("salary", 35000);

        JSONArray arry = new JSONArray();
        arry.put(ex1);
        JSONObject expected = new JSONObject();
        expected.
                put("name", "Jim").
                put("age", 23).
                put("isActive", true).
                put("position", ex2).
                put("previous", arry);

        // then
        JSONObject result = given.writeToJson();
        JSONAssert.assertEquals(expected, result, false);
    }

    @Test
    public void test_SAJsonparser_WriteObject_WithVeryComplexObject () throws Exception {
        // given
        SAMockPositionModel given00 = new SAMockPositionModel("Intern", 0);
        SAMockPositionModel given11 = new SAMockPositionModel("Junior Engineer", 28000);
        SAMockPositionModel given12 = new SAMockPositionModel("Engineer", 35000);

        SAMockEmployeeModel given1 = new SAMockEmployeeModel("John", 23, true, given11, Arrays.asList(given00));
        SAMockEmployeeModel given2 = new SAMockEmployeeModel("Danna", 18, true, given12, Arrays.asList(given00, given11));

        SAMockCompanyModel given = new SAMockCompanyModel("John Smith Ltd", Arrays.asList(given1, given2));

        // when
        JSONObject ex00 = new JSONObject();
        ex00.put("name", "Intern").put("salary", 0);
        JSONObject ex11 = new JSONObject();
        ex11.put("name", "Junior Engineer").put("salary", 28000);
        JSONObject ex12 = new JSONObject();
        ex12.put("name", "Engineer").put("salary", 35000);

        JSONArray arr11 = new JSONArray().put(ex00);
        JSONArray arr22 = new JSONArray().put(ex00).put(ex11);

        JSONObject ex1 = new JSONObject();
        ex1.put("name", "John").put("age", 23).put("isActive", true).put("position", ex11).put("previous", arr11);

        JSONObject ex2 = new JSONObject();
        ex2.put("name", "Danna").put("age", 18).put("isActive", true).put("position", ex12).put("previous", arr22);


        JSONArray arr33 = new JSONArray().put(ex1).put(ex2);

        JSONObject expected = new JSONObject();
        expected.put("name", "John Smith Ltd").put("employees", arr33);

        // then
        JSONObject result = given.writeToJson();
        JSONAssert.assertEquals(expected, result, false);
    }

    @Test
    public void test_SAJsonparser_WriteObject_WithNulls () throws Exception {
        // given
        SAMockPositionModel position = new SAMockPositionModel("Junior Engineer", 28000);
        SAMockEmployeeModel given = new SAMockEmployeeModel(null, 32, false, null, Arrays.asList(position));

        // then
        JSONObject pos = new JSONObject();
        pos.put("name", "Junior Engineer").put("salary", 28000);

        JSONArray arr = new JSONArray().put(pos);
        JSONObject expected = new JSONObject();

        expected.put("age", 32).put("isActive", false).put("previous", arr);

        // then
        JSONObject result = given.writeToJson();


        JSONAssert.assertEquals(expected, result, false);
    }
}
