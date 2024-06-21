@file:Suppress("UndocumentedPublicClass", "UndocumentedPublicFunction")
package tv.superawesome.lib.sautils

import android.os.Parcel
import android.os.Parcelable
import tv.superawesome.sdk.publisher.SAEvent
import tv.superawesome.sdk.publisher.SAInterface
import java.util.EnumMap

/**
 * An EventSender is a class that validates if the event can be sent before sending.
 * Essentially avoiding sending the same event multiple times.
 * A new one should be created each time a new video ad is shown.
 *
 * @property placementId placementId.
 */
internal class EventSender(val placementId: Int) : Parcelable {
    private val events: MutableMap<SAEvent, Boolean> = EnumMap(SAEvent::class.java)

    /**
     * Listener to send the events to.
     */
    var listener: SAInterface? = null

    init {
        // Events we care, other events are sent before reaching SAVideoActivity.
        // The init block is called before the secondary constructor below, therefore it won't
        // overwrite the events map.
        events[SAEvent.adPlaying] = false
        events[SAEvent.adPaused] = false
        events[SAEvent.adShown] = false
        events[SAEvent.adEnded] = false
        events[SAEvent.adClosed] = false
        events[SAEvent.adFailedToShow] = false
    }

    // Constructor for when we're unmarshalling the parcel object. Part of the Parcelable interface.
    constructor(parcel: Parcel) : this(parcel.readInt()) {
        events[SAEvent.adPlaying] = parcel.readInt() == 1
        events[SAEvent.adPaused] = parcel.readInt() == 1
        events[SAEvent.adShown] = parcel.readInt() == 1
        events[SAEvent.adEnded] = parcel.readInt() == 1
        events[SAEvent.adClosed] = parcel.readInt() == 1
        events[SAEvent.adFailedToShow] = parcel.readInt() == 1
    }

    fun adPlaying() {
        // Play is an exception since it could be sent again if video is paused.
        event(SAEvent.adPlaying)
        events[SAEvent.adPaused] = false
    }

    fun adPaused() {
        // Pause is an exception since it could be sent again if video is playing.
        event(SAEvent.adPaused)
        events[SAEvent.adPlaying] = false
    }

    fun adShown() {
        event(SAEvent.adShown)
    }

    fun adEnded() {
        event(SAEvent.adEnded)
    }

    fun adClosed() {
        event(SAEvent.adClosed)
    }

    fun adFailedToShow() {
        event(SAEvent.adFailedToShow)
    }

    private fun event(event: SAEvent) {
        if (events[event] == true) {
            return
        }

        listener?.onEvent(placementId, event)
        events[event] = true
    }

    /* Parcelable interface implementation. */
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(placementId)
        parcel.writeInt(if (events[SAEvent.adPlaying] == true) 1 else 0)
        parcel.writeInt(if (events[SAEvent.adPaused] == true) 1 else 0)
        parcel.writeInt(if (events[SAEvent.adShown] == true) 1 else 0)
        parcel.writeInt(if (events[SAEvent.adEnded] == true) 1 else 0)
        parcel.writeInt(if (events[SAEvent.adClosed] == true) 1 else 0)
        parcel.writeInt(if (events[SAEvent.adFailedToShow] == true) 1 else 0)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<EventSender> {
        override fun createFromParcel(parcel: Parcel): EventSender = EventSender(parcel)

        override fun newArray(size: Int): Array<EventSender?> = arrayOfNulls(size)
    }
}
