package tv.superawesome.demoapp.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tv.superawesome.demoapp.model.FeatureItem
import tv.superawesome.demoapp.model.PlacementItem
import tv.superawesome.demoapp.repository.FeaturesRepository

class MainViewModel : ViewModel() {
    private var featuresRepository: FeaturesRepository = FeaturesRepository()

    val items: MutableLiveData<List<PlacementItem>> by lazy {
        MutableLiveData<List<PlacementItem>>()
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val result = featuresRepository.fetchAllFeatures()
            updateData(result.optValue)
        }
    }

    private fun updateData(features: List<FeatureItem>?) {
        if (features == null) return
        val placements = mutableListOf<PlacementItem>()
        features.forEach { placements.addAll(it.placements) }
        items.postValue(placements)
    }
}
