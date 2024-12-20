package tv.superawesome.lib.sautils.aux;

import static junit.framework.Assert.assertEquals;

import android.graphics.Rect;

import org.junit.Test;

import tv.superawesome.lib.sautils.SAUtils;

/**
 * Created by gabriel.coman on 03/05/2018.
 */

public class SAUtils_MapSourceSizeIntoBoundingSize_Test {

    @Test
    public void testFrameMapping () {
        // given
        Rect oldFrame = new Rect(0, 30, 280, 45);
        Rect newFrame = new Rect(0, 0, 200, 100);

        // when
        Rect expected = new Rect(0, 33, 200, 32);

        // then
        Rect result = SAUtils.mapSourceSizeIntoBoundingSize(newFrame.right, newFrame.bottom, oldFrame.right, oldFrame.bottom);

        assertEquals(result.right, expected.right);
        assertEquals(result.bottom, expected.bottom);
    }
}
