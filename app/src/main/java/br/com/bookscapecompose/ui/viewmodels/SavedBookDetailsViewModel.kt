package br.com.bookscapecompose.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.bookscapecompose.model.Book
import br.com.bookscapecompose.ui.repositories.SavedBookRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SavedBookDetailsViewModel(private val repository: SavedBookRepository) : ViewModel() {

    val savedBookMessage = repository.savedBookMessage

    fun verifySavedClickedBookValue(): Book? =
        repository.clickedBook.value?.let { book ->
            viewModelScope.launch(IO) {
                runBlocking { repository.verifyIfBookIsSaved() }
            }
            return book
        }

    fun deleteBook(): SavedBookMessage {
        viewModelScope.launch(IO) {
            runBlocking { repository.deleteBook() }
        }
        return savedBookMessage.value
    }

    fun saveBook(): SavedBookMessage {
        viewModelScope.launch(IO) {
            runBlocking { repository.saveBook() }
        }
        return savedBookMessage.value
    }
}

sealed class SavedBookMessage {
    data object Initial : SavedBookMessage()
    data object AddedBook : SavedBookMessage()
    data object NotSavedBook : SavedBookMessage()
    data object Error : SavedBookMessage()
    data object DeletedBook : SavedBookMessage()
}