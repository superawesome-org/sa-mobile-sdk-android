package tv.superawesome.lib.sautils.network;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import tv.superawesome.lib.sautils.SAUtils;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created by gabriel.coman on 03/05/2018.
 */

public class TestSAUtils_FormGetQueryFromDict {

    @Test
    public void testFormGetQueryFromDict () {
        // given
        JSONObject given1 = new JSONObject();
        try {
            given1.put("client_id", "client-111");
            given1.put("user_id", 321);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject given2 = new JSONObject();
        JSONObject given3 = null;

        // when
        String expected1 = "client_id=client-111&user_id=321";
        String expected2 = "";
        String expected3 = "";

        // then
        String result1 = SAUtils.formGetQueryFromDict(given1);
        String result2 = SAUtils.formGetQueryFromDict(given2);
        String result3 = SAUtils.formGetQueryFromDict(given3);

        assertTrue(result1.contains("client_id=client-11"));
        assertTrue(result1.contains("user_id=321"));
        assertEquals(result1.split("&").length, 2);
        assertEquals(result2, expected2);
        assertEquals(result3, expected3);

    }
}
