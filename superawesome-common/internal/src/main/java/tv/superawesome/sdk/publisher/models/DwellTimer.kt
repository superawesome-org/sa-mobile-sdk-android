package tv.superawesome.sdk.publisher.models

/**
 * A simple ticker that triggers an action once a certain number of [ticksNeeded] ticked.
 */
class DwellTimer(
    private val ticksNeeded: Int,
) {

    private var ticks = 0

    fun tick(action: () -> Unit) {
        ticks++
        if (ticks % ticksNeeded == 0) {
            action()
        }
    }
}
