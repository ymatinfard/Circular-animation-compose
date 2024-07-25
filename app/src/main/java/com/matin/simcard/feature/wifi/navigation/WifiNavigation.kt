package com.matin.simcard.feature.wifi.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.matin.simcard.feature.wifi.MapScreenViewModel
import com.matin.simcard.feature.wifi.WifiScreen

const val WIFI_ROUTE = "wifi_route"

fun NavGraphBuilder.wifiScreen() {
    return composable(WIFI_ROUTE) {
        WifiScreen(viewModel = hiltViewModel<MapScreenViewModel>())
    }
}
