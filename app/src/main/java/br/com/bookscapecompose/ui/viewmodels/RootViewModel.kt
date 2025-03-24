package br.com.bookscapecompose.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.bookscapecompose.preferences.UserConfig
import br.com.bookscapecompose.preferences.UserPreferences
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RootViewModel(private val userPreferences: UserPreferences) : ViewModel() {

    private val _appState: MutableStateFlow<AppState> =
        MutableStateFlow(AppState.Loading)
    val appState = _appState.asStateFlow()

    suspend fun preferencesState(): AppState {
        val preferences = userPreferences()

        preferences.collect {
            if (it.loggedUserState == true && it.loggedUserEmail != "")
                _appState.emit(AppState.Logged)
            else
                _appState.emit(AppState.NotLogged)
        }

        return appState.value
    }

    fun userPreferences(): Flow<UserConfig> {
        return userPreferences.userPreferences()
    }

    fun updateUserPreferences(userConfig: UserConfig) {
        viewModelScope.launch(IO) {
            userPreferences.updateUserPreferences(userConfig)
        }
    }

    fun logout() {
        viewModelScope.launch(IO) {
            _appState.emit(AppState.NotLogged)
            userPreferences.logout()
        }
    }

}

sealed class AppState {
    data object Logged : AppState()
    data object NotLogged : AppState()
    data object Loading : AppState()
}