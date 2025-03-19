package br.com.bookscapecompose.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.bookscapecompose.model.Book
import br.com.bookscapecompose.ui.repositories.SavedBookRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class BookListViewModel(private val savedBookRepository: SavedBookRepository) : ViewModel() {

    val bookList = savedBookRepository.bookList

    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    fun showBooks() {
        viewModelScope.launch {
            _loading.emit(true)

            runBlocking { savedBookRepository.showBooks() }

            _loading.emit(false)
        }
    }

    fun sendBook(book: Book) {
        viewModelScope.launch(IO) {
            savedBookRepository.clearBookMessage()
            savedBookRepository.sendBook(book)
        }
    }

}