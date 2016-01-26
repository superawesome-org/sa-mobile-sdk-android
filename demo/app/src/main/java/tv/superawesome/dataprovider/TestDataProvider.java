package tv.superawesome.dataprovider;

import java.util.ArrayList;
import java.util.List;

import tv.superawesome.models.AdItem;
import tv.superawesome.models.AdItemType;

/**
 * Created by gabriel.coman on 07/01/16.
 */
public class TestDataProvider {

    /**
     * Creates the test data for the demo app
     * @return
     */
    public static List<AdItem> createTestData() {
        List<AdItem> list = new ArrayList<>();

        /** actually add the test data */
        list.add(AdItem.create("Image Interstitial - 43", 43, false, AdItemType.interstitial_item));
        list.add(AdItem.create("Image Banner - 45", 45, false, AdItemType.banner_item));
        list.add(AdItem.create("Rich Media Interstitial - 44", 44, false, AdItemType.interstitial_item));
        list.add(AdItem.create("Tablet Preroll - 38", 38, false, AdItemType.fullscreen_video_item));
        list.add(AdItem.create("Mobile Preroll - 40", 40, false, AdItemType.fullscreen_video_item));
        list.add(AdItem.create("Rich Media MPU - 46", 46, false, AdItemType.banner_item));
        list.add(AdItem.create("Third Party Tag - 47", 47, false, AdItemType.interstitial_item));

//        list.add(AdItem.create("Fullscreen Video - 21022", 21022, false, AdItemType.fullscreen_video_item));
//        list.add(AdItem.create("Interstitial - 9549", 9549, false, AdItemType.interstitial_item));
//        list.add(AdItem.create("Banner - 9549", 9549, false, AdItemType.banner_item));
//        list.add(AdItem.create("Interstitial - 10213", 10213, false, AdItemType.interstitial_item));
//        list.add(AdItem.create("Interstitial - 10198", 10198, false, AdItemType.interstitial_item));
//        list.add(AdItem.create("Interstitial - 10324", 10324, false, AdItemType.interstitial_item));
//        list.add(AdItem.create("Video - 28000", 28000, false, AdItemType.fullscreen_video_item));
//        list.add(AdItem.create("Fungus Amungus - 30302", 30302, false, AdItemType.interstitial_item));
//        list.add(AdItem.create("10305", 10305, false, AdItemType.interstitial_item));
//        list.add(AdItem.create("37", 37, false, AdItemType.interstitial_item));
//        list.add(AdItem.create("30324", 30324, false, AdItemType.interstitial_item));
        return list;
    }
}
