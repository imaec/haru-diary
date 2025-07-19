package com.imaec.feature.notificationsetting

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.imaec.core.designsystem.theme.AppTheme
import com.imaec.core.designsystem.theme.body2
import com.imaec.core.designsystem.theme.h2
import com.imaec.core.resource.R
import com.imaec.core.utils.extension.from
import com.imaec.core.utils.extension.singleClickable
import kotlinx.coroutines.launch

@Composable
fun TimeDialog(
    currentAmPm: Boolean,
    currentHour: Int,
    currentMinute: Int,
    onTimeSelected: (isAm: Boolean, hour: Int, minute: Int) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        TimeBottomSheetContent(
            currentAmPm = currentAmPm,
            currentHour = currentHour,
            currentMinute = currentMinute,
            onTimeSelected = onTimeSelected,
            onDismiss = onDismiss
        )
    }
}

@Composable
private fun TimeBottomSheetContent(
    currentAmPm: Boolean,
    currentHour: Int,
    currentMinute: Int,
    onTimeSelected: (isAm: Boolean, hour: Int, minute: Int) -> Unit,
    onDismiss: () -> Unit
) {
    var isAm by remember { mutableStateOf(currentAmPm) }
    var hour by remember { mutableIntStateOf(currentHour) }
    var minute by remember { mutableIntStateOf(currentMinute) }
    val pickerHeight = 200.dp
    val itemHeight = 50.dp

    Column(
        modifier = Modifier
            .background(
                color = AppTheme.colors.background,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(top = 16.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(onClick = onDismiss) {
                Text(
                    text = stringResource(R.string.cancel),
                    style = body2(),
                    color = AppTheme.colors.primary
                )
            }
            TextButton(
                onClick = {
                    onTimeSelected(isAm, hour, minute)
                    onDismiss()
                }
            ) {
                Text(
                    text = stringResource(R.string.confirm),
                    style = body2(),
                    color = AppTheme.colors.primary
                )
            }
        }
        Box {
            Box(
                Modifier
                    .align(Alignment.Center)
                    .padding(32.dp)
                    .height(itemHeight)
                    .fillMaxWidth()
                    .background(
                        color = AppTheme.colors.onPrimaryContainer.copy(alpha = 0.6f),
                        shape = RoundedCornerShape(8.dp)
                    )
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(pickerHeight)
                    .padding(horizontal = 32.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // AM/PM Wheel
                PickerColumn(
                    items = listOf(
                        stringResource(R.string.am),
                        stringResource(R.string.pm)
                    ),
                    selectedIndex = if (isAm) 0 else 1,
                    onSelected = { index -> isAm = (index == 0) },
                    itemHeight = itemHeight,
                    pickerHeight = pickerHeight
                )
                // Hour Wheel
                PickerColumn(
                    items = (1..12).map { "%02d".from(it) },
                    selectedIndex = hour - 1,
                    onSelected = { index -> hour = index + 1 },
                    itemHeight = itemHeight,
                    pickerHeight = pickerHeight
                )
                // Minute Wheel
                PickerColumn(
                    items = (0..59).map { "%02d".from(it) },
                    selectedIndex = minute,
                    onSelected = { index -> minute = index },
                    itemHeight = itemHeight,
                    pickerHeight = pickerHeight
                )
            }
        }
    }
}

@Composable
fun RowScope.PickerColumn(
    items: List<String>,
    selectedIndex: Int,
    onSelected: (Int) -> Unit,
    itemHeight: Dp,
    pickerHeight: Dp
) {
    // 스크롤 상태
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = selectedIndex)
    val coroutineScope = rememberCoroutineScope()
    val itemHeightPx = with(LocalDensity.current) { itemHeight.toPx() }

    LaunchedEffect(listState.isScrollInProgress) {
        if (!listState.isScrollInProgress) {
            val first = listState.firstVisibleItemIndex
            val offset = listState.firstVisibleItemScrollOffset

            // 이미 스크롤이 중앙에 정확히 맞으면 그냥 선택
            if (offset == 0) {
                onSelected(first)
                return@LaunchedEffect
            }

            // snap 방향 계산
            val snapTo = if (offset < itemHeightPx / 2) {
                first // 위쪽(현재 아이템)으로 snap
            } else {
                (first + 1).coerceAtMost(items.lastIndex) // 아래(다음 아이템)으로 snap
            }
            coroutineScope.launch {
                listState.animateScrollToItem(snapTo)
                onSelected(snapTo)
            }
        }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .weight(1f)
            .height(pickerHeight),
        contentPadding = PaddingValues(vertical = (pickerHeight - itemHeight) / 2)
    ) {
        itemsIndexed(items) { index, item ->
            val firstVisibleItemIndex by remember {
                derivedStateOf { listState.firstVisibleItemIndex }
            }
            val isSelected = index == firstVisibleItemIndex // 중앙 인덱스
            Box(
                modifier = Modifier
                    .height(itemHeight)
                    .fillMaxWidth()
                    .singleClickable(hasRipple = false) {
                        coroutineScope.launch {
                            listState.animateScrollToItem(index)
                            onSelected(index)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = item,
                    textAlign = TextAlign.Center,
                    style = h2(),
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    color = if (isSelected) {
                        AppTheme.colors.onBackground
                    } else {
                        AppTheme.colors.onBackground.copy(alpha = 0.4f)
                    }
                )
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MoodBottomSheetPreview() {
    AppTheme {
        Box(modifier = Modifier.background(AppTheme.colors.background)) {
            TimeBottomSheetContent(
                currentAmPm = true,
                currentHour = 9,
                currentMinute = 0,
                onTimeSelected = { _, _, _ -> },
                onDismiss = {}
            )
        }
    }
}
