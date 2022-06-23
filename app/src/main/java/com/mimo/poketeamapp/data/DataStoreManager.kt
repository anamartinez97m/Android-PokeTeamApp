package com.mimo.poketeamapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.mimo.poketeamapp.model.UserModel
import kotlinx.coroutines.flow.map

const val USER_DATASTORE = "datastore"

class DataStoreManager(val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_DATASTORE)

    companion object {
        val ID = stringPreferencesKey("USER_ID")
        val EMAIL = stringPreferencesKey("USER_EMAIL")
        val IMAGE = stringPreferencesKey("USER_IMAGE")
    }

    suspend fun saveToDataStore(user: UserModel) {
        context.dataStore.edit {
            it[ID] = user.id
            it[EMAIL] = user.email
            it[IMAGE] = user.image
        }
    }

    suspend fun getFromDataStore() = context.dataStore.data.map {
        UserModel(
            id = it[ID] ?: "a",
            email = it[EMAIL] ?: "b",
            image = it[IMAGE] ?: "c",
        )
    }
}