package tv.superawesome.lib.saevents.mocks.models;

import tv.superawesome.lib.samodelspace.vastad.SAVASTEvent;

/**
 * Created by gabriel.coman on 09/05/2018.
 */

public class MockVastEvent extends SAVASTEvent {

    public MockVastEvent (String event, int placementId) {
        this.event = event;
        this.URL = "http://localhost:64000/vast/event/" + event + "?placement="+ placementId;
    }
}
