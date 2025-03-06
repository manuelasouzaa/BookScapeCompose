package br.com.bookscapecompose.ui.viewmodels

import androidx.lifecycle.ViewModel
import br.com.bookscapecompose.ui.uistate.SignUpScreenUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SignUpViewModel : ViewModel() {

    private val _signUpState: MutableStateFlow<SignUpScreenUiState> =
        MutableStateFlow(SignUpScreenUiState())
    val signUpState = _signUpState.asStateFlow()

}