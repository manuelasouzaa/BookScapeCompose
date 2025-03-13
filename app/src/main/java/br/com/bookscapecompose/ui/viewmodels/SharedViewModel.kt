package br.com.bookscapecompose.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.bookscapecompose.model.Book
import br.com.bookscapecompose.ui.repositories.BookRepositoryImpl
import br.com.bookscapecompose.ui.uistate.MainScreenUiState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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

    private val _apiAnswer: MutableStateFlow<ApiAnswer> =
        MutableStateFlow(ApiAnswer.Initial)
    val apiAnswer = _apiAnswer.asStateFlow()

    private val _loading: MutableStateFlow<Boolean> =
        MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val repository = BookRepositoryImpl()

    val userEmail = "manuela.souza@gmail.com"

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
            _bookMessage.emit(BookMessage.Initial)
            _apiAnswer.emit(ApiAnswer.Initial)
        }
    }

    suspend fun searchBooks(searchText: String) {
        withContext(IO) {
            _apiAnswer.emit(ApiAnswer.Loading)
            searchingBooks(searchText)
        }
    }

    private suspend fun searchingBooks(searchText: String): ApiAnswer {
        val bookList = repository.verifyApiAnswer(searchText)
        when {
            bookList.isNullOrEmpty() -> {
                _apiAnswer.emit(ApiAnswer.EmptyList)
                _bookList.update { emptyList() }
                cleanTextField()
            }

            bookList.isNotEmpty() -> {
                _bookList.emit(bookList)
                _apiAnswer.emit(ApiAnswer.Success)
            }
        }
        return apiAnswer.value
    }

    fun cleanTextField() {
        viewModelScope.launch(IO) {
            _uiState.update { currentState ->
                currentState.copy(
                    searchText = ""
                )
            }
        }
    }

    fun sendBook(book: Book) {
        viewModelScope.launch(IO) {
            _clickedBook.emit(book)
        }
    }

    fun saveBook(context: Context): BookMessage {
        viewModelScope.launch(IO) {
            clickedBook.value?.let { book ->
                val verification = verifyIfBookIsSaved(context, book.id)

                when (verification) {
                    BookMessage.AddedBook -> return@launch

                    BookMessage.NotSavedBook -> {
                        val isBookSaved = repository.saveBook(context, book, userEmail)
                        if (!isBookSaved) _bookMessage.emit(BookMessage.Error)
                        if (isBookSaved) _bookMessage.emit(BookMessage.AddedBook)
                    }

                    else -> return@launch
                }
            }
        }
        return bookMessage.value
    }

    fun verifyClickedBookValue(context: Context): Book? {
        return clickedBook.value?.let { book ->
            viewModelScope.launch {
                verifyIfBookIsSaved(context, book.id)
            }
            book
        }
    }

    private suspend fun verifyIfBookIsSaved(context: Context, id: String): BookMessage {
        val isBookSaved = repository.verifyIfBookIsSaved(context, id, userEmail)
        if (isBookSaved)
            _bookMessage.emit(BookMessage.AddedBook)
        if (!isBookSaved)
            _bookMessage.emit(BookMessage.NotSavedBook)
        return _bookMessage.value
    }

    fun showBooks(context: Context) {
        viewModelScope.launch {
            _loading.emit(true)

            runBlocking { loadingSavedBooks(context) }

            _loading.emit(false)
        }
    }

    private suspend fun loadingSavedBooks(context: Context): List<Book?> {
        return withContext(IO) {
            val books = repository.showBooks(context, userEmail)
            _bookList.emit(books)
            books
        }
    }

    fun cleanApiAnswer() {
        viewModelScope.launch {
            _apiAnswer.emit(ApiAnswer.Initial)
        }
    }

    fun deleteBook(context: Context): BookMessage {
        viewModelScope.launch {
            clickedBook.value?.let {
                runBlocking {
                    deletingBook(context, it)
                }
            } ?: _bookMessage.emit(BookMessage.Error)
        }
        return bookMessage.value
    }

    private suspend fun deletingBook(context: Context, it: Book) {
        withContext(IO) {
            val wasBookDeleted = repository.deleteBook(context, it.id, userEmail)
            if (wasBookDeleted) {
                _bookMessage.emit(BookMessage.DeletedBook)
                loadingSavedBooks(context)
            } else
                _bookMessage.emit(BookMessage.Error)
        }
    }
}

sealed class BookMessage {
    data object Initial : BookMessage()
    data object AddedBook : BookMessage()
    data object NotSavedBook : BookMessage()
    data object Error : BookMessage()
    data object DeletedBook : BookMessage()
}

sealed class ApiAnswer {
    data object Loading : ApiAnswer()
    data object EmptyList : ApiAnswer()
    data object Success : ApiAnswer()
    data object Initial : ApiAnswer()
    data object Error : ApiAnswer()
}