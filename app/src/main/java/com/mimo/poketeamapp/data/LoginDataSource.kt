package com.mimo.poketeamapp.data

import com.mimo.poketeamapp.database.AppDatabase
import java.io.IOException

class LoginDataSource(private val db: AppDatabase) {

    fun login(username: String, password: String): Result<*> {
        return try {
            val userDao = db.userDao()
            val user = userDao.getUser(username, password)
            Result.Success(user.name.toString())
        } catch (e: Throwable) {
            Result.Error(IOException("Error logging in", e))
        }
    }
}