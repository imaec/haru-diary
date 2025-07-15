package com.imaec.core.designsystem.component.empty

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.imaec.core.designsystem.theme.AppTheme
import com.imaec.core.designsystem.theme.body1
import com.imaec.core.resource.R

@Composable
fun DiaryEmpty(emptyMessage: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 80.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = emptyMessage,
            style = body1(),
            color = AppTheme.colors.primary
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DiaryEmptyPreview() {
    AppTheme {
        Box(modifier = Modifier.background(AppTheme.colors.background)) {
            DiaryEmpty(
                emptyMessage = stringResource(R.string.empty_diaries)
            )
        }
    }
}
