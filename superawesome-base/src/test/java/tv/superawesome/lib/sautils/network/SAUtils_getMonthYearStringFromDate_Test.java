package tv.superawesome.lib.sautils.network;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import tv.superawesome.lib.sautils.SAUtils;

/**
 * Created by gabriel.coman on 17/10/16.
 */
public class SAUtils_getMonthYearStringFromDate_Test {

    @Test
    public void testGetMonthYearStringFromDate() throws ParseException {
        // given
        String sDate1 = "28/07/2020";
        Date given1 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);

        // then
        String result1 = SAUtils.getMonthYearStringFromDate(given1);

        assertEquals(result1, "072020");
    }
}
