package tv.superawesome.lib.saevents.mocks.models;

import java.util.Map;

import tv.superawesome.lib.samodelspace.saad.SAAd;
import tv.superawesome.lib.samodelspace.saad.SACreative;
import tv.superawesome.lib.sasession.defines.SAConfiguration;

/**
 * Created by gabriel.coman on 09/05/2018.
 */

public class MockAd extends SAAd {

    public MockAd(int placementId, SACreative creative) {
        this.error = 0;
        this.advertiserId = 1001;
        this.publisherId = 501;
        this.appId = 10;
        this.lineItemId = 2001;
        this.campaignId = 3001;
        this.placementId = placementId;
        this.configuration = SAConfiguration.PRODUCTION.getValue();
        this.isTest = false;
        this.isFallback = false;
        this.isFill = false;
        this.isHouse = false;
        this.isSafeAdApproved = true;
        this.isPadlockVisible = true;
        this.device = "phone";
        this.loadTime = 0;
        this.creative = creative;
    }

    public MockAd(int placementId, SACreative creative, String adRequestId) {
        this(placementId, creative);
        this.adRequestId = adRequestId;
    }

    public MockAd(int placementId, SACreative creative, Map<String, Object> requestOptions) {
        this(placementId, creative);
        this.requestOptions = requestOptions;
    }
}
