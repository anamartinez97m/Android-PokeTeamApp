package com.mimo.poketeamapp.data

class LoginRepository(val dataSource: LoginDataSource) {

    fun login(username: String, password: String): Result<*> {
        return dataSource.login(username, password)
    }
}