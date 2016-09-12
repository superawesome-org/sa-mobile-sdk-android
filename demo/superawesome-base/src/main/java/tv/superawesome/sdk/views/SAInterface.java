package tv.superawesome.sdk.views;

import java.io.Serializable;

/**
 * Created by gabriel.coman on 26/12/15.
 */
public interface SAInterface extends Serializable {
    void onEvent(int placementId, SAEvent event);
}
