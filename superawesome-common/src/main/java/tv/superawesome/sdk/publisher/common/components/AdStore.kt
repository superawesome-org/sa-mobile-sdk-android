package tv.superawesome.sdk.publisher.common.components

import tv.superawesome.sdk.publisher.common.models.AdResponse

interface AdStoreType {
    fun put(adResponse: AdResponse)
    fun consume(placementId: Int): AdResponse?

    /** Peaks the content inside the store without consuming it */
    fun peak(placementId: Int): AdResponse?
}

class AdStore : AdStoreType {
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

    override fun peak(placementId: Int): AdResponse? = data[placementId]
}
