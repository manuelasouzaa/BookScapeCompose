package br.com.bookscapecompose.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import br.com.bookscapecompose.model.Book
import br.com.bookscapecompose.ui.repositories.BookRepositoryImpl
import br.com.bookscapecompose.ui.uistate.MainScreenUiState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class SharedViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<MainScreenUiState> =
        MutableStateFlow(MainScreenUiState())
    val uiState get() = _uiState.asStateFlow()

    private val _bookList: MutableStateFlow<List<Book?>> =
        MutableStateFlow(emptyList())
    val bookList = _bookList.asStateFlow()

    private val _clickedBook: MutableStateFlow<Book?> =
        MutableStateFlow(null)
    private val clickedBook = _clickedBook.asStateFlow()

    private val _bookMessage: MutableStateFlow<BookMessage> =
        MutableStateFlow(BookMessage.Initial)
    val bookMessage = _bookMessage.asStateFlow()

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

        runBlocking {
            _bookMessage.emit(BookMessage.Initial)
        }
    }

    suspend fun searchBooks(searchText: String) {
        withContext(IO) {
            val bookList = repository.verifyApiAnswer(searchText)

            when {
                bookList.isNullOrEmpty() -> {
                    _bookList.update { emptyList() }
                    cleanTextField()
                }

                bookList.isNotEmpty() ->
                    _bookList.emit(bookList)
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

    suspend fun saveBook(context: Context): BookMessage {
        val userEmail = "manuela.souza@gmail.com"
        clickedBook.value?.let { book ->
            withContext(IO) {
                val isBookAlreadySaved = verifyIfBookIsSaved(context, book.id)

                when {
                    isBookAlreadySaved == BookMessage.AddedBook ->
                        return@withContext

                    isBookAlreadySaved != BookMessage.AddedBook -> {
                        val isBookSaved = repository.saveBook(context, book, userEmail)
                        if (!isBookSaved)
                            _bookMessage.emit(BookMessage.Error)
                        if (isBookSaved)
                            _bookMessage.emit(BookMessage.AddedBook)
                    }
                }
            }
        }
        return bookMessage.value
    }

    suspend fun verifyClickedBook(context: Context): Book? {
        var bookDetails: Book? = null
        clickedBook.value?.let { book ->
            bookDetails = Book(
                id = book.id,
                title = book.title,
                authors = book.authors ?: "",
                description = book.description ?: "",
                image = book.image ?: "",
                link = book.link
            )
            bookDetails?.let { verifyIfBookIsSaved(context, it.id) }
        }
        return bookDetails
    }

    private suspend fun verifyIfBookIsSaved(context: Context, id: String): BookMessage {
        withContext(IO) {
            val isBookSaved = repository.verifyIfBookIsSaved(context, id)
            if (isBookSaved)
                _bookMessage.emit(BookMessage.AddedBook)
            if (!isBookSaved)
                _bookMessage.emit(BookMessage.NotSavedBook)
        }
        return _bookMessage.value
    }
}

sealed class BookMessage {
    data object Initial : BookMessage()
    data object AddedBook : BookMessage()
    data object NotSavedBook : BookMessage()
    data object Error : BookMessage()
}