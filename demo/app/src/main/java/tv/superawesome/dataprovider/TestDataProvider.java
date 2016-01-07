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
        list.add(AdItem.create("Fullscreen Video - 21022", 21022, false, AdItemType.fullscreen_video_item));
        list.add(AdItem.create("Interstitial - 9549", 9549, false, AdItemType.interstitial_item));
        list.add(AdItem.create("Banner - 9549", 9549, false, AdItemType.banner_item));

        return list;
    }
}
