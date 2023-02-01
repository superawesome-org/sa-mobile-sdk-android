package com.superawesome.composeexample.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.superawesome.composeexample.models.FeatureItem

@Composable
fun FeaturesList(
    features: List<FeatureItem>,
    onTapFeature: (FeatureItem) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(features) { feature ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clickable {
                        onTapFeature(feature)
                    },
            ) {
                Text(
                    feature.type.name,
                    modifier = Modifier.padding(start = 0.dp, top = 10.dp, bottom = 10.dp),
                )
                Divider()
            }
        }
    }
}
