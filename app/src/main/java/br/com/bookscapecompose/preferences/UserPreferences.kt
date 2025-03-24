package br.com.bookscapecompose.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
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

    suspend fun updateUserPreferences(userConfig: UserConfig) {
        context.dataStore.edit { preferences ->
            preferences[loggedKey] = userConfig.loggedUserState == true
            preferences[emailKey] = userConfig.loggedUserEmail ?: ""
        }
    }

    fun userPreferences(): Flow<UserConfig> {
        return context.dataStore.data.map { preferences ->
            val loggedStateValue = preferences[loggedKey]
            val loggedEmailValue = preferences[emailKey]

            UserConfig(
                loggedUserEmail = loggedEmailValue,
                loggedUserState = loggedStateValue
            )
        }
    }

    suspend fun logout() {
        context.dataStore.edit { preferences ->
            preferences[loggedKey] = false
            preferences[emailKey] = ""
        }
    }
}

data class UserConfig(
    val loggedUserState: Boolean?,
    val loggedUserEmail: String?,
)