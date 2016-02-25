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
//        list.add(AdItem.create("Image Interstitial - 43", 43, false, AdItemType.interstitial_item));
//        list.add(AdItem.create("Image Banner - 45", 45, false, AdItemType.banner_item));
//        list.add(AdItem.create("Rich Media Interstitial - 44", 44, false, AdItemType.interstitial_item));
//        list.add(AdItem.create("Tablet Preroll - 38", 38, true, AdItemType.fullscreen_video_item));
//        list.add(AdItem.create("Mobile Preroll - 40", 40, true, AdItemType.fullscreen_video_item));
//        list.add(AdItem.create("Rich Media MPU - 46", 46, false, AdItemType.banner_item));
//        list.add(AdItem.create("Third Party Tag - 47", 47, false, AdItemType.interstitial_item));
//        list.add(AdItem.create("Third Party Tag - 76", 76, false, AdItemType.fullscreen_video_item));

//        list.add(AdItem.create("30007", 30007, false, AdItemType.interstitial_item));
//

        list.add(AdItem.create("79", 79, false, AdItemType.fullscreen_video_item));
        list.add(AdItem.create("1830", 1830, false, AdItemType.fullscreen_video_item));
        list.add(AdItem.create("230", 230, false, AdItemType.interstitial_item));
        list.add(AdItem.create("232", 232, false, AdItemType.interstitial_item));

        list.add(AdItem.create("seventy nine", 79, false, AdItemType.fullscreen_video_item));
        list.add(AdItem.create("1830", 1830, false, AdItemType.fullscreen_video_item));
        list.add(AdItem.create("Upsight - Phone Landscape Static - 30171", 30171, false, AdItemType.interstitial_item));

        list.add(AdItem.create("Upsight - Tablet Portrait Static - 30173", 30173, false, AdItemType.interstitial_item));
        list.add(AdItem.create("Upsight - Tablet Landscape Static - 30174", 30174, false, AdItemType.interstitial_item));

        list.add(AdItem.create("28000", 28000, false, AdItemType.fullscreen_video_item));
        list.add(AdItem.create("10305", 10305, false, AdItemType.banner_item));
        list.add(AdItem.create("30468", 30468, false, AdItemType.interstitial_item));
        list.add(AdItem.create("30510", 30510, false, AdItemType.fullscreen_video_item));
        list.add(AdItem.create("30512", 30512, false, AdItemType.fullscreen_video_item));

        return list;
    }
}
