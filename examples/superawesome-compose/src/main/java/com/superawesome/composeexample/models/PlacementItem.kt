package com.superawesome.composeexample.models

import android.os.Parcelable
import com.superawesome.composeexample.enums.FeatureType
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class PlacementItem (
    val id: UUID = UUID.randomUUID(),
    val name: String = "Default",
    val type: FeatureType,
    val placementId: Int,
    val lineItemId: Int? = null,
    val creativeId: Int? = null,
) : Parcelable
