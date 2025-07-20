package com.imaec.feature.darkmodesetting

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.imaec.core.designsystem.component.appbar.HaruAppBar
import com.imaec.core.designsystem.theme.AppTheme
import com.imaec.core.designsystem.theme.body2
import com.imaec.core.resource.R
import com.imaec.core.utils.extension.singleClickable
import com.imaec.domain.model.setting.DarkModeType

@Composable
fun DarkModeSettingScreen(viewModel: DarkModeSettingViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DarkModeSettingScreen(
        selectedDarkModeType = uiState.selectedDarkModeType,
        onItemClick = viewModel::selectDarkModeType
    )
}

@Composable
private fun DarkModeSettingScreen(
    selectedDarkModeType: DarkModeType,
    onItemClick: (DarkModeType) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            HaruAppBar(title = stringResource(R.string.dark_mode_setting))
        },
        content = { contentPadding ->
            DarkModeSettingContent(
                contentPadding = contentPadding,
                selectedDarkModeType = selectedDarkModeType,
                onItemClick = onItemClick
            )
        }
    )
}

@Composable
private fun DarkModeSettingContent(
    contentPadding: PaddingValues,
    selectedDarkModeType: DarkModeType,
    onItemClick: (DarkModeType) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            top = contentPadding.calculateTopPadding() + 16.dp,
            bottom = contentPadding.calculateBottomPadding() + 16.dp
        )
    ) {
        items(DarkModeType.entries) { darkModeType ->
            DarkModeSettingItem(
                darkModeType = darkModeType,
                isSelected = selectedDarkModeType == darkModeType,
                onItemClick = onItemClick
            )
        }
    }
}

@Composable
private fun DarkModeSettingItem(
    darkModeType: DarkModeType,
    isSelected: Boolean,
    onItemClick: (DarkModeType) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .singleClickable { onItemClick(darkModeType) }
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = darkModeType.darkModeName(),
            style = body2(),
            color = AppTheme.colors.onBackground
        )
        RadioButton(
            selected = isSelected,
            onClick = { onItemClick(darkModeType) }
        )
    }
}

@Composable
private fun DarkModeType.darkModeName(): String = when (this) {
    DarkModeType.SYSTEM -> stringResource(R.string.system_default)
    DarkModeType.LIGHT -> stringResource(R.string.light_mode)
    DarkModeType.DARK -> stringResource(R.string.dark_mode)
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DarkModeSettingScreenPreview() {
    AppTheme {
        DarkModeSettingScreen(
            selectedDarkModeType = DarkModeType.SYSTEM,
            onItemClick = {}
        )
    }
}
