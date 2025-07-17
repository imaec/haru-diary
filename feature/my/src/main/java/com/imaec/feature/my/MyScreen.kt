package com.imaec.feature.my

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.imaec.core.designsystem.theme.AppTheme
import com.imaec.core.designsystem.theme.body2
import com.imaec.core.designsystem.theme.h1
import com.imaec.core.designsystem.theme.h2
import com.imaec.core.navigation.navigator.LocalAppNavigator
import com.imaec.core.navigation.navigator.app.AppRoute
import com.imaec.core.resource.R
import com.imaec.core.utils.extension.singleClickable

@Composable
fun MyScreen(viewModel: MyViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val installedTime = rememberInstalledTime()

    LaunchedEffect(Unit) {
        viewModel.setInstallTime(installedTime = installedTime)
    }

    MyScreen(
        togetherDays = uiState.togetherDays,
        diariesCount = uiState.diariesCount,
        likedDiariesCount = uiState.likedDiariesCount
    )
}

@Composable
private fun rememberInstalledTime(): Long {
    val context = LocalContext.current
    return remember {
        context.packageManager.getPackageInfo(
            context.packageName,
            0
        ).firstInstallTime
    }
}

@Composable
private fun MyScreen(
    togetherDays: String,
    diariesCount: String,
    likedDiariesCount: String
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { contentPadding ->
            MyContent(
                contentPadding = contentPadding,
                togetherDays = togetherDays,
                diariesCount = diariesCount,
                likedDiariesCount = likedDiariesCount
            )
        }
    )
}

@Composable
private fun MyContent(
    contentPadding: PaddingValues,
    togetherDays: String,
    diariesCount: String,
    likedDiariesCount: String
) {
    Column(
        modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize()
            .padding(vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                modifier = Modifier.padding(horizontal = 18.dp),
                text = stringResource(R.string.together_haru_diary),
                style = h2().copy(fontWeight = FontWeight.Normal),
                color = AppTheme.colors.onSurface
            )
            Text(
                modifier = Modifier.padding(horizontal = 18.dp),
                text = stringResource(R.string.together_days, togetherDays),
                style = h1(),
                color = AppTheme.colors.primary
            )
        }
        MyDiaryRecord(
            diariesCount = diariesCount,
            likedDiariesCount = likedDiariesCount
        )
        SettingList()
    }
}

@Composable
private fun MyDiaryRecord(
    diariesCount: String,
    likedDiariesCount: String
) {
    val appNavigator = LocalAppNavigator.current
    val records = listOf(
        Triple(
            stringResource(R.string.haru_diary),
            diariesCount,
            AppRoute.DiaryList
        ),
        Triple(
            stringResource(R.string.liked_diary),
            likedDiariesCount,
            AppRoute.LikedDiaryList
        )
    )

    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .background(
                color = AppTheme.colors.surface,
                shape = RoundedCornerShape(16.dp)
            ),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        records.forEach { record ->
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .singleClickable {
                        appNavigator.navigate(record.third)
                    }
                    .padding(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = record.first,
                    style = body2().copy(fontWeight = FontWeight.SemiBold),
                    color = AppTheme.colors.onBackground
                )
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = stringResource(R.string.diary_count, record.second),
                    style = body2().copy(fontWeight = FontWeight.SemiBold),
                    color = AppTheme.colors.onPrimaryContainer
                )
            }
        }
    }
}

@Composable
private fun SettingList() {
    val appNavigator = LocalAppNavigator.current
    val items = listOf(
        Triple(
            R.drawable.ic_font,
            stringResource(R.string.font_setting),
            AppRoute.FontSetting
        ),
        Triple(
            R.drawable.ic_notification,
            stringResource(R.string.notification_setting),
            AppRoute.NotificationSetting
        ),
        Triple(
            R.drawable.ic_lock,
            stringResource(R.string.lock_setting),
            AppRoute.LockSetting
        ),
        Triple(
            R.drawable.ic_dark_mode,
            stringResource(R.string.dark_mode_setting),
            AppRoute.DarkModeSetting
        ),
//        Triple(
//            R.drawable.ic_backup,
//            stringResource(R.string.backup_setting),
//            AppRoute.BackupSetting
//        ),
        Triple(
            R.drawable.ic_language,
            stringResource(R.string.language_setting),
            AppRoute.LanguageSetting
        )
//        Triple(
//            R.drawable.ic_review,
//            stringResource(R.string.app_review),
//            AppRoute.AppReview
//        ),
//        Triple(
//            R.drawable.ic_cs,
//            stringResource(R.string.customer_service),
//            AppRoute.Cs
//        )
    )

    LazyColumn {
        items(items) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .singleClickable {
                        appNavigator.navigate(it.third)
                    }
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(it.first),
                    contentDescription = null
                )
                Text(
                    text = it.second,
                    style = body2(),
                    color = AppTheme.colors.onBackground
                )
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MyScreenPreview() {
    AppTheme {
        MyScreen(
            togetherDays = "10",
            diariesCount = "10",
            likedDiariesCount = "3"
        )
    }
}
