package tv.superawesome.lib.sautils.aux;

import android.app.Application;
import android.graphics.Rect;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockSettings;
import org.mockito.listeners.InvocationListener;
import org.mockito.listeners.VerificationStartedListener;
import org.mockito.mock.MockCreationSettings;
import org.mockito.mock.SerializableMode;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import tv.superawesome.lib.sautils.SAUtils;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * Created by gabriel.coman on 17/10/16.
 */
public class TestSAUtils_IsTargetRectInFrameRect {

    @Test
    public void testRectInRect () {
//        Rect given1 = mock(Rect.class);
//        given1.set(0, 250, 320, 45);
//        Rect given3 = mock(Rect.class);
//        given3.set(0, 0, 320, 648);
////        // given
////        Rect given1 = new Rect(0, 250, 320, 45);
////        Rect given2 = new Rect(-23, 720, 250, 45);
////        Rect given3 = new Rect(0, 0, 320, 684);
////
////        // then
//        boolean result1 = SAUtils.isTargetRectInFrameRect(given1, given3);
////        boolean result2 = SAUtils.isTargetRectInFrameRect(given2, given3);
//        assertTrue(result1);
////        assertFalse(result2);
    }
}
