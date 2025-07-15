package com.imaec.feature.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.imaec.core.designsystem.theme.AppTheme

@Composable
fun HomeScreen() {
    HomeScreen(dummy = "")
}

@Composable
private fun HomeScreen(dummy: String) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { contentPadding ->
            HomeContent(contentPadding = contentPadding)
        }
    )
}

@Composable
private fun HomeContent(contentPadding: PaddingValues) {
    Box(
        modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize()
    ) {
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HomeScreenPreview() {
    AppTheme {
        HomeScreen(dummy = "")
    }
}
