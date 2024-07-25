package com.matin.simcard.navigation

import androidx.annotation.StringRes
import com.matin.simcard.R
import com.matin.simcard.feature.data.navigation.DATA_ROUTE
import com.matin.simcard.feature.menu.MENU_ROUTE
import com.matin.simcard.feature.wifi.navigation.WIFI_ROUTE

enum class TopLevelDestination(
    val route: String,
    @StringRes val resourceId: Int,
    val icon: Int,
) {
    DATA(
        DATA_ROUTE,
        R.string.data,
        R.drawable.ic_data,
    ),
    Wifi(
        WIFI_ROUTE,
        R.string.wifi,
        R.drawable.ic_wifi,
    ),
    Menu(
        MENU_ROUTE,
        R.string.menu,
        R.drawable.ic_menu,
    ),
}
