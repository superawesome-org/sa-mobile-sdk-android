package tv.superawesome.sdk.publisher.components

import tv.superawesome.sdk.publisher.ad.AdController

interface AdStoreType {
    fun put(adController: AdController)
    fun consume(placementId: Int): AdController?

    /** Peeks the content inside the store without consuming it. */
    fun peek(placementId: Int): AdController?

    /** Clears cache. */
    fun clear()
}

class AdStore : AdStoreType {
    private val data = HashMap<Int, AdController>()

    override fun put(adController: AdController) {
        data[adController.adResponse.placementId] = adController
    }

    override fun consume(placementId: Int): AdController? {
        val item = data[placementId]
        if (data[placementId] != null) {
            data.remove(placementId)
        }
        return item
    }

    override fun peek(placementId: Int): AdController? = data[placementId]

    override fun clear() {
        data.clear()
    }
}
