package br.com.bookscapecompose.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import br.com.bookscapecompose.model.Book
import br.com.bookscapecompose.ui.navigation.UserPreferences
import br.com.bookscapecompose.ui.repositories.BookRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class SearchViewModel(
    private val bookRepository: BookRepository,
    preferences: UserPreferences,
) : ViewModel() {

    val userEmail = preferences.userEmail.asLiveData()

    val foundBooks = bookRepository.foundBooks

    fun sendBook(book: Book) {
        viewModelScope.launch(IO) {
            bookRepository.sendBook(book)
        }
    }

    fun cleanApiAnswer() {
        viewModelScope.launch(IO) {
            bookRepository.cleanApiAnswer()
            bookRepository.updateApiAnswerState(ApiAnswer.Initial)
        }
    }

}