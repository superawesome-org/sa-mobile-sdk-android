package tv.superawesome.lib.saevents.mocks.models;

import java.util.ArrayList;

import tv.superawesome.lib.samodelspace.vastad.SAVASTAd;
import tv.superawesome.lib.samodelspace.vastad.SAVASTAdType;

/**
 * Created by gabriel.coman on 09/05/2018.
 */
public class MockVastAd extends SAVASTAd {

    public MockVastAd (int placementId) {
        this.redirect = null;
        this.type = SAVASTAdType.InLine;
        this.url = "http://some/url";
        this.media = new ArrayList<>();
        this.media.add(new MockVastMedia());
        this.events = new ArrayList<>();
        this.events.add(new MockVastEvent("vast_impression", placementId));
        this.events.add(new MockVastEvent("vast_click_through", placementId));
        this.events.add(new MockVastEvent("vast_creativeView", placementId));
        this.events.add(new MockVastEvent("vast_start", placementId));
        this.events.add(new MockVastEvent("vast_firstQuartile", placementId));
        this.events.add(new MockVastEvent("vast_midpoint", placementId));
        this.events.add(new MockVastEvent("vast_thirdQuartile", placementId));
        this.events.add(new MockVastEvent("vast_complete", placementId));
        this.events.add(new MockVastEvent("vast_click_tracking", placementId));
        this.events.add(new MockVastEvent("vast_error", placementId));
    }
}
