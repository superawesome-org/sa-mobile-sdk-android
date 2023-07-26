package com.superawesome.composeexample.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.superawesome.composeexample.models.PlacementItem

@Composable
fun PlacementsList(
    placements: List<PlacementItem>,
    onTapPlacement: (PlacementItem) -> Unit,
    modifier: Modifier,
) {
    LazyColumn(modifier = modifier) {
        items(placements) { placement ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clickable {
                        onTapPlacement(placement)
                    },
            ) {
                Text(
                    placement.name,
                    modifier = Modifier.padding(10.dp),
                )
                Divider()
            }
        }
    }
}
