package com.imaec.feature.home

import android.content.res.Configuration
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.imaec.core.designsystem.component.calendar.HaruCalendar
import com.imaec.core.designsystem.component.diary.DiaryItem
import com.imaec.core.designsystem.component.empty.DiaryEmpty
import com.imaec.core.designsystem.theme.AppTheme
import com.imaec.core.designsystem.theme.h3
import com.imaec.core.model.diary.DiaryVo
import com.imaec.core.resource.R
import com.imaec.core.utils.extension.from

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // todo
//    BackStackEntryCallback(
//        key = WRITE_DIARY_MESSAGE_KEY,
//        onResult = ::showSnackbar
//    )

    HomeScreen(
        diaries = uiState.diaries,
        selectedDate = uiState.selectedDate,
        onDateClick = viewModel::selectDate,
        onDiaryLikeClick = viewModel::toggleDiaryLike
    )
}

@Composable
private fun HomeScreen(
    diaries: List<DiaryVo>,
    selectedDate: Triple<Int, Int, Int>,
    onDateClick: (year: Int, month: Int, day: Int) -> Unit,
    onDiaryLikeClick: (DiaryVo) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            HomeTopBar(diariesCount = diaries.size)
        },
        content = { contentPadding ->
            HomeContent(
                contentPadding = contentPadding,
                diaries = diaries,
                selectedDate = selectedDate,
                onDateClick = onDateClick,
                onDiaryLikeClick = onDiaryLikeClick
            )
        }
    )
}

@Composable
private fun HomeTopBar(diariesCount: Int) {
    Row(
        modifier = Modifier
            .background(AppTheme.colors.primaryContainer)
            .statusBarsPadding()
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(
                if (diariesCount == 0) {
                    R.string.written_diaries_empty
                } else {
                    R.string.written_diaries_count
                },
                diariesCount
            ),
            style = h3(),
            color = AppTheme.colors.tertiary
        )
    }
}

@Composable
private fun HomeContent(
    contentPadding: PaddingValues,
    diaries: List<DiaryVo>,
    selectedDate: Triple<Int, Int, Int>,
    onDateClick: (year: Int, month: Int, day: Int) -> Unit,
    onDiaryLikeClick: (DiaryVo) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize()
    ) {
        HaruCalendar(
            selectedDate = selectedDate,
            diaries = diaries,
            onDateClick = onDateClick,
            onMonthMoved = onDateClick
        )
        Box {
            DiaryList(
                diaries = diaries,
                selectedDate = selectedDate,
                onDiaryLikeClick = onDiaryLikeClick
            )
            Spacer(
                modifier = Modifier
                    .height(8.dp)
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                AppTheme.colors.onBackground.copy(alpha = 0.1f),
                                AppTheme.colors.onBackground.copy(alpha = 0f)
                            )
                        )
                    )
            )
        }
    }
}

@Composable
private fun DiaryList(
    diaries: List<DiaryVo>,
    selectedDate: Triple<Int, Int, Int>,
    onDiaryLikeClick: (DiaryVo) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 24.dp,
            // MainBottomBar + Fab height
            bottom = 24.dp + 56.dp + 30.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val date = "%04d-%02d-%02d".from(
            selectedDate.first,
            selectedDate.second,
            selectedDate.third
        )
        val todayDiaries = diaries.filter { it.date == date }
        items(todayDiaries) { diary ->
            DiaryItem(
                diary = diary,
                onDiaryLikeClick = onDiaryLikeClick
            )
        }
        if (todayDiaries.isEmpty()) {
            item {
                DiaryEmpty(emptyMessage = stringResource(R.string.empty_diaries))
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HomeScreenPreview() {
    AppTheme {
        HomeScreen(
            diaries = DiaryVo.mockList(size = 1),
            selectedDate = Triple(2025, 6, 19),
            onDateClick = { _, _, _ -> },
            onDiaryLikeClick = {}
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HomeScreenEmptyPreview() {
    AppTheme {
        HomeScreen(
            diaries = emptyList(),
            selectedDate = Triple(2025, 6, 19),
            onDateClick = { _, _, _ -> },
            onDiaryLikeClick = {}
        )
    }
}
