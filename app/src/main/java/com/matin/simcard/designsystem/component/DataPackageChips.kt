package com.matin.simcard.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.matin.simcard.model.DataPackage

@Composable
fun DataPackageChips(
    packages: List<DataPackage>,
    onDataPackageClick: (DataPackage) -> Unit,
) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        packages.forEach { item ->
            FilterChip(
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier.padding(end = 8.dp),
                onClick = { onDataPackageClick(item) },
                label = {
                    Text(text = item.label)
                },
                selected = item.isSelected,
            )
        }
    }
}