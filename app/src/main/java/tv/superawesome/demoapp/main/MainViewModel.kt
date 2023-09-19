package tv.superawesome.demoapp.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tv.superawesome.demoapp.model.FeatureItem
import tv.superawesome.demoapp.model.FeatureType
import tv.superawesome.demoapp.model.PlacementItem
import tv.superawesome.demoapp.repository.FeaturesRepository

class MainViewModel : ViewModel() {
    private var featuresRepository: FeaturesRepository = FeaturesRepository()

    val items: MutableLiveData<List<PlacementItem>> by lazy {
        MutableLiveData<List<PlacementItem>>()
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = featuresRepository.fetchAllFeatures()
                updateData(result.optValue)
            } catch (error: Throwable) {
                Log.e("SATestApp", "Error loading placements: ${error.message}")
                updateData(
                    listOf(
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

    private fun updateData(features: List<FeatureItem>?) {
        if (features == null) return
        val placements = mutableListOf<PlacementItem>()
        features.forEach { placements.addAll(it.placements) }
        items.postValue(placements)
    }

    fun insertPlacementItem(placementItem: PlacementItem) {
        val placements = mutableListOf<PlacementItem>()
        items.value?.let { placements.addAll(it) }

        val insertionIndex = placements.indexOfFirst { it.type ==  placementItem.type }

        if (insertionIndex >= 0) {
            placements.add(insertionIndex, placementItem)
        }

        items.postValue(placements)
    }
}
