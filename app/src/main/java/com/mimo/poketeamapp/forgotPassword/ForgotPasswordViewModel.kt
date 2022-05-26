package com.mimo.poketeamapp.forgotPassword

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mimo.poketeamapp.R
import com.mimo.poketeamapp.data.ForgotPasswordRepository
import com.mimo.poketeamapp.data.Result

class ForgotPasswordViewModel(private val forgotPasswordRepository: ForgotPasswordRepository): ViewModel() {

    private val _forgotPasswordFormState = MutableLiveData<ForgotPasswordFormState>()
    val forgotPasswordFormState: LiveData<ForgotPasswordFormState> = _forgotPasswordFormState

    private val _forgotPasswordResult = MutableLiveData<ForgotPasswordResult>()
    val forgotPasswordResult: LiveData<ForgotPasswordResult> = _forgotPasswordResult

    fun updatePassword(email: String, password: String) {
        val result = forgotPasswordRepository.updatePassword(email, password)

        if (result is Result.Success) {
            _forgotPasswordResult.value = ForgotPasswordResult(success = R.string.registration_correct)
        } else {
            _forgotPasswordResult.value = ForgotPasswordResult(error = R.string.registration_failed)
        }
    }

    fun forgotPasswordDataChanged(email: String, password: String, repeatedPassword: String) {
        if (!isEmailValid(email)) {
            _forgotPasswordFormState.value = ForgotPasswordFormState(emailError = R.string.invalid_email)
        } else if (!isPasswordValid(password)) {
            _forgotPasswordFormState.value = ForgotPasswordFormState(passwordError = R.string.invalid_password)
        } else if (!isRepeatedPasswordValid(password, repeatedPassword)) {
            _forgotPasswordFormState.value = ForgotPasswordFormState(repeatedPasswordError = R.string.invalid_repeated_password)
        } else {
            _forgotPasswordFormState.value = ForgotPasswordFormState(isDataValid = true)
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return if (email.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(email).matches()
        } else {
            email.isNotBlank()
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 3
    }

    private fun isRepeatedPasswordValid(password: String, repeatedPassword: String): Boolean {
        return password == repeatedPassword
    }
}

data class ForgotPasswordResult(
    val success: Int? = null,
    val error: Int? = null
)