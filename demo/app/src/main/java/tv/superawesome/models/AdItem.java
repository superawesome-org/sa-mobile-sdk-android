package tv.superawesome.models;

/**
 * Created by gabriel.coman on 07/01/16.
 */
public class AdItem {
    /**
     * Member vars for the Ad Item
     */
    public String title;
    public int placementId;
    public boolean testEnabled;
    public AdItemType type;

    /**
     * Static create function
     * @param title
     * @param placementId
     * @param testEnabled
     * @param type
     * @return
     */
    public static AdItem create(String title, int placementId, boolean testEnabled, AdItemType type){
        AdItem item = new AdItem();
        item.title = title;
        item.placementId = placementId;
        item.testEnabled = testEnabled;
        item.type = type;
        return item;
    }
}
