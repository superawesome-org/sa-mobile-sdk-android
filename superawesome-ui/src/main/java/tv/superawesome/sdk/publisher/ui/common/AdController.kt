package tv.superawesome.sdk.publisher.ui.common

import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import tv.superawesome.sdk.publisher.common.models.AdRequest
import tv.superawesome.sdk.publisher.common.network.DataResult
import tv.superawesome.sdk.publisher.common.repositories.AdRepositoryType

interface AdControllerType {
    var testEnabled: Boolean
    fun load(placementId: Int, request: AdRequest)
}

class AdController(private val adRepository: AdRepositoryType) : AdControllerType {
    override var testEnabled: Boolean = false

    override fun load(placementId: Int, request: AdRequest) {
        Log.i("gunhan", "AdController.load(${placementId})")

        GlobalScope.launch {
            val result = adRepository.getAd(placementId, request)

            when (result) {
                is DataResult.Success -> {
                    result.value
                }
                is DataResult.Failure -> {
                    result.error
                }
            }
        }
    }
}