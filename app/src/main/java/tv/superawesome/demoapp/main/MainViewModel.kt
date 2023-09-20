package tv.superawesome.demoapp.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import tv.superawesome.demoapp.caching.UserPlacementsCache
import tv.superawesome.demoapp.model.FeatureItem
import tv.superawesome.demoapp.model.FeatureType
import tv.superawesome.demoapp.model.PlacementItem
import tv.superawesome.demoapp.repository.FeaturesRepository
import java.lang.Integer.max

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private var featuresRepository: FeaturesRepository = FeaturesRepository()

    val items: MutableLiveData<ArrayList<PlacementItem>> by lazy {
        MutableLiveData<ArrayList<PlacementItem>>()
    }

    private val userPlacementsCache = UserPlacementsCache(application.applicationContext)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = featuresRepository.fetchAllFeatures()
                updateData(result.optValue)
            } catch (error: Throwable) {
                Log.e("SATestApp", "Error loading placements: ${error.message}")
                updateData(
                    arrayListOf(
                        FeatureItem(
                            FeatureType.BANNER,
                            listOf(
                                PlacementItem(name = "Error loading placements"),
                            ),
                        )
                    )
                )
            }
        }
    }

    private val userPlacements: List<PlacementItem>?
        get() = items.value?.filter { it.isUserAdded }

    private val cachedUserPlacement: List<PlacementItem>
        get() = Json.decodeFromString<List<PlacementItem>>(
            userPlacementsCache.userPlacements
        ).sortedBy { it.creationTimestamp }

    private fun updateData(features: List<FeatureItem>?) {
        if (features == null) return
        val placements = ArrayList<PlacementItem>()
        features.forEach { placements.addAll(it.placements) }
        cachedUserPlacement.forEach { placementItem ->
            val insertionIndex = max(0, placements.indexOfFirst { it.type ==  placementItem.type })
            placements.add(insertionIndex, placementItem)
        }
        items.postValue(placements)
    }

    fun insertPlacementItem(placementItem: PlacementItem) {
        val placements = items.value ?: ArrayList()
        val insertionIndex = max(0, placements.indexOfFirst { it.type ==  placementItem.type })
        placements.add(insertionIndex, placementItem)
        saveUserPlacements()
        items.postValue(placements)
    }

    fun removePlacementItem(index: Int) {
        val placements = items.value
        placements?.removeAt(index)
        saveUserPlacements()
        items.postValue(placements)
    }

    private fun saveUserPlacements() {
        userPlacements?.let {
            val encodedUserPlacements = Json.encodeToString(it)
            userPlacementsCache.userPlacements = encodedUserPlacements
        }
    }
}
