package tv.superawesome.sdk.publisher.common.components

import tv.superawesome.sdk.publisher.common.models.AdResponse

internal interface AdStoreType {
    fun put(adResponse: AdResponse)
    fun consume(placementId: Int): AdResponse?

    /** Peeks the content inside the store without consuming it */
    fun peek(placementId: Int): AdResponse?

    /** Clears cache */
    fun clear()
}

internal class AdStore : AdStoreType {
    private val data = HashMap<Int, AdResponse>()

    override fun put(adResponse: AdResponse) {
        data[adResponse.placementId] = adResponse
    }

    override fun consume(placementId: Int): AdResponse? {
        val item = data[placementId]
        if (data[placementId] != null) {
            data.remove(placementId)
        }
        return item
    }

    override fun peek(placementId: Int): AdResponse? = data[placementId]

    override fun clear() {
        data.clear()
    }
}
