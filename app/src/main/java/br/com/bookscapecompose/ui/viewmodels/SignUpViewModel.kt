package br.com.bookscapecompose.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.bookscapecompose.model.User
import br.com.bookscapecompose.ui.repositories.UserRepository
import br.com.bookscapecompose.ui.uistate.SignUpScreenUiState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<SignUpScreenUiState> =
        MutableStateFlow(SignUpScreenUiState())
    val uiState = _uiState.asStateFlow()

    private val _signUpMessage: MutableStateFlow<SignUpMessage> =
        MutableStateFlow(SignUpMessage.Initial)
    val signUpMessage = _signUpMessage.asStateFlow()

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onEmailChange = {
                    _uiState.value = _uiState.value.copy(
                        email = it
                    )
                },
                onUsernameChange = {
                    _uiState.value = _uiState.value.copy(
                        username = it
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

    fun addNewUser(email: String, username: String, password: String): SignUpMessage {
        viewModelScope.launch(IO) {
            try {
                when {
                    email.isEmpty() || username.isEmpty() || password.isEmpty() ->
                        _signUpMessage.emit(SignUpMessage.MissingInformation)

                    email.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty() -> {
                        val existentUser = userRepository.fetchUserByEmail(email)

                        if (existentUser != null)
                            _signUpMessage.emit(SignUpMessage.UserIsAlreadyAdded)

                        if (existentUser == null) {
                            val user = User(email, username, password)
                            userRepository.addUser(user)
                            _signUpMessage.emit(SignUpMessage.UserSuccessfullyAdded)
                        }
                    }
                }
            } catch (e: Exception) {
                _signUpMessage.emit(SignUpMessage.Error)
            }
        }
        return signUpMessage.value
    }

    fun clearSignUpMessage() {
        viewModelScope.launch {
            _signUpMessage.emit(SignUpMessage.Initial)
            _uiState.update { state ->
                state.copy(
                    email = "",
                    username = "",
                    password = ""
                )
            }
        }
    }
}

sealed class SignUpMessage {
    data object Initial : SignUpMessage()
    data object UserIsAlreadyAdded : SignUpMessage()
    data object UserSuccessfullyAdded : SignUpMessage()
    data object MissingInformation : SignUpMessage()
    data object Error : SignUpMessage()
}