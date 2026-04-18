package com.haykor.calendar.feature.auth.domain.service

import com.haykor.calendar.feature.auth.domain.model.GoogleSignInResult

interface GoogleSignInClient {
    suspend fun signIn(): GoogleSignInResult
}
