package com.mimo.poketeamapp.data

import com.mimo.poketeamapp.model.LoggedInUser
import com.mimo.poketeamapp.database.AppDatabase
import java.io.IOException

class LoginDataSource(private val db: AppDatabase) {

    fun login(username: String, password: String): Result<LoggedInUser> {
        return try {
            val userDao = db.userDao()
            val user = userDao.getUser(username, password)
            val loggedInUser = LoggedInUser(user.id)
            Result.Success(loggedInUser)
        } catch (e: Throwable) {
            Result.Error(IOException("Error logging in", e))
        }
    }
}