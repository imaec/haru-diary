package com.imaec.core.designsystem.component.appbar

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.imaec.core.designsystem.theme.AppTheme
import com.imaec.core.designsystem.theme.h3
import com.imaec.core.navigation.navigator.LocalAppNavigator
import com.imaec.core.resource.R
import com.imaec.core.utils.extension.singleClickable

@Composable
fun HaruAppBar(
    title: String
) {
    val appNavigator = LocalAppNavigator.current

    Row(
        modifier = Modifier
            .background(AppTheme.colors.primaryContainer)
            .statusBarsPadding()
            .fillMaxWidth()
            .height(56.dp)
            .padding(start = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .singleClickable {
                    appNavigator.popUp()
                }
                .padding(4.dp),
            painter = painterResource(R.drawable.ic_back),
            contentDescription = "back",
            tint = AppTheme.colors.tertiary
        )
        Text(
            text = title,
            style = h3(),
            color = AppTheme.colors.tertiary,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HaruAppBarPreview() {
    AppTheme {
        HaruAppBar(
            title = "AppBar"
        )
    }
}
