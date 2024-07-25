package com.matin.simcard.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.matin.simcard.R
import com.matin.simcard.model.WifiLocation

@Composable
fun WifiLocationItem(wifiLocation: WifiLocation) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_wifi),
            contentDescription = "WiFi icon",
            tint = MaterialTheme.colorScheme.surfaceVariant
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = wifiLocation.title, color = MaterialTheme.colorScheme.surfaceVariant)
            if (wifiLocation.isOftenWork) {
                Spacer(modifier = Modifier.height(4.dp))
                WifiStatusRow(
                    isOnline = wifiLocation.isOnline,
                    textResId = R.string.often_works,
                    wifiLocationTitle = wifiLocation.title
                )

            } else if (!wifiLocation.isOnline) {
                Spacer(modifier = Modifier.height(4.dp))
                WifiStatusRow(
                    isOnline = wifiLocation.isOnline,
                    textResId = R.string.open_network,
                    wifiLocationTitle = wifiLocation.title
                )
            }
        }
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_forward),
            contentDescription = stringResource(R.string.go_to_network),
            tint = MaterialTheme.colorScheme.onTertiaryContainer
        )
    }
}

@Composable
fun WifiStatusRow(isOnline: Boolean, textResId: Int, wifiLocationTitle: String) {
    Row {
        WifiStatusIndicator(isOnline = isOnline)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(id = textResId, wifiLocationTitle),
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            style = MaterialTheme.typography.labelSmall
        )
    }
}