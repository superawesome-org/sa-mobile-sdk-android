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

public class TestSAUtils_EncodeDictAsJsonDict {

    @Test
    public void testEncodeDictAsJsonDict () {
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
        String expected1 = "%7B%22client_id%22%3A%22client-111%22%2C%22user_id%22%3A321%7D";
        String expected2 = "%7B%7D";
        String expected3 = "%7B%7D";

        // then
        String result1 = SAUtils.encodeDictAsJsonDict(given1);
        String result2 = SAUtils.encodeDictAsJsonDict(given2);
        String result3 = SAUtils.encodeDictAsJsonDict(given3);

        assertTrue(result1.contains("%22client_id%22%3A%22client-111%22"));
        assertTrue(result1.contains("%22user_id%22%3A321"));
        assertEquals(result1.split("%2C").length, 2);
        assertEquals(result2, expected2);
        assertEquals(result3, expected3);
    }
}
