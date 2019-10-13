package tv.superawesome.lib.saevents.mocks.models;

import tv.superawesome.lib.samodelspace.saad.SADetails;
import tv.superawesome.lib.samodelspace.saad.SAMedia;

/**
 * Created by gabriel.coman on 09/05/2018.
 */

public class MockDetails extends SADetails {

    public MockDetails (SAMedia media) {
        this.width = 320;
        this.height = 50;
        this.name = "details";
        this.format = "image";
        this.bitrate = 0;
        this.duration = 0;
        this.value = 0;
        this.image = "some-image";
        this.video = "some-video";
        this.tag = null;
        this.zip = null;
        this.url = null;
        this.cdn = null;
        this.base = null;
        this.vast = null;
        this.media = media;
    }
}
