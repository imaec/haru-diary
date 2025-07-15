package com.imaec.core.designsystem.component.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.imaec.core.designsystem.theme.AppTheme
import com.imaec.core.designsystem.theme.body1
import com.imaec.core.designsystem.theme.body2
import com.imaec.core.designsystem.theme.h3
import com.imaec.core.designsystem.theme.primary400
import com.imaec.core.model.diary.DiaryVo
import com.imaec.core.resource.R
import com.imaec.core.utils.extension.from
import com.imaec.core.utils.extension.singleClickable
import com.imaec.core.utils.utils.formatDate
import com.imaec.core.utils.utils.isToday
import java.util.Calendar

@Composable
fun HaruCalendar(
    onDateClick: (year: Int, month: Int, day: Int) -> Unit,
    selectedDate: Triple<Int, Int, Int>? = null,
    diaries: List<DiaryVo> = emptyList(),
    onMonthMoved: (year: Int, month: Int, day: Int) -> Unit = { _, _, _ -> }
) {
    val selectedCalendar = Calendar.getInstance().apply {
        selectedDate?.run {
            set(Calendar.YEAR, first)
            set(Calendar.MONTH, second - 1)
            set(Calendar.DAY_OF_MONTH, third)
        }
    }
    val calendar = (selectedCalendar.clone() as Calendar).apply {
        set(Calendar.DAY_OF_MONTH, 1)
    }
    var year by rememberSaveable { mutableIntStateOf(calendar.get(Calendar.YEAR)) }
    var month by rememberSaveable { mutableIntStateOf(calendar.get(Calendar.MONTH)) }

    // 월 전환
    fun moveMonth(delta: Int) = with(calendar) {
        set(year, month, 1)
        add(Calendar.MONTH, delta)
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
        onMonthMoved(year, month + 1, 1)
    }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Month(
            year = year,
            calendar = calendar,
            selectedCalendar = selectedCalendar,
            onDateClick = onDateClick,
            onMonthMove = ::moveMonth,
            onYearChange = { year = it },
            onMonthChange = { month = it }
        )
        Week()
        Days(
            year = year,
            month = month,
            calendar = calendar,
            selectedDate = selectedDate,
            diaries = diaries,
            onDateClick = onDateClick
        )
    }
}

@Composable
private fun Month(
    year: Int,
    calendar: Calendar,
    selectedCalendar: Calendar,
    onDateClick: (Int, Int, Int) -> Unit,
    onMonthMove: (Int) -> Unit,
    onYearChange: (Int) -> Unit,
    onMonthChange: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .clickable { onMonthMove(-1) }
                    .padding(8.dp),
                painter = painterResource(R.drawable.ic_prev),
                contentDescription = "previous",
                tint = AppTheme.colors.onBackground
            )
            Box(modifier = Modifier.width(50.dp))
        }
        Text(
            text = stringResource(
                R.string.year_month,
                year,
                calendar.time.formatDate(format = "MMM")
            ),
            style = h3()
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Today(
                selectedCalendar = selectedCalendar,
                onDateClick = onDateClick,
                onYearChange = onYearChange,
                onMonthChange = onMonthChange
            )
            Icon(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .clickable { onMonthMove(1) }
                    .padding(8.dp),
                painter = painterResource(R.drawable.ic_next),
                contentDescription = "previous",
                tint = AppTheme.colors.onBackground
            )
        }
    }
}

@Composable
private fun Today(
    selectedCalendar: Calendar = Calendar.getInstance(),
    onDateClick: (Int, Int, Int) -> Unit,
    onYearChange: (Int) -> Unit = {},
    onMonthChange: (Int) -> Unit = {}
) {
    if (!selectedCalendar.isToday()) {
        Text(
            modifier = Modifier
                .width(50.dp)
                .singleClickable(hasRipple = false) {
                    val (todayYear, todayMonth, todayDay) = Calendar.getInstance().run {
                        Triple(
                            get(Calendar.YEAR),
                            get(Calendar.MONTH),
                            get(Calendar.DAY_OF_MONTH)
                        )
                    }
                    onYearChange(todayYear)
                    onMonthChange(todayMonth)
                    onDateClick(todayYear, todayMonth + 1, todayDay)
                },
            text = stringResource(R.string.today),
            textAlign = TextAlign.End,
            style = body1()
        )
    } else {
        Box(modifier = Modifier.width(50.dp))
    }
}

@Composable
private fun Week() {
    val days = listOf(
        stringResource(R.string.sunday),
        stringResource(R.string.monday),
        stringResource(R.string.tuesday),
        stringResource(R.string.wednesday),
        stringResource(R.string.thursday),
        stringResource(R.string.friday),
        stringResource(R.string.saturday)
    )
    Row(modifier = Modifier.fillMaxWidth()) {
        for (day in days) {
            Text(
                modifier = Modifier.weight(1f),
                text = day,
                textAlign = TextAlign.Center,
                style = body2().copy(fontWeight = FontWeight.Bold),
                color = when (days.indexOf(day)) {
                    0, 6 -> primary400 // 토, 일요일 색상
                    else -> AppTheme.colors.onBackground
                }
            )
        }
    }
}

@Composable
private fun Days(
    year: Int,
    month: Int,
    calendar: Calendar,
    selectedDate: Triple<Int, Int, Int>?,
    diaries: List<DiaryVo>,
    onDateClick: (Int, Int, Int) -> Unit
) {
    // 날짜 계산
    val firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) // 1=일요일, 7=토요일
    val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    // 날짜
    val cells = mutableListOf<Int?>().apply {
        for (i in 1 until firstDayOfWeek) add(null) // 앞 빈칸
        for (i in 1..daysInMonth) add(i)
        repeat(7 - size % 7) { add(null) }
    }
    Column {
        cells.chunked(7).forEach { week ->
            Row(modifier = Modifier.fillMaxWidth()) {
                for (day in week) {
                    val isSelected = year == selectedDate?.first &&
                        (month + 1) == selectedDate.second &&
                        day == selectedDate.third
                    Day(
                        year = year,
                        month = month,
                        day = day,
                        isSelected = isSelected,
                        diaries = diaries,
                        onDateClick = onDateClick
                    )
                }
            }
        }
    }
}

@Composable
private fun RowScope.Day(
    year: Int,
    month: Int,
    day: Int?,
    isSelected: Boolean,
    diaries: List<DiaryVo>,
    onDateClick: (Int, Int, Int) -> Unit
) {
    Box(
        modifier = Modifier
            .weight(1f)
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(
                if (isSelected) AppTheme.colors.primary else AppTheme.colors.background
            )
            .singleClickable(enabled = day != null) {
                if (day != null) {
                    onDateClick(year, month + 1, day)
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day?.toString() ?: "",
            style = if (isSelected) h3() else body2(),
            color = if (isSelected) AppTheme.colors.onPrimary else AppTheme.colors.onBackground
        )
        val date = "%04d-%02d-%02d".from(year, month + 1, day)
        if (diaries.any { it.date == date }) {
            Box(
                modifier = Modifier
                    .padding(top = 30.dp)
                    .size(6.dp)
                    .background(
                        color = AppTheme.colors.primary,
                        shape = CircleShape
                    )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeCalendarPreview() {
    AppTheme {
        HaruCalendar(
            selectedDate = Triple(2025, 6, 19),
            diaries = emptyList(),
            onDateClick = { _, _, _ -> }
        )
    }
}
