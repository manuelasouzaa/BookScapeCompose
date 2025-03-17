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

    val userEmail = preferences.userEmail.asLiveData()

    val apiAnswer = bookRepository.apiAnswer

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
            bookRepository.updateApiAnswerState(ApiAnswer.Initial)
        }
    }

    suspend fun searchBooks(searchText: String) {
        withContext(IO) {
            bookRepository.updateApiAnswerState(ApiAnswer.Loading)
            searchingBooks(searchText)
        }
    }

    private suspend fun searchingBooks(searchText: String) {
        val bookList = bookRepository.verifyApiAnswer(searchText)
        when {
            bookList.isNullOrEmpty() -> {
                bookRepository.updateApiAnswerState(ApiAnswer.EmptyList)
                cleanTextField()
            }

            bookList.isNotEmpty() -> {
                bookRepository.updateApiAnswerState(ApiAnswer.Success)
                cleanTextField()
            }
        }

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

sealed class ApiAnswer {
    data object Loading : ApiAnswer()
    data object EmptyList : ApiAnswer()
    data object Success : ApiAnswer()
    data object Initial : ApiAnswer()
    data object Error : ApiAnswer()
}