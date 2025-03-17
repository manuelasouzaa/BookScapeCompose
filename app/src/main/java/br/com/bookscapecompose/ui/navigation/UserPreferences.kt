package br.com.bookscapecompose.ui.navigation

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

const val preferences = "user_preferences"
const val loggedState = "logged_user"
const val loggedEmail = "user_email"

class UserPreferences(private val context: Context) {
    private val Context.dataStore by preferencesDataStore(preferences)

    companion object {
        val loggedKey = booleanPreferencesKey(loggedState)
        val emailKey = stringPreferencesKey(loggedEmail)
    }

    val state = context.dataStore.data.map { preferences ->
        preferences[loggedKey]
    }

    val userEmail = context.dataStore.data.map { preferences ->
        preferences[emailKey]
    }

    suspend fun updateLoggedState(isUserLogged: Boolean) =
        context.dataStore.edit { preferences ->
            preferences[loggedKey] = isUserLogged
        }

    suspend fun updateUserEmail(email: String) =
        context.dataStore.edit { preferences ->
            preferences[emailKey] = email
        }

    suspend fun logout() =
        context.dataStore.edit { preferences ->
            preferences[loggedKey] = false
            preferences[emailKey] = ""
        }
}
