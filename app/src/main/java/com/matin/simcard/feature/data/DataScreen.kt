package com.matin.simcard.feature.data

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.matin.simcard.R
import com.matin.simcard.designsystem.component.CountryButton
import com.matin.simcard.designsystem.component.DataPackageChips
import com.matin.simcard.designsystem.component.DataUsageItems
import com.matin.simcard.designsystem.component.simCardProgressIndicator
import com.matin.simcard.designsystem.theme.AdjustSystemBarColor
import com.matin.simcard.designsystem.theme.SimCardTheme
import com.matin.simcard.designsystem.util.DEFAULT_BOTTOM_NAV_HEIGHT
import com.matin.simcard.designsystem.util.dataPackages
import com.matin.simcard.designsystem.util.dataUsageItems
import com.matin.simcard.designsystem.util.extractNumber
import com.matin.simcard.model.DataPackage
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataScreen() {
    AdjustSystemBarColor(
        statusBarColor = MaterialTheme.colorScheme.primary,
        navigationBarColor = MaterialTheme.colorScheme.tertiary
    )

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    ExpandDataBottomSheet(bottomSheetScaffoldState)

    val currentProgressBarValue = remember {
        mutableStateOf("0")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(bottom = DEFAULT_BOTTOM_NAV_HEIGHT)
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.add_data),
                    style = MaterialTheme.typography.titleLarge,
                )
                CountryButton()
            }
            Spacer(modifier = Modifier.height(24.dp))
            simCardProgressIndicator(
                maxValue = 20f,
                currentValue = currentProgressBarValue.value,
                progressBackgroundColor = MaterialTheme.colorScheme.surface,
                progressIndicatorColor = MaterialTheme.colorScheme.outlineVariant,
            )
            Spacer(modifier = Modifier.height(22.dp))
            Text(
                text = stringResource(R.string.amplify_your_connectivity),
                style = MaterialTheme.typography.displaySmall
            )
            Spacer(modifier = Modifier.height(16.dp))
            DataUsageItems(dataUsageItems)
        }

        DataBottomSheet(state = bottomSheetScaffoldState) { selectedDataPackage ->
            currentProgressBarValue.value = selectedDataPackage.label.extractNumber()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExpandDataBottomSheet(bottomSheetScaffoldState: BottomSheetScaffoldState) {
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(key1 = Unit) {
        coroutineScope.launch {
            bottomSheetScaffoldState.bottomSheetState.expand()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataBottomSheet(state: BottomSheetScaffoldState, dataPackageClick: (DataPackage) -> Unit = {}) {
    BottomSheetScaffold(
        scaffoldState = state,
        sheetShape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
        sheetContainerColor = MaterialTheme.colorScheme.tertiary,
        sheetContent = { DataBottomSheetContent(dataPackageClick) },
    ) {}
}

@Composable
private fun DataBottomSheetContent(dataPackageClick: (DataPackage) -> Unit) {
    var dataPackages by remember {
        mutableStateOf(dataPackages)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.tertiary)
            .padding(horizontal = 16.dp)
    ) {
        DataPackageChips(
            packages = dataPackages,
        ) { selectedDataPackage ->
            dataPackageClick(selectedDataPackage)
            dataPackages = dataPackages.map {
                if (selectedDataPackage.label == it.label) it.copy(isSelected = true) else it.copy(
                    isSelected = false
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        PackageDetailItems()
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            shape = RoundedCornerShape(12.dp),
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        ) {
            Text(
                text = stringResource(R.string.continue_40_00_kr),
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
private fun PackageDetailItems() {
    PackageDetailsItem(label = stringResource(R.string.network), value = stringResource(R.string.vodafone))
    PackageDetailsItem(label = stringResource(R.string.plan_type), value = stringResource(R.string.data_only))
}

@Composable
fun PackageDetailsItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = MaterialTheme.colorScheme.onTertiaryContainer)
        Text(text = value, color = MaterialTheme.colorScheme.surfaceVariant)
    }
}

@Preview
@Composable
fun DataScreenPreview() {
    SimCardTheme {
        DataScreen()
    }
}