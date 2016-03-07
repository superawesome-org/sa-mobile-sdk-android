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

        list.add(AdItem.create("79", 79, false, AdItemType.fullscreen_video_item));
        list.add(AdItem.create("1830", 1830, false, AdItemType.fullscreen_video_item));
        list.add(AdItem.create("2559", 2559, false, AdItemType.interstitial_item));
        list.add(AdItem.create("10305", 10305, false, AdItemType.interstitial_item));
        list.add(AdItem.create("30462", 30462, false, AdItemType.interstitial_item));

        return list;
    }
}
