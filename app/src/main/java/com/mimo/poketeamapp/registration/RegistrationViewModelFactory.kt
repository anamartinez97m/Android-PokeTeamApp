package com.mimo.poketeamapp.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mimo.poketeamapp.data.RegistrationDataSource
import com.mimo.poketeamapp.data.RegistrationRepository
import com.mimo.poketeamapp.database.AppDatabase

class RegistrationViewModelFactory(private val db: AppDatabase) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegistrationViewModel::class.java)) {
            return RegistrationViewModel(
                registrationRepository = RegistrationRepository(
                    dataSource = RegistrationDataSource(db)
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}