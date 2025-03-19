package br.com.bookscapecompose.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.bookscapecompose.model.User
import br.com.bookscapecompose.ui.repositories.UserRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AccountViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val loading = _loading.asStateFlow()

    private val _user: MutableStateFlow<User?> = MutableStateFlow(null)
    val user = _user.asStateFlow()

    fun account() {
        viewModelScope.launch(IO) {
            searchingUser()
            _loading.emit(false)
        }
    }

    private suspend fun searchingUser() {
        _user.emit(userRepository.fetchUserByEmailPreference())
    }

    fun logout() {
        viewModelScope.launch(IO) {
            userRepository.logout()
        }
    }

}