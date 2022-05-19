package com.mimo.poketeamapp.data

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.mimo.poketeamapp.database.AppDatabase
import com.mimo.poketeamapp.database.entity.User
import java.io.IOException

class RegistrationDataSource {

    private val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "pokemon-database").build()

    fun registrate(name: String, surname: String, email: String, password: String): Result<*> {
        try {
            val userDao = db.userDao()
            userDao.insertUser(name, surname, email, password)

            val users: List<User> = userDao.getAll()
            Log.d("users", users.toString())

            return Result.Success("Ok")
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

}