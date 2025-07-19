package com.imaec.feature.fontsetting

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.imaec.core.designsystem.component.appbar.HaruAppBar
import com.imaec.core.designsystem.component.diary.DiaryItem
import com.imaec.core.designsystem.theme.AppTheme
import com.imaec.core.designsystem.theme.body2
import com.imaec.core.designsystem.theme.kotra_hope
import com.imaec.core.designsystem.theme.leeseoyun
import com.imaec.core.designsystem.theme.ownglyph_parckdahyun
import com.imaec.core.designsystem.theme.ownglyph_ryurue
import com.imaec.core.designsystem.theme.pretendard
import com.imaec.core.model.diary.DiaryVo
import com.imaec.core.resource.R
import com.imaec.core.utils.extension.singleClickable
import com.imaec.domain.model.setting.FontType

@Composable
fun FontSettingScreen(viewModel: FontSettingViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    FontSettingScreen(
        selectedFontType = uiState.selectedFontType,
        onFontTypeClick = viewModel::saveFontType
    )
}

@Composable
private fun FontSettingScreen(
    selectedFontType: FontType,
    onFontTypeClick: (FontType) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            // font가 변경 될 때마다 recomposition 되도록 설정
            key(selectedFontType) {
                HaruAppBar(title = stringResource(R.string.font_setting))
            }
        },
        content = { contentPadding ->
            FontSettingContent(
                contentPadding = contentPadding,
                selectedFontType = selectedFontType,
                onFontTypeClick = onFontTypeClick
            )
        }
    )
}

@Composable
private fun FontSettingContent(
    contentPadding: PaddingValues,
    selectedFontType: FontType,
    onFontTypeClick: (FontType) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize()
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        DiaryItem(
            modifier = Modifier.padding(horizontal = 16.dp),
            diary = DiaryVo.mock(content = stringResource(R.string.example_diary)),
            clickable = false,
            onDiaryLikeClick = {}
        )
        HorizontalDivider(color = AppTheme.colors.outline)
        FontList(
            selectedFontType = selectedFontType,
            onFontTypeClick = onFontTypeClick
        )
    }
}

@Composable
private fun FontList(
    selectedFontType: FontType,
    onFontTypeClick: (FontType) -> Unit
) {
    LazyColumn {
        items(FontType.entries) { fontType ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .singleClickable {
                        onFontTypeClick(fontType)
                    }
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = fontType.fontName(),
                    style = body2().copy(
                        fontFamily = when (fontType) {
                            FontType.DEFAULT -> pretendard
                            FontType.KOTRA_HOPE -> kotra_hope
                            FontType.OWNGLYPH_PARKDAHYUN -> ownglyph_parckdahyun
                            FontType.OWNGLYPH_RYURUE -> ownglyph_ryurue
                            FontType.LEESEOYUN -> leeseoyun
                        }
                    ),
                    color = AppTheme.colors.onBackground
                )
                RadioButton(
                    selected = fontType == selectedFontType,
                    onClick = { onFontTypeClick(fontType) }
                )
            }
        }
    }
}

@Composable
private fun FontType.fontName(): String = when (this) {
    FontType.DEFAULT -> stringResource(R.string.font_default)
    FontType.KOTRA_HOPE -> stringResource(R.string.font_kotra_hope)
    FontType.OWNGLYPH_RYURUE -> stringResource(R.string.font_ownglyph_ryurue)
    FontType.OWNGLYPH_PARKDAHYUN -> stringResource(R.string.font_ownglyph_parkdahyun)
    FontType.LEESEOYUN -> stringResource(R.string.font_leeseoyun)
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun FontSettingScreenPreview() {
    AppTheme {
        FontSettingScreen(
            selectedFontType = FontType.OWNGLYPH_PARKDAHYUN,
            onFontTypeClick = {}
        )
    }
}
