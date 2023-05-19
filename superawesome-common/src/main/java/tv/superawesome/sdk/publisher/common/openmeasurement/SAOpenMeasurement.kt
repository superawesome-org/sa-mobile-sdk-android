package tv.superawesome.sdk.publisher.common.openmeasurement

import android.content.Context
import com.iab.omid.library.superawesome.Omid
import com.iab.omid.library.superawesome.ScriptInjector

object SAOpenMeasurement {

    fun initializeOmsdk(context: Context): Boolean {
        Omid.activate(context)
        if (!Omid.isActive()) {
            return false
        }
        Omid.updateLastActivity()
        return true
    }
}
