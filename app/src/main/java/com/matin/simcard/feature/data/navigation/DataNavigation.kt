package com.matin.simcard.feature.data.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.matin.simcard.feature.data.DataScreen

const val DATA_ROUTE = "data_route"

fun NavGraphBuilder.dataScreen() {
    return composable(DATA_ROUTE) {
        DataScreen()
    }
}
