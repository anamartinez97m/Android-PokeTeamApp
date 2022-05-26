package com.mimo.poketeamapp.forgotPassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mimo.poketeamapp.data.ForgotPasswordDataSource
import com.mimo.poketeamapp.data.ForgotPasswordRepository
import com.mimo.poketeamapp.database.AppDatabase

class ForgotPasswordViewModelFactory(private val db: AppDatabase) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ForgotPasswordViewModel::class.java)) {
            return ForgotPasswordViewModel(
                forgotPasswordRepository = ForgotPasswordRepository(
                    dataSource = ForgotPasswordDataSource(db)
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
