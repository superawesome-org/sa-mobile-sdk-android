package tv.superawesome.sdk.publisher.tests.video;

import android.view.ViewGroup;
import android.widget.RelativeLayout;

import org.junit.Before;
import org.junit.Test;

import tv.superawesome.sdk.publisher.BaseInstrumentationTest;
import tv.superawesome.sdk.publisher.spies.OnClickListenerSpy;
import tv.superawesome.sdk.publisher.video.SAChromeControl;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.isSelected;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;

public class SAChromeControlTests extends BaseInstrumentationTest {

    private OnClickListenerSpy spy = new OnClickListenerSpy();
    private SAChromeControl controller;

    @Before
    @Override
    public void setUp() {
        super.setUp();

        /*
         * Create controller
         */
        controller = new SAChromeControl(rule().getActivity());
        int size = ViewGroup.LayoutParams.MATCH_PARENT;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(size, size);
        controller.setLayoutParams(params);
        controller.setClickListener(spy);

        /*
         * Setup view hierarchy
         */
        rule().getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                rule().getActivity().setContentView(content);
                content.addView(controller);
            }
        });
    }

    @Test
    public void chrome_ToHave_Correct_DefaultState() {
        onView(withId(SAChromeControl.CRONO_BG_ID))
                .check(matches(isDisplayed()));

        onView(withId(SAChromeControl.CRONO_ID))
                .check(matches(isDisplayed()))
                .check(matches(withText("Ad: 0")));

        onView(withId(SAChromeControl.MASK_ID))
                .check(matches(isDisplayed()));

        onView(withId(SAChromeControl.SHOW_MORE_ID))
                .check(matches(isDisplayed()))
                .check(matches(withText("")))
                .check(matches(isEnabled()))
                .check(matches(not(isSelected())));

        onView(withId(SAChromeControl.SMALL_SHOW_MORE_ID))
                .check(matches(not(isDisplayed())));

        onView(withId(SAChromeControl.PADLOCK_ID))
                .check(matches(isDisplayed()));
    }

    @Test
    public void chrome_ToHave_Correct_SmallClickState() {
        rule().getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                controller.setShouldShowSmallClickButton(true);
            }
        });

        onView(withId(SAChromeControl.CRONO_BG_ID))
                .check(matches(isDisplayed()));

        onView(withId(SAChromeControl.CRONO_ID))
                .check(matches(isDisplayed()))
                .check(matches(withText("Ad: 0")));

        onView(withId(SAChromeControl.MASK_ID))
                .check(matches(isDisplayed()));

        onView(withId(SAChromeControl.SHOW_MORE_ID))
                .check(matches(not(isDisplayed())));

        onView(withId(SAChromeControl.SMALL_SHOW_MORE_ID))
                .check(matches(isDisplayed()))
                .check(matches(withText("Find out more »")))
                .check(matches(isEnabled()))
                .check(matches(not(isSelected())));

        onView(withId(SAChromeControl.PADLOCK_ID))
                .check(matches(isDisplayed()));
    }

    @Test
    public void chrome_ToNot_DisplayPadlock_IfExplicitlyDisabled() {

        rule().getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                controller.shouldShowPadlock(false);
            }
        });

        onView(withId(SAChromeControl.PADLOCK_ID))
                .check(matches(not(isDisplayed())));
    }

    @Test
    public void chrome_ToUpdate_TimeElements_Accordingly() {
        rule().getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                controller.setTime(15000, 30000);
            }
        });

        onView(withId(SAChromeControl.CRONO_ID))
                .check(matches(isDisplayed()))
                .check(matches(withText("Ad: 15")));
    }

    @Test
    public void chrome_ToRegister_Clicks_ViaDelegation() {
        // when
        onView(withId(SAChromeControl.SHOW_MORE_ID))
                .perform(click())
                .perform(click());

        // then
        int noClicks = spy.getNoClicks();
        assertEquals(2, noClicks);
    }
}
