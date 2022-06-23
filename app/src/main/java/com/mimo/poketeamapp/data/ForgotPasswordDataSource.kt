package com.mimo.poketeamapp.data

import android.util.Log
import com.mimo.poketeamapp.database.AppDatabase
import com.mimo.poketeamapp.database.entity.User
import java.io.IOException

class ForgotPasswordDataSource(private val db: AppDatabase) {

    fun updatePassword(email: String, password: String): Result<*> {
        return try {
            val userDao = db.userDao()

            if(db.userDao().doesUserExistWithSamePassword(email, password) == 0) {
                userDao.updateUserPasswordFromEmail(email, password)
                Result.Success("Ok")
            } else {
                Result.Error(IOException("Error in registration"))
            }
        } catch (e: Throwable) {
            Result.Error(IOException("Error in registration", e))
        }
    }
}