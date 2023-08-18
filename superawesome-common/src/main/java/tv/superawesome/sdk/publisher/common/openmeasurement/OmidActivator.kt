package tv.superawesome.sdk.publisher.common.openmeasurement

import android.content.Context
import com.iab.omid.library.superawesome.Omid

/**
 * Activates the OMID object, this is to be performed at the start of each ad load request.
 */
internal class OmidActivator(
    private val context: Context,
) : OmidActivatorType {
    /**
     * Activates the OMID given the current context.
     */
    override fun activate() = Omid.activate(context.applicationContext)
}
