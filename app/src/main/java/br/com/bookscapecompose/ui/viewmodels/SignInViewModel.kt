package br.com.bookscapecompose.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.bookscapecompose.ui.repositories.UserRepositoryImpl
import br.com.bookscapecompose.ui.uistate.SignInScreenUiState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel() {

    private val repository = UserRepositoryImpl()

    private val _uiState: MutableStateFlow<SignInScreenUiState> =
        MutableStateFlow(SignInScreenUiState())
    val uiState get() = _uiState.asStateFlow()

    private val _signInMessage: MutableStateFlow<SignInMessage> =
        MutableStateFlow(SignInMessage.Initial)

    private val message = _signInMessage.asStateFlow()

    init {
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

    fun authenticate(context: Context, email: String, password: String): SignInMessage {
        viewModelScope.launch(IO) {
            when {
                email.isEmpty() || password.isEmpty() ->
                    _signInMessage.emit(SignInMessage.MissingInformation)

                email.isNotEmpty() && password.isNotEmpty() -> {
                    val verification = verifyIfUserExists(context, email)

                    if (!verification)
                        _signInMessage.emit(SignInMessage.UserDoesNotExist)

                    if (verification) {
                        try {
                            auth(context, email, password)
                        } catch (e: Exception) {
                            _signInMessage.emit(SignInMessage.Error)
                        }
                    }
                }

                else -> {
                    _signInMessage.emit(SignInMessage.Initial)
                }
            }
        }
        return message.value
    }

    private suspend fun auth(
        context: Context,
        email: String,
        password: String,
    ) {
        val userAuth = repository.fetchAndAuthenticateUser(context, email, password)
        if (userAuth != null) {
            _signInMessage.emit(SignInMessage.UserLoggedIn)
        }
        if (userAuth == null) {
            _signInMessage.emit(SignInMessage.WrongPassword)
        }
    }

    private fun verifyIfUserExists(context: Context, email: String): Boolean {
        var doesUserAlreadyExist = false
        val searchedUser = repository.fetchUserByEmail(context, email)
        if (searchedUser != null) {
            doesUserAlreadyExist = true
        }
        return doesUserAlreadyExist
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