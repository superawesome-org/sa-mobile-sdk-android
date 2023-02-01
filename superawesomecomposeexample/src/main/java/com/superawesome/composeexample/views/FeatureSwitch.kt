package com.superawesome.composeexample.views

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun FeatureSwitch(name: String, value: MutableState<Boolean>, onChange: (Boolean) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(name, modifier = Modifier.weight(1.0f))
        Switch(
            checked = value.value,
            onCheckedChange = {
                value.value = it
                onChange(it)
            },
        )
    }
}
