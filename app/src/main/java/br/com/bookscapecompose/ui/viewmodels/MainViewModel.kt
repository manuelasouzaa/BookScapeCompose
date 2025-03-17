package br.com.bookscapecompose.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import br.com.bookscapecompose.ui.navigation.UserPreferences
import br.com.bookscapecompose.ui.repositories.BookRepository
import br.com.bookscapecompose.ui.uistate.MainScreenUiState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val bookRepository: BookRepository,
    preferences: UserPreferences,
) : ViewModel() {

    private val _uiState: MutableStateFlow<MainScreenUiState> =
        MutableStateFlow(MainScreenUiState())
    val uiState get() = _uiState.asStateFlow()

    private val _googleApiAnswer: MutableStateFlow<GoogleApiAnswer> =
        MutableStateFlow(GoogleApiAnswer.Initial)
    val googleApiAnswer = _googleApiAnswer.asStateFlow()

    val userEmail = preferences.userEmail.asLiveData()


    init {
        _uiState.update { currentState ->
            currentState.copy(
                onSearchChange = {
                    _uiState.value = _uiState.value.copy(
                        searchText = it
                    )
                }
            )
        }

        viewModelScope.launch(IO) {
            _googleApiAnswer.emit(GoogleApiAnswer.Initial)
        }
    }

    suspend fun searchBooks(searchText: String) {
        withContext(IO) {
            _googleApiAnswer.emit(GoogleApiAnswer.Loading)
            searchingBooks(searchText)
        }
    }

    private suspend fun searchingBooks(searchText: String): GoogleApiAnswer {
        val bookList = bookRepository.verifyApiAnswer(searchText)
        when {
            bookList.isNullOrEmpty() -> {
                _googleApiAnswer.emit(GoogleApiAnswer.EmptyList)
                cleanTextField()
            }

            bookList.isNotEmpty() -> {
                _googleApiAnswer.emit(GoogleApiAnswer.Success)
                cleanTextField()
            }
        }
        return googleApiAnswer.value
    }

    private fun cleanTextField() {
        viewModelScope.launch(IO) {
            _uiState.update { currentState ->
                currentState.copy(
                    searchText = ""
                )
            }
        }
    }
}

sealed class GoogleApiAnswer {
    data object Loading : GoogleApiAnswer()
    data object EmptyList : GoogleApiAnswer()
    data object Success : GoogleApiAnswer()
    data object Initial : GoogleApiAnswer()
    data object Error : GoogleApiAnswer()
}