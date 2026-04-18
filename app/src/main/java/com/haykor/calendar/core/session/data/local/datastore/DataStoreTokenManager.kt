package com.haykor.calendar.core.session.data.local.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.haykor.calendar.core.common.domain.model.Tokens
import com.haykor.calendar.core.session.domain.repository.TokenManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class DataStoreTokenManager(
    private val dataStore: DataStore<Preferences>,
) : TokenManager {
    private companion object {
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        val ACCESS_TOKEN_EXPIRY = longPreferencesKey("access_token_expiry")
        val REFRESH_TOKEN_EXPIRY = longPreferencesKey("refresh_token_expiry")

        const val ACCESS_TOKEN_BUFFER_MS = 60_000L
        const val TAG = "TokenManager"
    }

    override suspend fun saveTokens(tokens: Tokens) {
        Log.d(
            TAG,
            "Saving tokens — accessExpiresIn=${tokens.accessTokenExpiresIn}ms," +
                " refreshExpiresIn=${tokens.refreshTokenExpiresIn}ms",
        )
        dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = tokens.accessToken
            preferences[REFRESH_TOKEN] = tokens.refreshToken
            preferences[ACCESS_TOKEN_EXPIRY] =
                System.currentTimeMillis() + tokens.accessTokenExpiresIn
            preferences[REFRESH_TOKEN_EXPIRY] =
                System.currentTimeMillis() + tokens.refreshTokenExpiresIn
        }
        Log.d(TAG, "Tokens saved successfully")
    }

    override suspend fun getAccessToken(): String? =
        dataStore.data.map { it[ACCESS_TOKEN] }.first().also { token ->
            Log.d(TAG, "getAccessToken — ${if (token != null) "found" else "not found"}")
        }

    override suspend fun getRefreshToken(): String? =
        dataStore.data.map { it[REFRESH_TOKEN] }.first().also { token ->
            Log.d(TAG, "getRefreshToken — ${if (token != null) "found" else "not found"}")
        }

    override suspend fun isAccessTokenExpired(): Boolean {
        val now = System.currentTimeMillis()
        val expiry = dataStore.data.map { it[ACCESS_TOKEN_EXPIRY] ?: 0L }.first()
        val isExpired = now >= expiry - ACCESS_TOKEN_BUFFER_MS
        Log.d(
            TAG,
            "isAccessTokenExpired=$isExpired — now=$now," +
                " expiry=$expiry," +
                " buffer=$ACCESS_TOKEN_BUFFER_MS," +
                " remainingMs=${expiry - now}",
        )
        return isExpired
    }

    override suspend fun isRefreshTokenExpired(): Boolean {
        val now = System.currentTimeMillis()
        val expiry = dataStore.data.map { it[REFRESH_TOKEN_EXPIRY] ?: 0L }.first()
        val isExpired = now >= expiry
        Log.d(
            TAG,
            "isRefreshTokenExpired=$isExpired — now=$now, expiry=$expiry, remainingMs=${expiry - now}",
        )
        return isExpired
    }

    override suspend fun clearTokens() {
        Log.d(TAG, "Clearing all tokens")
        dataStore.edit { preferences ->
            preferences.remove(ACCESS_TOKEN)
            preferences.remove(REFRESH_TOKEN)
            preferences.remove(ACCESS_TOKEN_EXPIRY)
            preferences.remove(REFRESH_TOKEN_EXPIRY)
        }
        Log.d(TAG, "Tokens cleared")
    }
}
