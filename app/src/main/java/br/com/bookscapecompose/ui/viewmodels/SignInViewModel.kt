package br.com.bookscapecompose.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.bookscapecompose.ui.repositories.UserRepository
import br.com.bookscapecompose.ui.uistate.SignInScreenUiState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignInViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<SignInScreenUiState> =
        MutableStateFlow(SignInScreenUiState())
    val uiState get() = _uiState.asStateFlow()

    private val _signInMessage: MutableStateFlow<SignInMessage> =
        MutableStateFlow(SignInMessage.Initial)
    val signInMessage = _signInMessage.asStateFlow()

    init {
        viewModelScope.launch(IO) {
            _signInMessage.emit(SignInMessage.Initial)
        }
        _uiState.update { currentState ->
            currentState.copy(
                onEmailChange = {
                    _uiState.value = _uiState.value.copy(
                        email = it
                    )
                },
                onPasswordChange = {
                    _uiState.value = _uiState.value.copy(
                        password = it
                    )
                }
            )
        }
    }

    fun authenticate(email: String, password: String): SignInMessage {
        viewModelScope.launch {
            if (email.isNotEmpty() && password.isNotEmpty()) {
                withContext(IO) {
                    val verification = verifyIfUserExists()
                    if (verification) {
                        val auth = auth(email, password)
                        if (auth)
                            userRepository.login(email)
                    } else {
                        _signInMessage.emit(SignInMessage.UserDoesNotExist)
                    }
                }
            } else
                _signInMessage.emit(SignInMessage.MissingInformation)
        }
        return signInMessage.value
    }

    private fun verifyIfUserExists(): Boolean {
        val searchedUser = this.userRepository.fetchUserByEmail(uiState.value.email)
        return searchedUser != null
    }

    private suspend fun auth(email: String, password: String): Boolean {
        val userAuth = this.userRepository.fetchAndAuthenticateUser(email, password)
        if (userAuth == null) _signInMessage.emit(SignInMessage.WrongPassword)
        else _signInMessage.emit(SignInMessage.UserLoggedIn)
        return userAuth != null
    }

    fun clearSignInMessage() {
        viewModelScope.launch {
            _signInMessage.emit(SignInMessage.Initial)
            _uiState.update { state ->
                state.copy(
                    email = "",
                    password = ""
                )
            }
        }
    }
}

sealed class SignInMessage {
    data object Initial : SignInMessage()
    data object UserDoesNotExist : SignInMessage()
    data object WrongPassword : SignInMessage()
    data object MissingInformation : SignInMessage()
    data object UserLoggedIn : SignInMessage()
    data object Error : SignInMessage()
}