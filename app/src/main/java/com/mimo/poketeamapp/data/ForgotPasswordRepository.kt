package com.mimo.poketeamapp.data

class ForgotPasswordRepository(private val dataSource: ForgotPasswordDataSource) {

    fun updatePassword(email: String, password: String): Result<*> {
        return dataSource.updatePassword(email, password)
    }
}