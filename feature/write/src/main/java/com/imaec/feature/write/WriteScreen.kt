package com.imaec.feature.write

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.imaec.core.designsystem.component.dialog.CommonDialog
import com.imaec.core.designsystem.component.snackbar.showSnackbar
import com.imaec.core.designsystem.theme.AppTheme
import com.imaec.core.designsystem.theme.body1
import com.imaec.core.designsystem.theme.body2
import com.imaec.core.designsystem.theme.caption1
import com.imaec.core.designsystem.theme.gray400
import com.imaec.core.designsystem.theme.h2
import com.imaec.core.designsystem.theme.h3
import com.imaec.core.navigation.navigator.LocalAppNavigator
import com.imaec.core.navigation.utils.WRITE_DIARY_MESSAGE_KEY
import com.imaec.core.resource.R
import com.imaec.core.utils.extension.comma
import com.imaec.core.utils.extension.singleClickable
import com.imaec.core.utils.utils.toYearMonthDay
import com.imaec.domain.model.diary.MoodType
import com.imaec.domain.model.diary.WeatherType

@Composable
fun WriteScreen(viewModel: WriteViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showDeleteDialog by remember { mutableStateOf(false) }

    CollectEvent(viewModel = viewModel)

    if (showDeleteDialog) {
        DeleteDialog(
            viewModel = viewModel,
            onDismiss = { showDeleteDialog = false }
        )
    }

    WriteScreen(
        writeType = uiState.writeType,
        date = uiState.date,
        weatherTypes = uiState.weatherTypes,
        moodTypes = uiState.moodTypes,
        content = uiState.content,
        onDateChange = viewModel::updateDate,
        onWeatherChange = viewModel::updateWeather,
        onMoodChange = viewModel::updateMood,
        onTextChange = viewModel::updateContent,
        onWriteClick = viewModel::writeDiary,
        onDeleteClick = { showDeleteDialog = true }
    )
}

@Composable
private fun CollectEvent(viewModel: WriteViewModel) {
    val appNavigator = LocalAppNavigator.current
    val contentEmptyMessage = stringResource(R.string.empty_content)
    val writePopUpMessage = stringResource(R.string.message_write_diary)
    val updatePopUpMessage = stringResource(R.string.message_update_diary)
    val deletePopUpMessage = stringResource(R.string.message_delete_diary)

    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when (it) {
                is WriteEvent.ShowContentEmptyMessage -> {
                    showSnackbar(message = contentEmptyMessage)
                }
                is WriteEvent.PopUp -> {
                    appNavigator.popUp(
                        WRITE_DIARY_MESSAGE_KEY to when (it.popUpType) {
                            WritePopUpType.WRITE -> writePopUpMessage
                            WritePopUpType.UPDATE -> updatePopUpMessage
                            WritePopUpType.DELETE -> deletePopUpMessage
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun DeleteDialog(
    viewModel: WriteViewModel,
    onDismiss: () -> Unit
) {
    CommonDialog(
        title = stringResource(R.string.message_delete_diary_title),
        message = stringResource(R.string.message_delete_diary_info),
        positiveButtonText = stringResource(R.string.confirm),
        negativeButtonText = stringResource(R.string.cancel),
        onPositiveButtonClick = {
            viewModel.deleteDiary()
            onDismiss()
        },
        onNegativeButtonClick = onDismiss,
        onDismiss = onDismiss
    )
}

@Composable
private fun WriteScreen(
    writeType: WriteType,
    date: String,
    weatherTypes: List<Pair<WeatherType, Boolean>>,
    moodTypes: List<Pair<MoodType, Boolean>>,
    content: String,
    onDateChange: (String) -> Unit,
    onWeatherChange: (WeatherType) -> Unit,
    onMoodChange: (MoodType) -> Unit,
    onTextChange: (String) -> Unit,
    onWriteClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .imePadding()
            .fillMaxSize(),
        topBar = {
            WriteTopBar()
        },
        content = { contentPadding ->
            WriteContent(
                contentPadding = contentPadding,
                date = date,
                weatherTypes = weatherTypes,
                moodTypes = moodTypes,
                content = content,
                onDateChange = onDateChange,
                onWeatherChange = onWeatherChange,
                onMoodChange = onMoodChange,
                onTextChange = onTextChange
            )
        },
        bottomBar = {
            WriteButton(
                writeType = writeType,
                onWriteClick = onWriteClick,
                onDeleteClick = onDeleteClick
            )
        }
    )
}

@Composable
private fun WriteTopBar() {
    val appNavigator = LocalAppNavigator.current

    Row(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.record_tody_diary),
            textAlign = TextAlign.Center,
            style = h2().copy(fontWeight = FontWeight.Bold),
            color = AppTheme.colors.onBackground
        )
        Icon(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .singleClickable { appNavigator.popUp() }
                .padding(8.dp),
            painter = painterResource(R.drawable.ic_close),
            contentDescription = "close",
            tint = AppTheme.colors.onBackground
        )
    }
}

@Composable
private fun WriteContent(
    contentPadding: PaddingValues,
    date: String,
    weatherTypes: List<Pair<WeatherType, Boolean>>,
    moodTypes: List<Pair<MoodType, Boolean>>,
    content: String,
    onDateChange: (String) -> Unit,
    onWeatherChange: (WeatherType) -> Unit,
    onMoodChange: (MoodType) -> Unit,
    onTextChange: (String) -> Unit
) {
    var showCalendarDialog by remember { mutableStateOf(false) }
    var showWeatherBottomSheet by remember { mutableStateOf(false) }
    var showMoodBottomSheet by remember { mutableStateOf(false) }
    val (year, month, day) = remember(date) { date.toYearMonthDay() }

    if (showCalendarDialog) {
        CalendarDialog(
            date = date,
            onDateClick = onDateChange,
            onDismiss = {
                showCalendarDialog = false
            }
        )
    }

    if (showWeatherBottomSheet) {
        WeatherBottomSheet(
            onWeatherSelected = onWeatherChange,
            onDismiss = {
                showWeatherBottomSheet = false
            }
        )
    }

    if (showMoodBottomSheet) {
        MoodBottomSheet(
            onMoodSelected = onMoodChange,
            onDismiss = {
                showMoodBottomSheet = false
            }
        )
    }

    LazyColumn(
        modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.singleClickable { showCalendarDialog = true },
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(
                            R.string.date_format_for_write,
                            year,
                            month,
                            day
                        ),
                        style = body2().copy(fontWeight = FontWeight.SemiBold)
                    )
                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(R.drawable.ic_down),
                        contentDescription = "down"
                    )
                }
                Row(
                    modifier = Modifier.singleClickable { showWeatherBottomSheet = true },
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = weatherTypes.first { it.second }.first.emoji,
                        style = body2().copy(fontWeight = FontWeight.SemiBold)
                    )
                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(R.drawable.ic_down),
                        contentDescription = "down"
                    )
                }
            }
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.today_mood),
                    style = body1().copy(fontWeight = FontWeight.Bold)
                )
                Row(
                    modifier = Modifier.singleClickable { showMoodBottomSheet = true },
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = moodTypes.first { it.second }.first.emoji,
                        style = body2().copy(fontWeight = FontWeight.SemiBold)
                    )
                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(R.drawable.ic_down),
                        contentDescription = "down"
                    )
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(8.dp))
        }
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = stringResource(R.string.haru_diary),
                        style = h3(),
                        color = AppTheme.colors.primary
                    )
                    Text(
                        text = stringResource(R.string.input_count, content.length.comma()),
                        style = caption1(),
                        color = gray400
                    )
                }
                DiaryTextField(
                    content = content,
                    onTextChange = onTextChange
                )
            }
        }
    }
}

