package tv.superawesome.lib.sautils.network;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import tv.superawesome.lib.sautils.SAUtils;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by gabriel.coman on 17/10/16.
 */
public class TestSAUtils_IsValidEmail {

    @Test
    public void testIsValidEmail () {
        // given
        String given1 = "dev.gabriel.coman@gmail.com";
        String given2 = "jsksls////";
        String given3 = null;
        String given4 = "";
        String given5 = "test@test.com";

        // then
        boolean result1 = SAUtils.isValidEmail(given1);
        boolean result2 = SAUtils.isValidEmail(given2);
        boolean result3 = SAUtils.isValidEmail(given3);
        boolean result4 = SAUtils.isValidEmail(given4);
        boolean result5 = SAUtils.isValidEmail(given5);

        assertTrue(result1);
        assertFalse(result2);
        assertFalse(result3);
        assertFalse(result4);
        assertTrue(result5);
    }
}
