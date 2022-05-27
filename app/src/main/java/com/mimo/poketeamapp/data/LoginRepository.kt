package com.mimo.poketeamapp.data

import com.mimo.poketeamapp.data.model.LoggedInUser
import com.mimo.poketeamapp.database.entity.User

class LoginRepository(val dataSource: LoginDataSource) {

    private var loggedInUser: LoggedInUser? = null

    fun login(username: String, password: String): Result<LoggedInUser> {
        val result = dataSource.login(username, password)

        if (result is Result.Success) {
            setLoggedInUser(result.data)
        }

        return result
    }

    private fun setLoggedInUser(user: LoggedInUser) {
        this.loggedInUser = user
    }
}