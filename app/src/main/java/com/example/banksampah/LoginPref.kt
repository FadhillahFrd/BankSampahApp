package com.example.banksampah

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LoginPref private constructor(private val dataStore: DataStore<Preferences>) {

    fun getUser(): Flow<DataLogin> {
        return dataStore.data.map { preferences ->
            DataLogin(
                preferences[USERNAME_KEY] ?: "",
                preferences[NAMA_KEY] ?: "",
                preferences[TIPE_USER_KEY] ?: "",
                preferences[NOMOR_HP_KEY] ?: ""
                )
        }
    }

    suspend fun login(dataLogin: DataLogin) {
        dataStore.edit { preferences ->
            preferences[NAMA_KEY] = dataLogin.nama
            preferences[USERNAME_KEY] = dataLogin.username
            preferences[TIPE_USER_KEY] = dataLogin.tipeUser
            preferences[NOMOR_HP_KEY] = dataLogin.noHp
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: LoginPref? = null

        private val NAMA_KEY = stringPreferencesKey("nama")
        private val USERNAME_KEY = stringPreferencesKey("username")
        private val TIPE_USER_KEY = stringPreferencesKey("tipeUser")
        private val NOMOR_HP_KEY = stringPreferencesKey("noHp")

        fun getInstance(dataStore: DataStore<Preferences>): LoginPref {
            return INSTANCE ?: synchronized(this) {
                val instance = LoginPref(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}