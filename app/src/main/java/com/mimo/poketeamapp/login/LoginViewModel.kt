package com.mimo.poketeamapp.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import com.mimo.poketeamapp.data.LoginRepository
import com.mimo.poketeamapp.data.Result

import com.mimo.poketeamapp.R
import com.mimo.poketeamapp.data.model.LoggedInUser

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    private val _loggedInUser = MutableLiveData<LoggedInUser>()
    val loggedInUser: LiveData<LoggedInUser> = _loggedInUser

    fun login(username: String, password: String) {
        val result = loginRepository.login(username, password)

        if (result is Result.Success) {
            _loggedInUser.value = LoggedInUser(result.data.userId)
            _loginResult.value = LoginResult(success = R.string.login_correct)
        } else {
            _loginResult.value = LoginResult(error = R.string.login_failed)
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 3
    }
}