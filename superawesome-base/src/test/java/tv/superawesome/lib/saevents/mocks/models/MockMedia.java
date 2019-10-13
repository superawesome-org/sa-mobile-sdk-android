package tv.superawesome.lib.saevents.mocks.models;

import tv.superawesome.lib.samodelspace.saad.SAMedia;
import tv.superawesome.lib.samodelspace.vastad.SAVASTAd;

/**
 * Created by gabriel.coman on 09/05/2018.
 */
public class MockMedia extends SAMedia {

    public MockMedia (SAVASTAd savastAd, String media) {
        this.html = "<some></some>";
        this.path = "file://local/file/resource/" + media;
        this.url = "http://localhost:64000/resource/" + media;
        this.type = "mp4";
        this.isDownloaded = true;
        this.vastAd = savastAd;
    }
}
