package tv.superawesome.lib.saevents.mocks.models;

import tv.superawesome.lib.samodelspace.vastad.SAVASTMedia;

/**
 * Created by gabriel.coman on 09/05/2018.
 */

public class MockVastMedia extends SAVASTMedia {

    public MockVastMedia() {
        this.type = "mp4";
        this.url = "http://api.com/resources/video.mp4";
        this.bitrate = 1000;
        this.width = 640;
        this.height = 320;
    }
}
