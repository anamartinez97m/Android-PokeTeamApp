package com.mimo.poketeamapp.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.mimo.poketeamapp.database.entity.User

@Dao
interface UserDao {
    @Query("SELECT * FROM USER")
    fun getAll(): List<User>

    @Query("INSERT INTO USER(name, surname, email, password) VALUES (:name, :surname, :email, :password)")
    fun insertUser(name: String, surname: String, email: String, password: String)
}