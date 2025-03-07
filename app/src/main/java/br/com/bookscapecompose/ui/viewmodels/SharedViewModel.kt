package br.com.bookscapecompose.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import br.com.bookscapecompose.model.Book
import br.com.bookscapecompose.ui.repositories.BookRepositoryImpl
import br.com.bookscapecompose.ui.uistate.MainScreenUiState
import br.com.bookscapecompose.web.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

class SharedViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<MainScreenUiState> =
        MutableStateFlow(MainScreenUiState())
    val uiState get() = _uiState.asStateFlow()

    private val _booklist: MutableStateFlow<List<Book?>> =
        MutableStateFlow(emptyList())
    val bookList = _booklist.asStateFlow()

    private val _clickedBook: MutableStateFlow<Book?> =
        MutableStateFlow(null)
    val clickedBook = _clickedBook.asStateFlow()

    private val repository = BookRepositoryImpl()

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
    }

    suspend fun searchBooks(searchText: String) {
        withContext(IO) {
            val bookList = repository.verifyApiAnswer(searchText)

            when {
                bookList.isNullOrEmpty() -> {
                    _booklist.update { emptyList() }
                    cleanTextField()
                }

                bookList.isNotEmpty() ->
                    _booklist.emit(bookList)
            }
        }
    }

    fun cleanTextField() {
        _uiState.update { currentState ->
            currentState.copy(
                searchText = ""
            )
        }
    }

    suspend fun sendBook(book: Book) {
        _clickedBook.emit(book)
    }

    suspend fun saveBook(context: Context) {
        val userEmail = "teste@gmail.com"
        clickedBook.value?.let { book ->
            withContext(IO) {
                repository.saveBook(context, book, userEmail)
            }
        }
    }
}