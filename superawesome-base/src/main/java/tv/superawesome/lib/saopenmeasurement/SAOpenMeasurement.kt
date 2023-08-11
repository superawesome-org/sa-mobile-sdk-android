package tv.superawesome.lib.saopenmeasurement

import android.content.Context
import com.iab.omid.library.superawesome.Omid

/**
 * Object for initialising the OM SDK.
 */
object SAOpenMeasurement {

    /**
     * Initialises the OMSDK.
     * @param context The context used to activate OMID.
     * @return True if the OMSDK is successfully activated.
     */
    fun initializeOmsdk(context: Context): Boolean {
        Omid.activate(context)
        if (!Omid.isActive()) {
            return false
        }
        Omid.updateLastActivity()
        return true
    }
}
