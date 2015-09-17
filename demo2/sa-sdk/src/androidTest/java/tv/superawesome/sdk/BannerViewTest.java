//package tv.superawesome.superawesomesdk;
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
//import tv.superawesome.sdk.mocks.FakeBannerView;
//import tv.superawesome.sdk.view.BannerView;
//import tv.superawesome.sdk.view.BannerViewListener;
//
////import static com.jayway.awaitility.Awaitility.await;
//import static org.mockito.Mockito.*;
////import com.jayway.awaitility.*;
//
///**
// * Created by balazs.kiss on 29/06/2015.
// */
//public class BannerViewTest extends AndroidTestCase {
//
//    private CountDownLatch done;
//    private String ApiResponse_1 = "{\"line_item_id\":1, \"campaign_id\":1,\"creative\":{\"id\":1,\"format\":\"rich_media\",\"details\": {\"url\":\"https://s3-eu-west-1.amazonaws.com/beta-ads-uploads/rich-media/demo-floor/index.html\",\"width\":970,\"height\":90}}}";
////    private String ApiResponse_2 =
//    boolean adLoaded;
//    boolean adErrored;
//
//
//    //This will create a mock of IEventListener
//    @Mock
//    BannerViewListener listener;
//
//    @InjectMocks
//    BannerView bannerView;
//
//    @Override
//    protected void setUp() throws Exception {
//        super.setUp();
//    }
//
//    @Before
//    public void initMocks() {
//        //This will initialize the annotated mocks
//        MockitoAnnotations.initMocks(this);
//    }
//
//
//    @UiThreadTest
//    public void testBannerLoadImage() throws java.lang.InterruptedException{
//        this.bannerView = new FakeBannerView(this.getContext(), "1");
//        this.adLoaded = false;
//        this.adErrored = false;
//        this.listener = new BannerViewListener() {
//            @Override
//            public void onAdLoaded() {
//
//            }
//
//            @Override
//            public void onAdError(String message) {
//
//            }
//        };
////        BannerViewListener listener = new BannerViewListener() {
////            @Override
////            public void onAdLoaded() {
////                adLoaded = true;
////            }
////
////            @Override
////            public void onAdError(String message) {
////                adErrored = true;
////            }
////        };
//        this.bannerView.setListener(listener);
//        this.bannerView.loadAd();
//        verify(listener, times(1)).onAdLoaded();
////        verify(listener, times(0)).onAdError();
////        assertTrue(adLoaded);
////        assertFalse(adErrored);
//
//
//    }
//
//    @Override
//    protected void tearDown() throws Exception {
//        super.tearDown();
//    }
//
//}
