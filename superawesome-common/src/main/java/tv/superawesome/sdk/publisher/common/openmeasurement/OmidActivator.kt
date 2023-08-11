package tv.superawesome.sdk.publisher.common.openmeasurement

import android.content.Context
import com.iab.omid.library.superawesome.Omid

class OmidActivator(
    private val context: Context,
) : OmidActivatorType {
    override fun activate() = Omid.activate(context.applicationContext)
}
