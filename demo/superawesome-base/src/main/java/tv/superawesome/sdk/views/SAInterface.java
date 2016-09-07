package tv.superawesome.sdk.views;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by gabriel.coman on 26/12/15.
 */
public interface SAInterface extends Serializable {

    void SADidLoadAd(int placementId);

    void SADidNotLoadAd(int placementId);

    void SADidShowAd();

    void SADidNotShowAd();

    void SADidCloseAd();

    void SADidClickAd();

}
