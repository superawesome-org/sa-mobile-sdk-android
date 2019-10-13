package tv.superawesome.lib.sautils.aux;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import tv.superawesome.lib.sautils.SAUtils;

import static junit.framework.Assert.assertTrue;

/**
 * Created by gabriel.coman on 03/05/2018.
 */

public class TestSAUtils_GenerateUniqueKey {

    @Test
    public void testGenerateUniqueKey () throws Exception {
        // given
        int bound = 100;
        List<String> uniqueKeys = new ArrayList<>();
        for (int i = 0; i < bound; i++) {
            uniqueKeys.add(SAUtils.generateUniqueKey());
        }

        // when
        boolean allUniques = true;
        for (int i = 0; i < bound; i++) {
            boolean hasFound = false;

            for (int j = 0; j < bound && j != i; j++) {
                String uniquei = uniqueKeys.get(i);
                String uniquej = uniqueKeys.get(j);
                if (uniquei.equals(uniquej)) {
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
