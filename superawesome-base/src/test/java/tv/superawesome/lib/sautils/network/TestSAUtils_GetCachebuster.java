package tv.superawesome.lib.sautils.network;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import tv.superawesome.lib.sautils.SAUtils;

import static junit.framework.Assert.assertTrue;

/**
 * Created by gabriel.coman on 03/05/2018.
 */

public class TestSAUtils_GetCachebuster {

    @Test
    public void testGetCacheBuster () {
        // given
        int bound = 10;
        List<Integer> uniqueKeys = new ArrayList<>();
        for (int i = 0; i < bound; i++) {
            uniqueKeys.add(SAUtils.getCacheBuster());
        }

        // when
        boolean allUniques = true;
        for (int i = 0; i < bound; i++) {
            boolean hasFound = false;

            for (int j = 0; j < bound && j != i; j++) {
                int uniquei = uniqueKeys.get(i);
                int uniquej = uniqueKeys.get(j);
                if (uniquei == uniquej) {
                    hasFound = true;
                    break;
                }
            }

            if (hasFound) {
                allUniques = false;
                break;
            }
        }

        // then
        assertTrue(allUniques);
    }
}
