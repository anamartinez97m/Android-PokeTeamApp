package com.mimo.poketeamapp.registration

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mimo.poketeamapp.data.RegistrationDataSource
import com.mimo.poketeamapp.data.RegistrationRepository

class RegistrationViewModelFactory: ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegistrationViewModel::class.java)) {
            return RegistrationViewModel(
                registrationRepository = RegistrationRepository(
                    dataSource = RegistrationDataSource()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}