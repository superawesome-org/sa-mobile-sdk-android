package tv.superawesome.sdk.publisher.components

import android.util.Log
import tv.superawesome.sdk.publisher.ad.NewAdController
import tv.superawesome.sdk.publisher.models.AdResponse

interface AdStoreType {
    fun put(adController: NewAdController)
    fun consume(placementId: Int): NewAdController?

    /** Peeks the content inside the store without consuming it. */
    fun peek(placementId: Int): NewAdController?

    /** Clears cache. */
    fun clear()
}

class AdStore : AdStoreType {
    private val data = HashMap<Int, NewAdController>()

    override fun put(adController: NewAdController) {
        data[adController.adResponse.placementId] = adController
    }

    override fun consume(placementId: Int): NewAdController? {
        Log.d("MATHEUS", "consume, placements: $data")
        val item = data[placementId]
        if (data[placementId] != null) {
            data.remove(placementId)
        }
        return item
    }

    override fun peek(placementId: Int): NewAdController? = data[placementId]

    override fun clear() {
        data.clear()
    }
}
