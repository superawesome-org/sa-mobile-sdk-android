/**
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.sdk.cpi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import tv.superawesome.lib.sasession.SASession;

public class SACPI extends BroadcastReceiver {

    public void sendInstallEvent (Context context, final SASession session, final SAInstallEventInterface listener) {
        SASourceBundleInspector inspector = new SASourceBundleInspector(context);
        final SAInstallEvent installEvent = new SAInstallEvent(context);

        inspector.checkPackage(session, new SASourceBundleInspectorInterface() {
            @Override
            public void didFindPackage(String sourceBundle) {
                installEvent.sendEvent(session, sourceBundle, listener);
            }
        });
    }

    @Override public void onReceive(Context context, Intent intent) {

        SAReferralEvent referralEvent = new SAReferralEvent(context, intent);
        referralEvent.sendEvent();
    }
}