@Composable
private fun ColumnScope.DiaryTextField(
    content: String,
    onTextChange: (String) -> Unit
) {
    BasicTextField(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(
                color = AppTheme.colors.primaryContainer,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp),
        value = content,
        onValueChange = onTextChange,
        textStyle = body2().copy(color = AppTheme.colors.tertiary),
        cursorBrush = SolidColor(AppTheme.colors.tertiary),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.TopStart
            ) {
                if (content.isEmpty()) {
                    Text(
                        text = stringResource(R.string.hint_write_diary),
                        style = body2(),
                        color = AppTheme.colors.primary
                    )
                }
                innerTextField()
            }
        }
    )
}

@Composable
private fun WriteButton(
    writeType: WriteType,
    onWriteClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 32.dp
            ),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (writeType == WriteType.UPDATE) {
            Box(
                modifier = Modifier
                    .navigationBarsPadding()
                    .background(
                        color = AppTheme.colors.onPrimary,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = AppTheme.colors.primary,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .weight(0.3f)
                    .height(56.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .singleClickable {
                        onDeleteClick()
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_delete),
                    tint = AppTheme.colors.primary,
                    contentDescription = null
                )
            }
        }
        Box(
            modifier = Modifier
                .navigationBarsPadding()
                .background(
                    color = AppTheme.colors.primary,
                    shape = RoundedCornerShape(8.dp)
                )
                .weight(1f)
                .height(56.dp)
                .clip(RoundedCornerShape(8.dp))
                .singleClickable {
                    onWriteClick()
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "기록하기",
                style = body1().copy(fontWeight = FontWeight.Bold),
                color = AppTheme.colors.onPrimary,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun WriteScreenPreview() {
    AppTheme {
        WriteScreen(
            writeType = WriteType.UPDATE,
            date = "2025-06-19",
            weatherTypes = WeatherType.toSelectableList(),
            moodTypes = MoodType.toSelectableList(),
            content = "",
            onDateChange = {},
            onWeatherChange = {},
            onMoodChange = {},
            onTextChange = {},
            onWriteClick = {},
            onDeleteClick = {}
        )
    }
}
