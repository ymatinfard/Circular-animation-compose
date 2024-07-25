package com.matin.simcard.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun AdjustSystemBarColor(statusBarColor: Color, navigationBarColor: Color) {
    val systemUiController = rememberSystemUiController()

    DisposableEffect(systemUiController) {
        systemUiController.apply {
            setSystemBarsColor(
                color = statusBarColor
            )
            setNavigationBarColor(
                color = navigationBarColor
            )
        }
        onDispose { }
    }
}