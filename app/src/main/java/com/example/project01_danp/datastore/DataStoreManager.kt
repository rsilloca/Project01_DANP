package com.example.project01_danp.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(private val context: Context) {

    suspend fun setUserPIN(newPIN: String) {
        context.dataStoreManager.edit { preferences ->
            preferences[USER_PIN_KEY] = newPIN
        }
    }

    val userPIN: Flow<String>
        get() = context.dataStoreManager.data.map { preferences ->
            preferences[USER_PIN_KEY] ?: ""
        }


    suspend fun setUserEmail(newEmail: String) {
        context.dataStoreManager.edit { preferences ->
            preferences[USER_EMAIL_KEY] = newEmail
        }
    }

    val userEmail: Flow<String>
        get() = context.dataStoreManager.data.map { preferences ->
            preferences[USER_EMAIL_KEY] ?: ""
        }


    suspend fun setUserName(newName: String) {
        context.dataStoreManager.edit { preferences ->
            preferences[USER_NAME_KEY] = newName
        }
    }

    val userName: Flow<String>
        get() = context.dataStoreManager.data.map { preferences ->
            preferences[USER_NAME_KEY] ?: ""
        }


    suspend fun setFontFamily(newFontFamily: String) {
        context.dataStoreManager.edit { preferences ->
            preferences[FONT_FAMILY_KEY] = newFontFamily
        }
    }

    val fontFamily: Flow<String>
        get() = context.dataStoreManager.data.map { preferences ->
            preferences[FONT_FAMILY_KEY] ?: "Nunito"
        }


    suspend fun setTheme(newTheme: String) {
        context.dataStoreManager.edit { preferences ->
            preferences[THEME_KEY] = newTheme
        }
    }

    val theme: Flow<String>
        get() = context.dataStoreManager.data.map { preferences ->
            preferences[THEME_KEY] ?: "Light"
        }


    suspend fun setLanguage(newLanguage: String) {
        context.dataStoreManager.edit { preferences ->
            preferences[LANGUAGE_KEY] = newLanguage
        }
    }

    val language: Flow<String>
        get() = context.dataStoreManager.data.map { preferences ->
            preferences[LANGUAGE_KEY] ?: "Español"
        }

    companion object {
        private const val DATASTORE_NAME = "colaboremos_pe_prefs"

        private val USER_PIN_KEY = stringPreferencesKey("user_pin_key")
        private val USER_EMAIL_KEY = stringPreferencesKey("user_email_key")
        private val USER_NAME_KEY = stringPreferencesKey("user_name_key")
        private val FONT_FAMILY_KEY = stringPreferencesKey("font_family_key")
        private val THEME_KEY = stringPreferencesKey("theme_key")
        private val LANGUAGE_KEY = stringPreferencesKey("language_key")

        private val Context.dataStoreManager by preferencesDataStore(
            name = DATASTORE_NAME
        )
    }
}
