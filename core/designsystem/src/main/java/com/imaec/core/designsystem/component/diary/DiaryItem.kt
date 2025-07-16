package com.imaec.core.designsystem.component.diary

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imaec.core.designsystem.theme.AppTheme
import com.imaec.core.designsystem.theme.gray400
import com.imaec.core.designsystem.theme.h3
import com.imaec.core.designsystem.theme.label1
import com.imaec.core.designsystem.theme.like
import com.imaec.core.model.diary.DiaryVo
import com.imaec.core.navigation.navigator.LocalAppNavigator
import com.imaec.core.navigation.navigator.app.AppRoute
import com.imaec.core.resource.R
import com.imaec.core.utils.extension.singleClickable
import com.imaec.core.utils.utils.formatDate
import com.imaec.core.utils.utils.parseDate
import com.imaec.core.utils.utils.toYearMonthDay
import com.imaec.domain.model.diary.MoodType
import com.imaec.domain.model.diary.WeatherType
import java.util.Date

@Composable
fun DiaryItem(
    diary: DiaryVo,
    onDiaryLikeClick: (DiaryVo) -> Unit,
    modifier: Modifier = Modifier,
    clickable: Boolean = true
) {
    val appNavigator = LocalAppNavigator.current
    val (year, month, day) = remember(diary.date) { diary.date.toYearMonthDay() }
    val dayOfWeek = (diary.date.parseDate(format = "yyyy-MM-dd") ?: Date())
        .formatDate(format = "E")
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .shadow(
                elevation = 1.dp,
                shape = RoundedCornerShape(8.dp),
                ambientColor = AppTheme.colors.primary,
                spotColor = AppTheme.colors.primary
            )
            .background(
                color = AppTheme.colors.surface,
                shape = RoundedCornerShape(8.dp)
            )
            .singleClickable(enabled = clickable) {
                appNavigator.navigate(AppRoute.Write(diaryId = diary.id))
            }
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(
                        R.string.date_format_for_diary,
                        year,
                        month,
                        day,
                        dayOfWeek
                    ),
                    style = label1(),
                    color = gray400
                )
                Text(
                    text = diary.weather.emoji,
                    style = h3()
                )
            }
            Text(
                modifier = Modifier.padding(end = 44.dp),
                text = diary.content,
                style = label1(),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
        Text(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp),
            text = diary.mood.emoji,
            style = h3().copy(fontSize = 36.sp)
        )
        Icon(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(12.dp)
                .size(32.dp)
                .clip(CircleShape)
                .singleClickable(enabled = clickable) {
                    onDiaryLikeClick(diary)
                }
                .padding(4.dp),
            painter = painterResource(
                if (diary.isLiked) R.drawable.ic_like_fill else R.drawable.ic_like
            ),
            tint = if (diary.isLiked) like else Color.Unspecified,
            contentDescription = "liked diary"
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DiaryItemPreview() {
    AppTheme {
        DiaryItem(
            diary = DiaryVo(
                date = "2025-06-19",
                mood = MoodType.HAPPY,
                weather = WeatherType.RAINY,
                content = "힘든 하루였다 힘든 하루였다 힘든 하루였다 힘든 하루였다 힘든 하루였다 힘든 하루였다" +
                    "힘든 하루였다 힘든 하루였다 힘든 하루였다 힘든 하루였다 힘든 하루였다 힘든 하루였다",
                isLiked = true
            ),
            onDiaryLikeClick = {}
        )
    }
}
