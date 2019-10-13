package tv.superawesome.lib.saevents.mocks.models;

import java.util.ArrayList;

import tv.superawesome.lib.samodelspace.saad.SACreative;
import tv.superawesome.lib.samodelspace.saad.SACreativeFormat;
import tv.superawesome.lib.samodelspace.saad.SADetails;

/**
 * Created by gabriel.coman on 09/05/2018.
 */

public class MockCreative extends SACreative {

    public MockCreative (SACreativeFormat format, SADetails details) {
        this.id = 5001;
        this.name = "ad-name";
        this.cpm = 3;
        this.format = format;
        this.live = true;
        this.approved = true;
        this.bumper = false;
        this.payload = "";
        this.clickUrl = null;
        this.clickCounterUrl = null;
        this.impressionUrl = null;
        this.osTarget = new ArrayList<>();
        this.bundle = "com.test.myapp";
        this.referral = null;
        this.details = details;
    }
}
