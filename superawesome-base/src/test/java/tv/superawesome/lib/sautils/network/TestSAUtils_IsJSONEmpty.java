package tv.superawesome.lib.sautils.network;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import tv.superawesome.lib.sautils.SAUtils;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by gabriel.coman on 03/05/2018.
 */

public class TestSAUtils_IsJSONEmpty {

    @Test
    public void testIsJSONEmpty () {
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

        // then
        boolean result1 = SAUtils.isJSONEmpty(given1);
        boolean result2 = SAUtils.isJSONEmpty(given2);
        boolean result3 = SAUtils.isJSONEmpty(given3);

        assertFalse(result1);
        assertTrue(result2);
        assertTrue(result3);
    }
}
