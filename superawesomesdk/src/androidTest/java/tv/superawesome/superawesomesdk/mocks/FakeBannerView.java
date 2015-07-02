//package tv.superawesome.superawesomesdk.mocks;
//
//import android.content.Context;
//
//import org.json.JSONObject;
//
//import tv.superawesome.superawesomesdk.SuperAwesome;
//import tv.superawesome.superawesomesdk.view.Ad;
//import tv.superawesome.superawesomesdk.view.BannerView;
//
///**
// * Created by connor.leigh-smith on 01/07/15.
// */
//public class FakeBannerView extends BannerView {
//
//    public FakeBannerView(Context context, String placementID) {
//        super(context, placementID);
//    }
//
//    public void loadAd() {
//        JSONObject json = null;
//        try {
//            if (this.placementID.equals("1")) {
//                json = new JSONObject("{\n" +
//                        "  \"line_item_id\":1,\n" +
//                        "  \"campaign_id\":1,\n" +
//                        "  \"test\":\"false\",\n" +
//                        "  \"creative\":{\n" +
//                        "    \"id\":1,\n" +
//                        "    \"format\":\"image_with_link\",\n" +
//                        "    \"click_url\": \"http://superawesome.tv\",\n" +
//                        "    \"details\": {\n" +
//                        "      \"image\":\"http://www.helpinghomelesscats.com/images/cat1.jpg\",\n" +
//                        "      \"width\":728,\n" +
//                        "      \"height\":90\n" +
//                        "    }\n" +
//                        "  }\n" +
//                        "}");
//            } else if (this.placementID.equals("2")) {
//                json = new JSONObject("{\n" +
//                        "  \"line_item_id\":1,\n" +
//                        "  \"campaign_id\":1,\n" +
//                        "  \"test\":\"false\",\n" +
//                        "  \"creative\":{\n" +
//                        "    \"id\":1,\n" +
//                        "    \"format\":\"rich_media\",\n" +
//                        "    \"details\": {\n" +
//                        "      \"url\":\"https://s3-eu-west-1.amazonaws.com/beta-ads-uploads/rich-media/demo-floor/index.html\",\n" +
//                        "      \"width\":728,\n" +
//                        "      \"height\":90\n" +
//                        "    }\n" +
//                        "  }\n" +
//                        "}");
//            }
//            if (this.adLoaderListener != null && json != null) this.adLoaderListener.onLoaded(new Ad(json));
//        } catch (Exception e) {
//            if (listener != null) listener.onAdError(e.getMessage());
//        }
//
//    }
//}
