package com.haykor.calendar.core.di

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import com.haykor.calendar.core.data.local.datastore.TokenManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule =
    module {
        single {
            PreferenceDataStoreFactory.create(
                produceFile = { androidContext().preferencesDataStoreFile("calendar_preferences") },
            )
        }
        single { TokenManager(get()) }
    }
