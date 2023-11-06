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
import okhttp3.ConnectionSpec
import okhttp3.TlsVersion
import tv.superawesome.demoapp.HasEnvironment
import tv.superawesome.demoapp.SDKEnvironment
import tv.superawesome.demoapp.caching.UserPlacementsCache
import tv.superawesome.demoapp.model.FeatureItem
import tv.superawesome.demoapp.model.FeatureType
import tv.superawesome.demoapp.model.PlacementItem
import tv.superawesome.demoapp.repository.FeaturesRepository
import java.lang.Integer.max

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val featuresRepository: FeaturesRepository = FeaturesRepository(
        environment = HasEnvironment.environment,
        connectionSpec = if (HasEnvironment.environment == SDKEnvironment.UITesting) {
            ConnectionSpec.Builder(ConnectionSpec.CLEARTEXT).build()
        } else {
            ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_2)
                .build()
        }
    )
    private val userPlacementsCache = UserPlacementsCache(application.applicationContext)
    private val userPlacements: List<PlacementItem>?
        get() = items.value?.filter { it.isUserCreated }

    private val cachedUserPlacements: List<PlacementItem>
        get() = Json.decodeFromString<List<PlacementItem>>(userPlacementsCache.userPlacements)
            .sortedBy { it.creationTimestamp }

    private val hasCachedUserPlacements: Boolean
        get() = cachedUserPlacements.isNotEmpty()

    val items: MutableLiveData<ArrayList<PlacementItem>> by lazy {
        MutableLiveData<ArrayList<PlacementItem>>()
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = featuresRepository.fetchAllFeatures()
                updateData(result.optValue)
            } catch (error: Throwable) {
                if (hasCachedUserPlacements) {
                    // Still load the cached placements
                    updateData(arrayListOf())
                } else {
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
    }

    private fun updateData(features: List<FeatureItem>?) {
        if (features == null) return
        val placements = ArrayList<PlacementItem>()
        features.forEach { placements.addAll(it.placements) }
        cachedUserPlacements.forEach { placementItem ->
            insertPlacementItem(placementItem, placements)
        }
        items.postValue(placements)
    }

    fun insertPlacementItem(placementItem: PlacementItem) {
        val placements = items.value ?: ArrayList()
        insertPlacementItem(placementItem, placements)
        saveUserPlacements()
        items.postValue(placements)
    }

    fun removePlacementItem(index: Int) {
        val placements = items.value
        placements?.removeAt(index)
        saveUserPlacements()
        items.postValue(placements)
    }

    private fun insertPlacementItem(item: PlacementItem,
                                    placementItems: ArrayList<PlacementItem>) {

        val insertionIndex = max(0, placementItems.indexOfFirst { it.type ==  item.type })
        placementItems.add(insertionIndex, item)
    }

    private fun saveUserPlacements() {
        userPlacements?.let {
            val encodedUserPlacements = Json.encodeToString(it)
            userPlacementsCache.userPlacements = encodedUserPlacements
        }
    }
}
