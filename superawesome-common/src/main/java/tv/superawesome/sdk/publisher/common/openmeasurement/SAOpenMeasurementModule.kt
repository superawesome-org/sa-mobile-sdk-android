package tv.superawesome.sdk.publisher.common.openmeasurement

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.iab.omid.library.superawesome.Omid

/**
 * SAOpenMeasurementModule - The module used in dependency injection and the start up of the SDK,
 * this is vital to activate the Open Measurement SDK on launch.
 */
object SAOpenMeasurementModule {
    /**
     * activate - This activates the OM SDK on initial launch
     * @param context - this is used to activate and update the OMID object
     */
    fun activate(context: Context) {
        Handler(Looper.getMainLooper()).post {
            Omid.activate(context)
            if (!Omid.isActive()) {
                Log.d("SuperAwesome SDK", "Error, unable to start Open Measurement")
            }
            Omid.updateLastActivity()
        }
    }
}
