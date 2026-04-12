package com.haykor.calendar.core.di

import android.util.Log
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import com.haykor.calendar.BuildConfig
import com.haykor.calendar.core.data.local.datastore.TokenManager
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.new
import org.koin.dsl.module
import org.koin.dsl.onClose

val appModule =
    module {
        single {
            PreferenceDataStoreFactory.create(
                produceFile = { androidContext().preferencesDataStoreFile("calendar_preferences") },
            )
        }

        single { new(::TokenManager) }

        single {
            HttpClient(OkHttp) {
                install(ContentNegotiation) {
                    json(
                        Json {
                            ignoreUnknownKeys = true
                            isLenient = true
                        },
                    )
                }
                install(Logging) {
                    logger =
                        object : Logger {
                            override fun log(message: String) {
                                Log.d("Ktor", message)
                            }
                        }
                    level = LogLevel.ALL // logs headers, body, url, method
                }
                defaultRequest {
                    url(BuildConfig.API_URL)
                    contentType(ContentType.Application.Json)
                }
            }
        } onClose { it?.close() }
    }
