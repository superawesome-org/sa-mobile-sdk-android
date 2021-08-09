package tv.superawesome.sdk.publisher;

import android.widget.RelativeLayout;

import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;

import tv.superawesome.sdk.publisher.test.SingleTestActivity;

public class BaseInstrumentationTest {

    /**
     * The activity to load for the test
     */
    private final ActivityTestRule testActivityRule = new ActivityTestRule(SingleTestActivity.class, true, true);

    /**
     * @return an instance of a test activity rule
     */
    @Rule
    public ActivityTestRule rule() {
        return testActivityRule;
    }

    private static final int CONTENT_ID  = 0x123;
    protected RelativeLayout content;

    @Before
    public void setUp() {
        content = new RelativeLayout(testActivityRule.getActivity());
        content.setId(CONTENT_ID);
        int size = RelativeLayout.LayoutParams.MATCH_PARENT;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(size, size);
        content.setLayoutParams(params);
    }
}
