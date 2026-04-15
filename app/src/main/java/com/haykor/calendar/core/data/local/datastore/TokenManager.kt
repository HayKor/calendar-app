package com.haykor.calendar.core.data.local.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.haykor.calendar.core.common.domain.model.Tokens
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class TokenManager(
    private val dataStore: DataStore<Preferences>,
) {
    private companion object {
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        val ACCESS_TOKEN_EXPIRY = longPreferencesKey("access_token_expiry")
        val REFRESH_TOKEN_EXPIRY = longPreferencesKey("refresh_token_expiry")

        const val ACCESS_TOKEN_BUFFER_MS = 60_000L // refresh 1 minute before actual expiry
    }

    suspend fun saveTokens(tokens: Tokens) {
        dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = tokens.accessToken
            preferences[REFRESH_TOKEN] = tokens.refreshToken
            preferences[ACCESS_TOKEN_EXPIRY] =
                System.currentTimeMillis() + tokens.accessTokenExpiresIn
            preferences[REFRESH_TOKEN_EXPIRY] =
                System.currentTimeMillis() + tokens.refreshTokenExpiresIn
        }
    }

    suspend fun getAccessToken(): String? = dataStore.data.map { it[ACCESS_TOKEN] }.first()

    suspend fun getRefreshToken(): String? = dataStore.data.map { it[REFRESH_TOKEN] }.first()

    suspend fun isAccessTokenExpired(): Boolean {
        val expiry = dataStore.data.map { it[ACCESS_TOKEN_EXPIRY] ?: 0L }.first()
        Log.d("tokens", "expiryAccess=$expiry currentTime=${System.currentTimeMillis()}")
        return System.currentTimeMillis() >= expiry - ACCESS_TOKEN_BUFFER_MS
    }

    suspend fun isRefreshTokenExpired(): Boolean {
        val expiry =
            dataStore.data.map { it[REFRESH_TOKEN_EXPIRY] ?: 0L }.first()
        Log.d("tokens", "expiryRefresh=$expiry currentTime=${System.currentTimeMillis()}")
        return System.currentTimeMillis() >= expiry
    }

    suspend fun clearTokens() {
        dataStore.edit { preferences ->
            preferences.remove(ACCESS_TOKEN)
            preferences.remove(REFRESH_TOKEN)
            preferences.remove(ACCESS_TOKEN_EXPIRY)
            preferences.remove(REFRESH_TOKEN_EXPIRY)
        }
    }
}
