package com.matin.simcard.designsystem.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun WifiStatusIndicator(isOnline: Boolean) {
    val color = if (isOnline) Color.Green else Color.Red
    Box(
        modifier = Modifier.size(10.dp)
    ) {
        Canvas(modifier = Modifier.size(10.dp)) {
            drawCircle(
                color = color,
                radius = size.minDimension / 2,
                center = Offset(x = size.width / 2, y = size.height / 2)
            )
        }
    }
}