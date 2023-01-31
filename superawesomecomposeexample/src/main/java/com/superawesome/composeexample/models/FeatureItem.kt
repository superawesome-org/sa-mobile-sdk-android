package com.superawesome.composeexample.models

import android.os.Parcelable
import com.superawesome.composeexample.enums.FeatureType
import java.util.UUID
import kotlinx.parcelize.Parcelize

@Parcelize
data class FeatureItem (
    val id: UUID = UUID.randomUUID(),
    val type: FeatureType,
    val placements: List<PlacementItem>,
) : Parcelable
