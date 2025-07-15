package com.imaec.feature.main

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.imaec.core.designsystem.theme.AppTheme
import com.imaec.core.designsystem.theme.white
import com.imaec.core.navigation.navigator.LocalMainNavigator
import com.imaec.core.navigation.navigator.main.MainRoute
import com.imaec.core.resource.R
import com.imaec.core.utils.extension.singleClickable

@Composable
fun MainBottomBar(
    selectedRoute: MainRoute,
    onBottomBarItemClick: (MainRoute) -> Unit
) {
    val bottomBarHeight = 56.dp

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(bottomBarHeight)
        ) {
            Background(bottomBarHeight = bottomBarHeight)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(bottomBarHeight)
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                MainBottomBarItem(
                    icon = R.drawable.ic_home,
                    selectedRoute = selectedRoute,
                    route = MainRoute.Home,
                    onBottomBarItemClick = onBottomBarItemClick
                )
                Spacer(modifier = Modifier.width(86.dp))
                MainBottomBarItem(
                    icon = R.drawable.ic_my,
                    selectedRoute = selectedRoute,
                    route = MainRoute.My,
                    onBottomBarItemClick = onBottomBarItemClick
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding())
                .background(AppTheme.colors.primaryContainer)
        )
    }
}

@Composable
private fun Background(bottomBarHeight: Dp) {
    val colors = AppTheme.colors

    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height
        val fabRadius = (bottomBarHeight / 2 + 8.dp).toPx()
        val minusPadding = 8.dp.toPx()

        val path = Path().apply {
            moveTo(0f, 0f)
            lineTo((width / 2f - fabRadius - minusPadding), 0f)

            cubicTo(
                (width / 2f - fabRadius), 0f,
                width / 2f - fabRadius, fabRadius - minusPadding,
                width / 2f, fabRadius - minusPadding
            )

            cubicTo(
                width / 2f + fabRadius, fabRadius - minusPadding,
                width / 2f + fabRadius, 0f,
                width / 2f + fabRadius + minusPadding, 0f
            )

            lineTo(width, 0f)
            lineTo(width, height)
            lineTo(0f, height)
            close()
        }

        drawPath(path = path, color = colors.primaryContainer)
    }
}

@Composable
private fun RowScope.MainBottomBarItem(
    @DrawableRes icon: Int,
    selectedRoute: MainRoute,
    route: MainRoute,
    onBottomBarItemClick: (MainRoute) -> Unit
) {
    val mainNavigator = LocalMainNavigator.current

    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .weight(1f)
            .fillMaxHeight()
            .clip(RoundedCornerShape(16.dp))
            .singleClickable {
                mainNavigator.navigate(route)
                onBottomBarItemClick(route)
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(icon),
            contentDescription = null,
            tint = if (selectedRoute == route) {
                AppTheme.colors.tertiary
            } else {
                AppTheme.colors.primary
            }
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MainBottomBarPreview() {
    AppTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(white),
            contentAlignment = Alignment.BottomCenter
        ) {
            MainBottomBar(
                selectedRoute = MainRoute.Home,
                onBottomBarItemClick = {}
            )
        }
    }
}
