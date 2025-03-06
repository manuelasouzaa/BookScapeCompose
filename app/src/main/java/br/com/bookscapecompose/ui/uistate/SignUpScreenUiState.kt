package br.com.bookscapecompose.ui.uistate

data class SignUpScreenUiState(
    val email: String = "",
    val onEmailChange: (String) -> Unit = {},
    val username: String = "",
    val onUsernameChange: (String) -> Unit = {},
    val password: String = "",
    val onPasswordChange: (String) -> Unit = {},
)