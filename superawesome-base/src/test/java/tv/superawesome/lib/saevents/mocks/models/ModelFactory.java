package tv.superawesome.lib.saevents.mocks.models;

import java.util.Map;

import tv.superawesome.lib.sametrics.models.SAPerformanceMetricModel;
import tv.superawesome.lib.sametrics.models.SAPerformanceMetricName;
import tv.superawesome.lib.sametrics.models.SAPerformanceMetricType;
import tv.superawesome.lib.samodelspace.saad.SAAd;
import tv.superawesome.lib.samodelspace.saad.SACreative;
import tv.superawesome.lib.samodelspace.saad.SACreativeFormat;
import tv.superawesome.lib.samodelspace.saad.SADetails;
import tv.superawesome.lib.samodelspace.saad.SAMedia;
import tv.superawesome.lib.samodelspace.vastad.SAVASTAd;

/**
 * Created by gabriel.coman on 09/05/2018.
 */
public class ModelFactory {

    public static SAAd createDisplayAd (int placementId) {
        SAMedia media = new MockMedia(null, "file.png");
        SADetails details = new MockDetails(media);
        SACreative creative = new MockCreative(SACreativeFormat.image, details);
        return new MockAd(placementId, creative);
    }

    public static SAAd createVideoAd (int placementId) {
        SAVASTAd savastAd = new MockVastAd(placementId);
        SAMedia media = new MockMedia(savastAd, "");
        SADetails details = new MockDetails(media);
        SACreative creative = new MockCreative(SACreativeFormat.video, details);
        return new MockAd(placementId, creative);
    }

    public static SAAd createVideoAd (int placementId, Map<String, Object> options) {
        SAVASTAd savastAd = new MockVastAd(placementId);
        SAMedia media = new MockMedia(savastAd, "");
        SADetails details = new MockDetails(media);
        SACreative creative = new MockCreative(SACreativeFormat.video, details);
        return new MockAd(placementId, creative, options);
    }
}
