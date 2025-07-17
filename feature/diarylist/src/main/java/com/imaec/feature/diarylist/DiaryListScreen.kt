package com.imaec.feature.diarylist

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.imaec.core.designsystem.component.appbar.HaruAppBar
import com.imaec.core.designsystem.component.diary.DiaryItem
import com.imaec.core.designsystem.component.empty.DiaryEmpty
import com.imaec.core.designsystem.theme.AppTheme
import com.imaec.core.model.diary.DiaryVo
import com.imaec.core.resource.R

@Composable
fun DiaryListScreen(viewModel: DiaryListViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DiaryListScreen(
        diaries = uiState.diaries,
        onDiaryLikeClick = viewModel::toggleDiaryLike
    )
}

@Composable
private fun DiaryListScreen(
    diaries: List<DiaryVo>,
    onDiaryLikeClick: (DiaryVo) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            HaruAppBar(title = stringResource(R.string.haru_diary))
        },
        content = { contentPadding ->
            DiaryListContent(
                contentPadding = contentPadding,
                diaries = diaries,
                onDiaryLikeClick = onDiaryLikeClick
            )
        }
    )
}

@Composable
private fun DiaryListContent(
    contentPadding: PaddingValues,
    diaries: List<DiaryVo>,
    onDiaryLikeClick: (DiaryVo) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = contentPadding.calculateTopPadding() + 16.dp,
            bottom = contentPadding.calculateBottomPadding() + 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(diaries) { diary ->
            DiaryItem(
                diary = diary,
                onDiaryLikeClick = onDiaryLikeClick
            )
        }
        if (diaries.isEmpty()) {
            item {
                DiaryEmpty(emptyMessage = stringResource(R.string.empty_diaries))
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DiaryListScreenPreview() {
    AppTheme {
        DiaryListScreen(
            diaries = DiaryVo.mockList(),
            onDiaryLikeClick = {}
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DiaryListScreenEmptyPreview() {
    AppTheme {
        DiaryListScreen(
            diaries = emptyList(),
            onDiaryLikeClick = {}
        )
    }
}
