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
import com.imaec.domain.model.diary.MoodType

@Composable
fun MoodBottomSheet(
    onMoodSelected: (MoodType) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        containerColor = AppTheme.colors.surface,
        onDismissRequest = onDismiss
    ) {
        MoodBottomSheetContent(
            onMoodSelected = onMoodSelected,
            onDismiss = onDismiss
        )
    }
}

@Composable
private fun MoodBottomSheetContent(
    onMoodSelected: (MoodType) -> Unit,
    onDismiss: () -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 16.dp)) {
        MoodType.entries.forEach { mood ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .singleClickable {
                        onMoodSelected(mood)
                        onDismiss()
                    }
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = mood.emoji,
                    style = display2()
                )
                Text(
                    text = mood.getMoodName(),
                    style = body1()
                )
            }
        }
    }
}

@Composable
private fun MoodType.getMoodName(): String = when (this) {
    MoodType.HAPPY -> stringResource(R.string.happy)
    MoodType.BAD -> stringResource(R.string.bad)
    MoodType.ANGRY -> stringResource(R.string.angry)
    MoodType.EXCITED -> stringResource(R.string.excited)
    MoodType.SAD -> stringResource(R.string.sad)
    MoodType.RELAXED -> stringResource(R.string.relaxed)
    MoodType.TIRED -> stringResource(R.string.tired)
    MoodType.NORMAL -> stringResource(R.string.normal)
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MoodBottomSheetPreview() {
    AppTheme {
        Box(modifier = Modifier.background(AppTheme.colors.background)) {
            MoodBottomSheetContent(
                onMoodSelected = {},
                onDismiss = {}
            )
        }
    }
}
