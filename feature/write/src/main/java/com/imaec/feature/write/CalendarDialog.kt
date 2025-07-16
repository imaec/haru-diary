package com.imaec.feature.write

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.imaec.core.designsystem.component.calendar.HaruCalendar
import com.imaec.core.designsystem.theme.AppTheme
import com.imaec.core.utils.extension.from
import com.imaec.core.utils.utils.parseDate
import java.util.Calendar

@Composable
fun CalendarDialog(
    date: String,
    onDateClick: (String) -> Unit,
    onDismiss: () -> Unit = {}
) {
    val currentDate = date.parseDate("yyyy-MM-dd")
    val selectedDate = Calendar.getInstance().run {
        if (currentDate != null) time = currentDate
        Triple(get(Calendar.YEAR), get(Calendar.MONTH) + 1, get(Calendar.DAY_OF_MONTH))
    }

    Dialog(onDismissRequest = { onDismiss() }) {
        Box(
            modifier = Modifier
                .background(
                    color = AppTheme.colors.background,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            HaruCalendar(
                selectedDate = selectedDate,
                onDateClick = { year, month, day ->
                    onDateClick("%04d-%02d-%02d".from(year, month, day))
                    onDismiss()
                }
            )
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CalendarDialogPreview() {
    AppTheme {
        CalendarDialog(
            date = "2025년 6월 19일",
            onDateClick = {}
        )
    }
}
