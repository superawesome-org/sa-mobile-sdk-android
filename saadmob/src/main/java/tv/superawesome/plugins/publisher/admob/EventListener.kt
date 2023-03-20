package tv.superawesome.plugins.publisher.admob

import android.util.Log
import tv.superawesome.sdk.publisher.SAEvent
import tv.superawesome.sdk.publisher.SAInterface
import java.lang.ref.WeakReference

internal class EventListener : SAInterface {
    private var observers: MutableList<WeakReference<SAInterface>> = mutableListOf()

    fun subscribe(listener: SAInterface) {
        observers.forEach {
            val observer = it.get()
            if (observer == listener) {
                Log.d(
                    "SuperAwesome",
                    "EventListener subscribe -> Already subscribed"
                )
                return
            }
        }
        observers.add(WeakReference(listener))
        Log.d(
            "SuperAwesome",
            "EventListener subscribe -> Subscribed Total: ${observers.count()}"
        )
    }

    fun unsubscribe(listener: SAInterface) {
        observers.forEach {
            val observer = it.get()
            if (observer == listener) {
                observers.remove(it)
                Log.d(
                    "SuperAwesome",
                    "EventListener unsubscribe -> Unsubscribed Total: ${observers.count()}"
                )
                return
            } else if (observer == null) {
                observers.remove(it)
                Log.d(
                    "SuperAwesome",
                    "EventListener unsubscribe -> Weak reference Total: ${observers.count()}"
                )
            }
        }
    }

    override fun onEvent(placementId: Int, event: SAEvent?) {
        observers.forEach {
            val observer = it.get()
            observer?.onEvent(placementId, event)
        }
    }

    companion object {
        val videoEvents = EventListener()
        val interstitialEvents = EventListener()
    }
}