package tv.superawesome.sdk.parentalgate;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.net.URL;

/**
 * Created by gabriel.coman on 17/09/15.
 */
public class SAParentalGateStandalone {
    public SAParentalGateStandalone() {
        // do nothing
    }

    public  void  createParentalGate(final Context context, final String url) {
        // create new and show
        SAParentalGate gate = new SAParentalGate(context);
        gate.setListener(new SAParentalGateListener() {
            @Override
            public void onPressCancel() {
                // do nothing
            }

            @Override
            public void onPressContinueWithError() {
                // do nothing
            }

            @Override
            public void onPressContinueWithSuccess() {
                // do nothing
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });
        gate.show();
    }
}
