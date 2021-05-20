/**
 * @Copyright: SuperAwesome Trading Limited 2017
 * @Author: Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.lib.sasession.capper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Date;

import tv.superawesome.lib.sautils.SAUtils;

/**
 * Class that abstracts away generating a distinct ID called "DAU ID", which consists of:
 *  - the Advertising ID int
 *  - a random ID
 *  - the package name
 * each hashed and then XOR-ed together
 */
public class SACapper implements ISACapper {
    private static final String SUPER_AWESOME_FIRST_PART_DAU = "SUPER_AWESOME_FIRST_PART_DAU";

    private Context context;

    /**
     * Constructor with executor
     * @param context the current context (activity or fragment)
     */
    public SACapper(Context context) {
        this.context = context;
    }

    /**
     * Main capper method that takes an SACapperInterface interface instance as parameter, to be
     * able to sent back the generated ID when the async operation finishes
     *
     * @param listener an instance of the SACapperInterface
     */
    @Override
    public void getDauID(final SACapperInterface listener) {
        String firstPartOfDAU = SAUtils.getMonthYearStringFromDate(new Date());

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

    private void sendBackMessage(SACapperInterface listener, int dauId) {
        if (listener != null) {
            listener.didFindDAUID(dauId);
        }
    }
}
