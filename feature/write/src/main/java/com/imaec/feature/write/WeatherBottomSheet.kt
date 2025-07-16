package com.imaec.feature.write

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.imaec.core.designsystem.theme.AppTheme
import com.imaec.core.designsystem.theme.body1
import com.imaec.core.designsystem.theme.display2
import com.imaec.core.resource.R
import com.imaec.core.utils.extension.singleClickable
import com.imaec.domain.model.diary.WeatherType

@Composable
fun WeatherBottomSheet(
    onWeatherSelected: (WeatherType) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        containerColor = AppTheme.colors.surface,
        onDismissRequest = onDismiss
    ) {
        WeatherBottomSheetContent(
            onMoodSelected = onWeatherSelected,
            onDismiss = onDismiss
        )
    }
}

@Composable
private fun WeatherBottomSheetContent(
    onMoodSelected: (WeatherType) -> Unit,
    onDismiss: () -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 16.dp)) {
        WeatherType.entries.forEach { weather ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .singleClickable {
                        onMoodSelected(weather)
                        onDismiss()
                    }
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = weather.emoji,
                    style = display2()
                )
                Text(
                    text = weather.getWeatherName(),
                    style = body1()
                )
            }
        }
    }
}

@Composable
private fun WeatherType.getWeatherName(): String = when (this) {
    WeatherType.SUNNY -> stringResource(R.string.sunny)
    WeatherType.PARTLY_CLOUDY -> stringResource(R.string.partly_cloudy)
    WeatherType.CLOUDY -> stringResource(R.string.cloudy)
    WeatherType.RAINY -> stringResource(R.string.rainy)
    WeatherType.SNOWY -> stringResource(R.string.snowy)
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun WeatherBottomSheetPreview() {
    AppTheme {
        Box(modifier = Modifier.background(AppTheme.colors.background)) {
            WeatherBottomSheetContent(
                onMoodSelected = {},
                onDismiss = {}
            )
        }
    }
}
