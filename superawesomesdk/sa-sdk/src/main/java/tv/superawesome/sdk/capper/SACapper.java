package tv.superawesome.sdk.capper;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;

import java.io.IOException;

import tv.superawesome.lib.sautils.SAUtils;

/**
 * Created by gabriel.coman on 16/02/16.
 */
public class SACapper {

    /** constant that specifies the local-pref dict key to use */
    private static final String SUPER_AWESOME_FIRST_PART_DAU = "SUPER_AWESOME_FIRST_PART_DAU";

    /**
     * static method to enable Capping
     * Through it's listener interface it returns a dauID
     * The dauID can be non-0 -> in which case it's valid
     * or it can be 0 -> in which case it's not valid (user does not have tracking enabled or
     * gms enabled)
     **/
    public static void enableCapping(final Context context, final SACapperListener listener) {


        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {

                /** get the ad data from Google Play Services */
                String adString = "";
                try {
                    AdvertisingIdClient.Info adInfo = AdvertisingIdClient.getAdvertisingIdInfo(context);
                    if (adInfo.isLimitAdTrackingEnabled()) {
                        adString = adInfo.getId();
                    }
                } catch (IOException e) {
                    /** do nothing */
                    Log.d("SuperAwesome", "IOException");
                } catch (GooglePlayServicesNotAvailableException e) {
                    /** do nothing */
                    Log.d("SuperAwesome", "GooglePlayServicesNotAvailableException");
                } catch (GooglePlayServicesRepairableException e) {
                    /** do nothing */
                    Log.d("SuperAwesome", "GooglePlayServicesRepairableException");
                } catch (VerifyError e) {
                    /** do nothing */
                    Log.d("SuperAwesome", "VerifyError");
                } catch (NullPointerException e) {
                    /** do nothing */
                    Log.d("SuperAwesome", "NullPointerException");
                }

                return adString;
            }

            @Override
            protected void onPostExecute(String adInfo) {
                super.onPostExecute(adInfo);

                if (!adInfo.equals("")){

                    /** continue as if  user has Ad Tracking enabled and all ... */
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

                    /** create the dauID */
                    String firstPartOfDAU = adInfo;
                    String secondPartOfDAU = preferences.getString(SUPER_AWESOME_FIRST_PART_DAU, null);

                    if (secondPartOfDAU == null || secondPartOfDAU.equals("")) {
                        SharedPreferences.Editor editor = preferences.edit();
                        secondPartOfDAU = generateUniqueKey();
                        editor.putString(SUPER_AWESOME_FIRST_PART_DAU, secondPartOfDAU);
                        editor.apply();
                    }

                    int hash1 = Math.abs(firstPartOfDAU.hashCode());
                    int hash2 = Math.abs(secondPartOfDAU.hashCode());
                    int dauHash = Math.abs(hash1 ^ hash2);
                    Log.d("SuperAwesome", "hashes " + hash1 + "-" + hash2 + "-" + dauHash);

                    if (listener != null){
                        listener.didFindDAUId(dauHash);
                    }
                }
                /** either the service is not available or the user does not have Google Play Services */
                else {
                    if (listener != null) {
                        listener.didFindDAUId(0);
                    }
                }
            }
        };
        task.execute();
    }


    /** group of functions that relate to the Device-App-User ID */
    private static String generateUniqueKey () {
        /** constants */
        final String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXZY0123456789";
        final int length = alphabet.length();
        final int dauLength = 32;

        /** generate the string */
        String s = "";
        for (int i = 0; i < dauLength; i++){
            int index = SAUtils.randomNumberBetween(0, length - 1);
            s += alphabet.charAt(index);
        }
        return s;
    }

    /**
     * Listener interface for the Dau
     */
    public interface SACapperListener {
        void didFindDAUId(int dauID);
    }
}
