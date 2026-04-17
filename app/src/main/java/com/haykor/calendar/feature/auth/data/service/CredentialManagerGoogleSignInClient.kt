package com.haykor.calendar.feature.auth.data.service

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.haykor.calendar.feature.auth.domain.model.GoogleSignInResult
import com.haykor.calendar.feature.auth.domain.service.GoogleSignInClient

class CredentialManagerGoogleSignInClient(
    private val context: Context,
    webClientId: String,
) : GoogleSignInClient {
    private val credentialManager = CredentialManager.create(context)

    private val request =
        GetCredentialRequest
            .Builder()
            .addCredentialOption(
                GetGoogleIdOption
                    .Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(webClientId)
                    .build(),
            ).build()

    override suspend fun signIn(): GoogleSignInResult =
        try {
            val result = credentialManager.getCredential(context, request)
            val credential = result.credential
            if (credential is CustomCredential &&
                credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
            ) {
                val idToken = GoogleIdTokenCredential.createFrom(credential.data).idToken
                GoogleSignInResult.Success(idToken)
            } else {
                GoogleSignInResult.Failure
            }
        } catch (e: GetCredentialCancellationException) {
            GoogleSignInResult.Cancelled
        } catch (e: GetCredentialException) {
            GoogleSignInResult.Failure
        }
}
