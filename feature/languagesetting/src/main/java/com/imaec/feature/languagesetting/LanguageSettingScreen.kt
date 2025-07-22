package com.imaec.feature.languagesetting

import android.app.LocaleManager
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.imaec.core.designsystem.component.appbar.HaruAppBar
import com.imaec.core.designsystem.theme.AppTheme
import com.imaec.core.designsystem.theme.body2
import com.imaec.core.resource.R
import com.imaec.core.utils.extension.findActivity
import com.imaec.core.utils.extension.singleClickable
import com.imaec.domain.model.setting.LanguageType
import java.util.Locale

@Composable
fun LanguageSettingScreen(viewModel: LanguageSettingViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LanguageSettingScreen(
        selectedLanguageType = uiState.selectedLanguageType,
        onItemClick = viewModel::selectLanguageType
    )
}

@Composable
private fun LanguageSettingScreen(
    selectedLanguageType: LanguageType,
    onItemClick: (LanguageType) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            HaruAppBar(title = stringResource(R.string.language_setting))
        },
        content = { contentPadding ->
            LanguageSettingContent(
                contentPadding = contentPadding,
                selectedLanguageType = selectedLanguageType,
                onItemClick = onItemClick
            )
        }
    )
}

@Composable
private fun LanguageSettingContent(
    contentPadding: PaddingValues,
    selectedLanguageType: LanguageType,
    onItemClick: (LanguageType) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            top = contentPadding.calculateTopPadding() + 16.dp,
            bottom = contentPadding.calculateBottomPadding() + 16.dp
        )
    ) {
        items(LanguageType.entries) { languageType ->
            DarkModeSettingItem(
                languageType = languageType,
                isSelected = selectedLanguageType == languageType,
                onItemClick = onItemClick
            )
        }
    }
}

@Composable
private fun DarkModeSettingItem(
    languageType: LanguageType,
    isSelected: Boolean,
    onItemClick: (LanguageType) -> Unit
) {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .singleClickable { onItemClick(languageType) }
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = languageType.languageName(),
            style = body2(),
            color = AppTheme.colors.onBackground
        )
        RadioButton(
            selected = isSelected,
            onClick = {
                onItemClick(languageType)
                setAppLocale(
                    context = context,
                    language = languageType.language
                )
            }
        )
    }
}

@Composable
private fun LanguageType.languageName(): String = when (this) {
    LanguageType.SYSTEM -> stringResource(R.string.system_default)
    LanguageType.KR -> "한국어"
    LanguageType.US -> "English"
}

private fun setAppLocale(context: Context, language: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        with(context.getSystemService(LocaleManager::class.java)) {
            val locales = if (language.isEmpty()) {
                LocaleList.getEmptyLocaleList()
            } else {
                LocaleList.forLanguageTags(language)
            }
            applicationLocales = locales
        }
    } else {
        // Android 13 미만 처리
        with(context.findActivity()) {
            val locale = (if (language.isEmpty()) Locale.getDefault() else Locale(language))
                .also(Locale::setDefault)
            val config = Configuration(resources.configuration).apply {
                setLocale(locale)
            }
            resources.updateConfiguration(config, resources.displayMetrics)
            recreate()
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun LanguageSettingScreenPreview() {
    AppTheme {
        LanguageSettingScreen(
            selectedLanguageType = LanguageType.SYSTEM,
            onItemClick = {}
        )
    }
}
