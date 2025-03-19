package br.com.bookscapecompose.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.bookscapecompose.model.Book
import br.com.bookscapecompose.ui.repositories.SavedBookRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class SavedBookDetailsViewModel(private val repository: SavedBookRepository) : ViewModel() {

    val savedBookMessage = repository.savedBookMessage

    fun verifySavedClickedBookValue(): Book? =
        repository.clickedBook.value?.let { book ->
            viewModelScope.launch(IO) {
                repository.verifyIfBookIsSaved()
            }
            return book
        }

    fun deleteBook() {
        viewModelScope.launch(IO) {
            repository.deleteBook()
        }
    }

    fun saveBook() {
        viewModelScope.launch(IO) {
            repository.saveBook()
        }
    }

}

sealed class SavedBookMessage {
    data object Initial : SavedBookMessage()
    data object AddedBook : SavedBookMessage()
    data object NotSavedBook : SavedBookMessage()
    data object Error : SavedBookMessage()
    data object DeletedBook : SavedBookMessage()
}