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
        list.add(AdItem.create("27 - AA - Mobile", 27, false, AdItemType.fullscreen_video_item));
        list.add(AdItem.create("28 - AA - Tablet", 28, false, AdItemType.fullscreen_video_item));

        list.add(AdItem.create("20 - Video for Tablet", 20, false, AdItemType.fullscreen_video_item));
        list.add(AdItem.create("21 - Video for Mobile", 21, false, AdItemType.fullscreen_video_item));
        list.add(AdItem.create("22 - Video for Web only", 22, false, AdItemType.fullscreen_video_item));
        list.add(AdItem.create("23 - Video for all devices", 23, false, AdItemType.fullscreen_video_item));

        list.add(AdItem.create("Fullscreen Video - 21022", 21022, false, AdItemType.fullscreen_video_item));
        list.add(AdItem.create("Interstitial - 9549", 9549, false, AdItemType.interstitial_item));
        list.add(AdItem.create("Banner - 9549", 9549, false, AdItemType.banner_item));
        list.add(AdItem.create("Interstitial - 10213", 10213, false, AdItemType.interstitial_item));
        list.add(AdItem.create("Interstitial - 10198", 10198, false, AdItemType.interstitial_item));
        list.add(AdItem.create("Interstitial - 10324", 10324, false, AdItemType.interstitial_item));
        list.add(AdItem.create("Video - 30288", 30288, false, AdItemType.fullscreen_video_item));
        list.add(AdItem.create("Fungus Amungus - 30302", 30302, false, AdItemType.interstitial_item));
        list.add(AdItem.create("10305", 10305, false, AdItemType.interstitial_item));
        list.add(AdItem.create("25", 25, false, AdItemType.interstitial_item));
        return list;
    }
}
