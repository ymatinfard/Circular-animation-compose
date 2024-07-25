package com.matin.simcard.feature.menu

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val MENU_ROUTE = "menu_route"

fun NavGraphBuilder.menuScreen() {
    return composable(MENU_ROUTE) {
        MenuScreen()
    }
}
