package com.imaec.harudiary

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.CompositionLocalProvider
import com.imaec.core.navigation.navigator.LocalAppNavigator
import com.imaec.core.navigation.navigator.LocalMainNavigator
import com.imaec.core.navigation.navigator.app.AppNavigator
import com.imaec.core.navigation.navigator.main.MainNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    internal lateinit var appNavigator: AppNavigator

    @Inject
    internal lateinit var mainNavigator: MainNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            CompositionLocalProvider(
                LocalAppNavigator provides appNavigator,
                LocalMainNavigator provides mainNavigator
            ) {
                AppNavGraph()
            }
        }
    }
}
