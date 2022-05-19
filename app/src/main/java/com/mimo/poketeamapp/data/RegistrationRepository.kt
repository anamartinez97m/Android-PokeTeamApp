package com.mimo.poketeamapp.data

class RegistrationRepository(private val dataSource: RegistrationDataSource) {

    fun registrate(name: String, surname: String, email: String, password: String): Result<*> {
        return dataSource.registrate(name, surname, email, password)
    }

}