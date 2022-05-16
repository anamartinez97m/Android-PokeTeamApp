package com.mimo.poketeamapp.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.mimo.poketeamapp.database.entity.User

@Dao
interface UserDao {
    @Query("SELECT * FROM USER")
    fun getAll(): List<User>
}