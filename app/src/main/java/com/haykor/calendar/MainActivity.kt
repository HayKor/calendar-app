package com.haykor.calendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.haykor.calendar.core.di.appModule
import com.haykor.calendar.core.di.authModule
import com.haykor.calendar.core.navigation.NavigationRoot
import com.haykor.calendar.core.ui.theme.AppTheme
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        startKoin {
            androidLogger()
            androidContext(this@MainActivity)
            modules(appModule, authModule)
        }
        setContent {
            AppTheme {
                NavigationRoot()
            }
        }
    }
}
