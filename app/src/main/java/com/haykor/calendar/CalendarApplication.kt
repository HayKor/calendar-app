package com.haykor.calendar

import android.app.Application
import com.haykor.calendar.core.di.appModule
import com.haykor.calendar.core.di.authModule
import com.haykor.calendar.core.di.splashModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class CalendarApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@CalendarApplication)
            modules(appModule, splashModule, authModule)
        }
    }
}
