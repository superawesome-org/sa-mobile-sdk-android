package tv.superawesome.lib.sautils.aux;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import tv.superawesome.lib.sautils.SAUtils;

/**
 * Created by gabriel.coman on 03/05/2018.
 */

public class TestSAUtils_RandomNumberBetween {

    @Test
    public void testRandomNumber () throws Exception {
        // arrange
        int lower = 0;
        int upper = 58;

        // act
        int result = SAUtils.randomNumberBetween(lower, upper);

        // assert
        assertTrue(result <= upper);
        assertTrue( result >= lower);
    }
}
