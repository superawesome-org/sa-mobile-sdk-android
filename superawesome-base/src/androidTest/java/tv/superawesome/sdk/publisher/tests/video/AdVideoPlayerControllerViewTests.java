package tv.superawesome.sdk.publisher.tests.video;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isSelected;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;

import android.view.ViewGroup;
import android.widget.RelativeLayout;

import org.junit.Before;
import org.junit.Test;

import tv.superawesome.sdk.publisher.BaseInstrumentationTest;
import tv.superawesome.sdk.publisher.spies.OnClickListenerSpy;
import tv.superawesome.sdk.publisher.video.AdVideoPlayerControllerView;

public class AdVideoPlayerControllerViewTests extends BaseInstrumentationTest {

    private final OnClickListenerSpy spy = new OnClickListenerSpy();
    private AdVideoPlayerControllerView controller;

    @Before
    @Override
    public void setUp() {
        super.setUp();

        /*
         * Create controller
         */
        controller = new AdVideoPlayerControllerView(rule().getActivity());
        int size = ViewGroup.LayoutParams.MATCH_PARENT;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(size, size);
        controller.setLayoutParams(params);
        controller.setClickListener(spy);

        /*
         * Setup view hierarchy
         */
        rule().getActivity().runOnUiThread(() -> {
            rule().getActivity().setContentView(content);
            content.addView(controller);
        });
    }

    @Test
    public void chrome_ToHave_Correct_DefaultState() {
        onView(withId(AdVideoPlayerControllerView.CRONO_BG_ID))
                .check(matches(isDisplayed()));

        onView(withId(AdVideoPlayerControllerView.CRONO_ID))
                .check(matches(isDisplayed()))
                .check(matches(withText("Ad: 0")));

        onView(withId(AdVideoPlayerControllerView.MASK_ID))
                .check(matches(isDisplayed()));

        onView(withId(AdVideoPlayerControllerView.SHOW_MORE_ID))
                .check(matches(isDisplayed()))
                .check(matches(withText("")))
                .check(matches(isEnabled()))
                .check(matches(not(isSelected())));

        onView(withId(AdVideoPlayerControllerView.SMALL_SHOW_MORE_ID))
                .check(matches(not(isDisplayed())));

        onView(withId(AdVideoPlayerControllerView.PADLOCK_ID))
                .check(matches(isDisplayed()));
    }

    @Test
    public void chrome_ToHave_Correct_SmallClickState() {
        rule().getActivity().runOnUiThread(() -> controller.setShouldShowSmallClickButton(true));

        onView(withId(AdVideoPlayerControllerView.CRONO_BG_ID))
                .check(matches(isDisplayed()));

        onView(withId(AdVideoPlayerControllerView.CRONO_ID))
                .check(matches(isDisplayed()))
                .check(matches(withText("Ad: 0")));

        onView(withId(AdVideoPlayerControllerView.MASK_ID))
                .check(matches(isDisplayed()));

        onView(withId(AdVideoPlayerControllerView.SHOW_MORE_ID))
                .check(matches(not(isDisplayed())));

        onView(withId(AdVideoPlayerControllerView.SMALL_SHOW_MORE_ID))
                .check(matches(isDisplayed()))
                .check(matches(withText("Find out more »")))
                .check(matches(isEnabled()))
                .check(matches(not(isSelected())));

        onView(withId(AdVideoPlayerControllerView.PADLOCK_ID))
                .check(matches(isDisplayed()));
    }

    @Test
    public void chrome_ToNot_DisplayPadlock_IfExplicitlyDisabled() {

        rule().getActivity().runOnUiThread(() -> controller.shouldShowPadlock(false));

        onView(withId(AdVideoPlayerControllerView.PADLOCK_ID))
                .check(matches(not(isDisplayed())));
    }

    @Test
    public void chrome_ToUpdate_TimeElements_Accordingly() {
        rule().getActivity().runOnUiThread(() -> controller.setTime(15000, 30000));

        onView(withId(AdVideoPlayerControllerView.CRONO_ID))
                .check(matches(isDisplayed()))
                .check(matches(withText("Ad: 15")));
    }

    @Test
    public void chrome_ToRegister_Clicks_ViaDelegation() {
        // when
        onView(withId(AdVideoPlayerControllerView.SHOW_MORE_ID))
                .perform(click())
                .perform(click());

        // then
        int noClicks = spy.getNoClicks();
        assertEquals(2, noClicks);
    }
}
