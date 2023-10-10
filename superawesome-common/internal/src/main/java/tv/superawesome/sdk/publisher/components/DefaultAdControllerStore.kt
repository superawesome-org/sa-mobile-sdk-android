package tv.superawesome.sdk.publisher.components

import tv.superawesome.sdk.publisher.ad.AdController

/**
 * Store definition for [AdController]s.
 */
interface AdControllerStore {

    /**
     * Puts an [AdController] in the store.
     */
    fun put(adController: AdController)

    /**
     * Consumes an [AdController] from the store, if it exists and removes it from the store
     * or returns `null`.
     */
    fun consume(placementId: Int): AdController?

    /**
     * Peeks the content inside the store without consuming it.
     */
    fun peek(placementId: Int): AdController?

    /**
     * Clears cache.
     */
    fun clear()
}

/**
 * Default implementation of an [AdControllerStore].
 */
class DefaultAdControllerStore : AdControllerStore {
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

    override fun peek(placementId: Int): AdController? = data[placementId].also {
        println(data)
    }

    override fun clear() {
        data.clear()
    }
}
