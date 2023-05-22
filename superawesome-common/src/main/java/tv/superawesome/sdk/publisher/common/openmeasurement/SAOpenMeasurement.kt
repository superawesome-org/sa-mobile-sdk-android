package tv.superawesome.sdk.publisher.common.openmeasurement

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.iab.omid.library.superawesome.Omid

object SAOpenMeasurement {

    fun initializeOmsdk(context: Context) {
        Handler(Looper.getMainLooper()).post {
            Omid.activate(context)
            if (!Omid.isActive()) {
                Log.d("SuperAwesome SDK", "Error, unable to start Open Measurement")
            }
            Omid.updateLastActivity()
        }
    }
}
