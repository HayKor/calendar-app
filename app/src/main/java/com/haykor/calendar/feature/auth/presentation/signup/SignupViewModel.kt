package com.haykor.calendar.feature.auth.presentation.signup

import androidx.lifecycle.ViewModel
import com.haykor.calendar.feature.auth.domain.usecase.SignupUseCase

class SignupViewModel (
    private val signupUseCase: SignupUseCase,
) : ViewModel() {

}