/**
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.lib.sasession.capper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import tv.superawesome.lib.sautils.SAUtils;

/**
 * Class that abstracts away generating a distinct ID called "DAU ID", which consists of:
 *  - the Advertising ID int
 *  - a random ID
 *  - the package name
 * each hashed and then XOR-ed together
 */
public class SACapper implements ISACapper {


    // constants
    private static final String GOOGLE_ADVERTISING_CLASS = "com.google.android.gms.ads.identifier.AdvertisingIdClient";
    private static final String GOOGLE_ADVERTISING_ID_CLASS = "com.google.android.gms.ads.identifier.AdvertisingIdClient$Info";
    private static final String GOOGLE_ADVERTISING_INFO_METHOD = "getAdvertisingIdInfo";
    private static final String GOOGLE_ADVERTISING_TRACKING_METHOD = "isLimitAdTrackingEnabled";
    private static final String GOOGLE_ADVERTISING_ID_METHOD = "getId";
    private static final String SUPER_AWESOME_FIRST_PART_DAU = "SUPER_AWESOME_FIRST_PART_DAU";

    // private current context & executor
    private Executor executor = null;
    private Context context = null;

    /**
     * Main constructor for the capper, which takes the current context as paramter
     *
     * @param context the current context (activity or fragment)
     */
    public SACapper (Context context) {
        this(context, Executors.newSingleThreadExecutor());
    }

    /**
     * Constructor with executor
     * @param context the current context (activity or fragment)
     * @param executor an injected executor
     */
    public SACapper (Context context, Executor executor) {
        this.context = context;
        this.executor = executor;
    }

    /**
     * Main capper method that takes an SACapperInterface interface instance as parameter, to be
     * able to sent back the generated ID when the async operation finishes
     *
     * @param listener an instance of the SACapperInterface
     */
    @Override
    public void getDauID(final SACapperInterface listener) {

        // guard against this class not being available or th context being null
        if (!SAUtils.isClassAvailable(GOOGLE_ADVERTISING_CLASS) || context == null) {
            sendBackMessage(listener, 0);
            return;
        }

        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {

                    Class<?> advertisingIdClass = Class.forName(GOOGLE_ADVERTISING_CLASS);
                    java.lang.reflect.Method getAdvertisingIdInfo = advertisingIdClass.getMethod(GOOGLE_ADVERTISING_INFO_METHOD, Context.class);
                    Object adInfo = getAdvertisingIdInfo.invoke(advertisingIdClass, context);

                    Class<?> advertisingIdInfoClass = Class.forName(GOOGLE_ADVERTISING_ID_CLASS);

                    java.lang.reflect.Method isLimitAdTrackingEnabled = advertisingIdInfoClass.getMethod(GOOGLE_ADVERTISING_TRACKING_METHOD);
                    java.lang.reflect.Method getId = advertisingIdInfoClass.getMethod(GOOGLE_ADVERTISING_ID_METHOD);

                    Boolean isEnabled = (Boolean) isLimitAdTrackingEnabled.invoke(adInfo);

                    String firstPartOfDAU = !isEnabled ? (String) getId.invoke(adInfo) : "";

                    if (firstPartOfDAU != null && !firstPartOfDAU.isEmpty()) {

                        // continue as if user has Ad Tracking enabled and all
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

                        // get the second part of the DAU ID
                        String secondPartOfDAU = preferences.getString(SUPER_AWESOME_FIRST_PART_DAU, null);

                        // if the second part of the DAU ID is empty then generate & save a new one
                        if (secondPartOfDAU == null || secondPartOfDAU.isEmpty()) {
                            secondPartOfDAU = SAUtils.generateUniqueKey();
                            preferences.edit().putString(SUPER_AWESOME_FIRST_PART_DAU, secondPartOfDAU).apply();
                        }

                        // form the third part of the DAU ID as the package name
                        String thirdPartOfDau = context != null ? context.getPackageName() : "unknown";

                        // generate three hashes for the three strings
                        int hash1 = Math.abs(firstPartOfDAU.hashCode());
                        int hash2 = Math.abs(secondPartOfDAU.hashCode());
                        int hash3 = Math.abs(thirdPartOfDau.hashCode());
                        // and do a XOR on them
                        int dauID = Math.abs(hash1 ^ hash2 ^ hash3);

                        // finally call the listener to sent the DAU ID
                        sendBackMessage(listener, dauID);
                    }
                    // either the service is not available or the user does not have Google Play Services
                    else {
                        sendBackMessage(listener, 0);
                    }

                } catch (Exception e) {
                    sendBackMessage(listener, 0);
                }
            }
        });
    }

    private void sendBackMessage (SACapperInterface listener, int  dauId) {
        if (listener != null) {
            listener.didFindDAUID(dauId);
        }
    }
}
