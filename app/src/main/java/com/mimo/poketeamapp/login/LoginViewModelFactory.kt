package com.mimo.poketeamapp.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mimo.poketeamapp.data.LoginDataSource
import com.mimo.poketeamapp.data.LoginRepository
import com.mimo.poketeamapp.database.AppDatabase

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class LoginViewModelFactory(private val db: AppDatabase) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                    loginRepository = LoginRepository(
                            dataSource = LoginDataSource(db)
                    )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}