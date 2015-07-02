//package tv.superawesome.sademoapp;
//
//import android.app.Activity;
//import android.content.Context;
//import android.test.ActivityUnitTestCase;
//import android.test.AndroidTestCase;
//import android.test.UiThreadTest;
//import android.test.mock.MockContext;
//import android.util.Log;
//
//import org.junit.Before;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.invocation.InvocationOnMock;
//import org.mockito.stubbing.Answer;
//
//import java.util.concurrent.CountDownLatch;
//
////import tv.superawesome.superawesomesdk.mocks.FakeBannerView;
//import tv.superawesome.superawesomesdk.AdManager;
//import tv.superawesome.superawesomesdk.view.BannerView;
//import tv.superawesome.superawesomesdk.view.BannerViewListener;
//
////import static com.jayway.awaitility.Awaitility.await;
//import static org.mockito.Mockito.*;
////import com.jayway.awaitility.*;
//
///**
// * Created by connor.leigh-smith on 01/07/15.
// */
//public class BannerViewTest extends AndroidTestCase {
//
//    private CountDownLatch done;
//    private String ApiResponse_1 = "{\"line_item_id\":1, \"campaign_id\":1,\"creative\":{\"id\":1,\"format\":\"rich_media\",\"details\": {\"url\":\"https://s3-eu-west-1.amazonaws.com/beta-ads-uploads/rich-media/demo-floor/index.html\",\"width\":970,\"height\":90}}}";
//
//    BannerViewListener listener;
//
//    BannerView bannerView;
//
//    AdManager adManager = mock(AdManager.class);
//    BannerView bannerView = new BannerView(this.getContext(), "1", adManager);
//
//    @Override
//    @Before
//    protected void setUp() throws Exception {
//        super.setUp();
//        this.listener = mock(BannerViewListener.class);
//    }
//
//
//    @UiThreadTest
//    public void testBannerLoadImage() throws java.lang.InterruptedException{
//        this.bannerView = new BannerView(this.getContext(), "1");
//        this.bannerView.setListener(listener);
//        this.bannerView.loadAd();
//
//    }
//
//    @Override
//    protected void tearDown() throws Exception {
//        super.tearDown();
//    }
//
//}
