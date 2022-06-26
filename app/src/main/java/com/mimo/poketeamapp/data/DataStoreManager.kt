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


//    private val dataStore = context.globalDataStore(name = "settings_pref")

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_DATASTORE)

        val ID = stringPreferencesKey("USER_ID")
        val IMAGE = stringPreferencesKey("USER_IMAGE")
        val LANGUAGE = stringPreferencesKey("LANGUAGE")
    }

    suspend fun saveToDataStore(user: UserModel) {
        context.dataStore.edit {
            it[ID] = user.id
            it[IMAGE] = user.image
            it[LANGUAGE] = user.preferredLanguage
        }
    }

    suspend fun getFromDataStore() = context.dataStore.data.map {
        UserModel(
            id = it[ID] ?: "a",
            image = it[IMAGE] ?: "b",
            preferredLanguage = it[LANGUAGE] ?: "c",
        )
    }
}