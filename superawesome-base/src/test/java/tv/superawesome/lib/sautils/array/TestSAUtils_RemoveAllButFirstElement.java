package tv.superawesome.lib.sautils.array;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tv.superawesome.lib.sautils.SAUtils;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

/**
 * Created by gabriel.coman on 17/10/16.
 */
public class TestSAUtils_RemoveAllButFirstElement {

    @Test
    public void testRemoveAllButFirst1 () {
        // given
        List<String> given = Arrays.asList("one", "two", "three");

        // when
        List<String> expected = new ArrayList<>();
        expected.add(given.get(0));

        // then
        List<String> result = SAUtils.removeAllButFirstElement(given);

        assertEquals(result.size(), expected.size());
        assertEquals(result, expected);
    }

    @Test
    public void testRemoveAllButFirst2 () {
        // given
        List<String> given = new ArrayList<>();

        // when
        List<String> expected = new ArrayList<>();

        // then
        List<String> result = SAUtils.removeAllButFirstElement(given);

        assertEquals(result.size(), expected.size());
        assertEquals(result, expected);
    }

    @Test
    public void testRemoveAllButFirst3 () {
        // given
        List<String> given = null;

        // then
        List<String> result = SAUtils.removeAllButFirstElement(given);

        assertNull(result);
    }
}
