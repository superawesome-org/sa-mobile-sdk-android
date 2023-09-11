package tv.superawesome.plugins.publisher.admob

import android.util.Log
import tv.superawesome.sdk.publisher.models.SAEvent
import tv.superawesome.sdk.publisher.models.SAInterface

import java.lang.ref.WeakReference

internal class EventListener : SAInterface {
    private val observers: MutableList<WeakReference<SAInterface>> = mutableListOf()

    @Synchronized
    fun subscribe(listener: SAInterface) {
        val iterator = observers.iterator()
        while (iterator.hasNext()) {
            val element = iterator.next()
            val observer = element.get()
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

    @Synchronized
    fun unsubscribe(listener: SAInterface) {
        Log.d(
            "SuperAwesome",
            "EventListener unsubscribe -> Total: ${observers.count()}"
        )
        val iterator = observers.iterator()

        while (iterator.hasNext()) {
            val element = iterator.next()
            val observer = element.get()
            if (observer == null || observer == listener) {
                iterator.remove()
                Log.d(
                    "SuperAwesome",
                    "EventListener unsubscribe -> Unsubscribed Total: ${observers.count()}"
                )
            }
        }
    }

    @Synchronized
    override fun onEvent(placementId: Int, event: SAEvent) {
        Log.d(
            "SuperAwesome",
            "EventListener onEvent -> placementId: $placementId event: $event"
        )
        observers.toList().forEach {
            val observer = it.get()
            observer?.onEvent(placementId, event)
        }
    }

    companion object {
        val videoEvents = EventListener()
        val interstitialEvents = EventListener()
    }
}
