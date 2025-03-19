package br.com.bookscapecompose.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.bookscapecompose.model.Book
import br.com.bookscapecompose.ui.repositories.BookRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookDetailsViewModel(private val bookRepository: BookRepository) : ViewModel() {

    private val _bookMessage: MutableStateFlow<BookMessage> = MutableStateFlow(BookMessage.Initial)
    val bookMessage = _bookMessage.asStateFlow()

    fun saveBook(): BookMessage {
        viewModelScope.launch(IO) {
            try {
                when (verifyIfBookIsSaved()) {
                    BookMessage.NotSavedBook -> {
                        val isBookSaved = bookRepository.saveBook()
                        if (isBookSaved) _bookMessage.emit(BookMessage.AddedBook)
                    }

                    BookMessage.AddedBook ->
                        return@launch

                    else -> {}
                }
            } catch (e: Exception) {
                _bookMessage.emit(BookMessage.Error)
            }
        }
        return bookMessage.value
    }

    fun verifyClickedBookValue(): Book? {
        return bookRepository.clickedBook.value?.let { book ->
            viewModelScope.launch(IO) {
                verifyIfBookIsSaved()
            }
            book
        }
    }

    private suspend fun verifyIfBookIsSaved(): BookMessage {
        val isBookSaved = bookRepository.verifyIfBookIsSaved()

        if (isBookSaved) _bookMessage.emit(BookMessage.AddedBook)
        if (!isBookSaved) _bookMessage.emit(BookMessage.NotSavedBook)

        return _bookMessage.value
    }

    fun clearClickedBook() {
        viewModelScope.launch(IO) {
            bookRepository.clearClickedBook()
        }
    }
}

sealed class BookMessage {
    data object Initial : BookMessage()
    data object AddedBook : BookMessage()
    data object NotSavedBook : BookMessage()
    data object Error : BookMessage()
}