package br.com.bookscapecompose.ui.uistate

data class SignInScreenUiState(
    val email: String = "",
    val onEmailChange: (String) -> Unit = {},
    val password: String = "",
    val onPasswordChange: (String) -> Unit = {},
)