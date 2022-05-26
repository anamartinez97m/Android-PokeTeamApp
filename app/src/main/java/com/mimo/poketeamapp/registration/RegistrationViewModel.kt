package com.mimo.poketeamapp.registration

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mimo.poketeamapp.R
import com.mimo.poketeamapp.data.RegistrationRepository
import com.mimo.poketeamapp.data.Result

class RegistrationViewModel(private val registrationRepository: RegistrationRepository): ViewModel() {

    private val _registrationFormState = MutableLiveData<RegistrationFormState>()
    val registrationFormState: LiveData<RegistrationFormState> = _registrationFormState

    private val _registrationResult = MutableLiveData<RegistrationResult>()
    val registrationResult: LiveData<RegistrationResult> = _registrationResult

    fun registrate(name: String, surname: String, email: String, password: String) {
        val result = registrationRepository.registrate(name, surname, email, password)

        if (result is Result.Success) {
            _registrationResult.value = RegistrationResult(success = R.string.registration_correct)
        } else {
            _registrationResult.value = RegistrationResult(error = R.string.registration_failed)
        }
    }

    fun registrationDataChanged(name: String, surname: String, email: String, password: String, repeatedPassword: String) {
        if (!isEmailValid(email)) {
            _registrationFormState.value = RegistrationFormState(emailError = R.string.invalid_email)
        } else if (!isPasswordValid(password)) {
            _registrationFormState.value = RegistrationFormState(passwordError = R.string.invalid_password)
        } else if (!isRepeatedPasswordValid(password, repeatedPassword)) {
            _registrationFormState.value = RegistrationFormState(repeatedPasswordError = R.string.invalid_repeated_password)
        } else {
            _registrationFormState.value = RegistrationFormState(isDataValid = true)
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

data class RegistrationResult(
    val success: Int? = null,
    val error: Int? = null
)