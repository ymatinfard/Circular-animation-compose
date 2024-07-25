package com.matin.simcard.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.matin.simcard.model.DataUsage

@Composable
fun DataUsageItems(dataUsageItems: List<DataUsage>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        dataUsageItems.forEach {
            DataUsageItem(time = it.time, type = it.type)
        }
    }
}

@Composable
fun DataUsageItem(time: String, type: String) {
    Column(
        modifier = Modifier.padding(start = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = time, style = MaterialTheme.typography.labelMedium)
        Text(
            text = type,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onTertiaryContainer
        )
    }
}