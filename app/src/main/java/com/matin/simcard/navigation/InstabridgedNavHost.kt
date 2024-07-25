package com.matin.simcard.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.matin.simcard.feature.data.navigation.DATA_ROUTE
import com.matin.simcard.feature.data.navigation.dataScreen
import com.matin.simcard.feature.menu.menuScreen
import com.matin.simcard.feature.wifi.navigation.wifiScreen

@Composable
fun simCardNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = DATA_ROUTE) {
        dataScreen()
        wifiScreen()
        menuScreen()
    }
}