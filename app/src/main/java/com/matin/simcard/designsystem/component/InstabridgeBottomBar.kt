package com.matin.simcard.designsystem.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.matin.simcard.navigation.TopLevelDestination

@Composable
fun simCardBottomBar(
    currentDestination: TopLevelDestination,
    onNavigationToDestination: (TopLevelDestination) -> Unit,
    bottomNavItems: List<TopLevelDestination>
) {
    NavigationBar(
        modifier =
        Modifier
            .fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.tertiary
    ) {
        bottomNavItems.forEach { destination ->
            val isSelected = currentDestination == destination
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    onNavigationToDestination(destination)
                },
                icon = {
                    Icon(
                        painter = painterResource(destination.icon),
                        contentDescription = null
                    )
                },
                label = { Text(text = stringResource(destination.resourceId), style = MaterialTheme.typography.labelSmall) },
                colors = NavigationBarItemColors(
                    selectedIconColor = MaterialTheme.colorScheme.surface,
                    unselectedIconColor = MaterialTheme.colorScheme.onTertiaryContainer,
                    selectedIndicatorColor = MaterialTheme.colorScheme.tertiary,
                    selectedTextColor = MaterialTheme.colorScheme.surface,
                    unselectedTextColor = MaterialTheme.colorScheme.onTertiaryContainer,
                    disabledIconColor = MaterialTheme.colorScheme.onTertiaryContainer,
                    disabledTextColor = MaterialTheme.colorScheme.onTertiaryContainer
                )
            )
        }
    }
}