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

    @Query("SELECT * FROM USER WHERE email = :email AND password = :password")
    fun getUser(email: String, password: String): User

    @Query("SELECT count(*) FROM USER WHERE email = :email")
    fun doesUserExist(email: String): Int

    @Query("SELECT count(*) FROM USER WHERE email = :email AND password = :password")
    fun doesUserExistWithSamePassword(email: String, password: String): Int

    @Query("UPDATE USER SET password = :password WHERE email = :email")
    fun updateUserPassword(email: String, password: String)
}