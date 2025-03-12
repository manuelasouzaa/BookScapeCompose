package br.com.bookscapecompose.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.bookscapecompose.model.Book
import br.com.bookscapecompose.model.SavedBook
import br.com.bookscapecompose.ui.repositories.BookRepositoryImpl
import br.com.bookscapecompose.ui.uistate.MainScreenUiState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
                _apiAnswer.emit(ApiAnswer.BookList)
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
        val userEmail = "manuela.souza@gmail.com"
        viewModelScope.launch(IO) {
            clickedBook.value?.let { book ->
                val isBookAlreadySaved = verifyIfBookIsSaved(context, book.id)

                when {
                    isBookAlreadySaved == BookMessage.AddedBook ->
                        return@launch

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

    fun verifyClickedBook(context: Context): Book? {
        var bookDetails: Book? = null
        viewModelScope.launch {
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

    suspend fun showBooks(context: Context): List<Book?> {
        return withContext(IO) {
            val savedList = repository.showBooks(context, "manuela.souza@gmail.com")
            formattingList(savedList)
        }
    }

    private fun formattingList(savedList: List<SavedBook?>): List<Book?> {
        return savedList.map { savedBook: SavedBook? ->
            savedBook?.let { it: SavedBook ->
                Book(
                    it.bookApiId,
                    it.bookTitle,
                    it.bookAuthor,
                    it.bookDescription,
                    it.bookImage,
                    it.bookLink
                )
            }
        }
    }

    fun cleanApiAnswer() {
        viewModelScope.launch {
            _apiAnswer.emit(ApiAnswer.Initial)
        }
    }
}

sealed class BookMessage {
    data object Initial : BookMessage()
    data object AddedBook : BookMessage()
    data object NotSavedBook : BookMessage()
    data object Error : BookMessage()
}

sealed class ApiAnswer {
    data object Loading : ApiAnswer()
    data object EmptyList : ApiAnswer()
    data object BookList : ApiAnswer()
    data object Initial : ApiAnswer()
    data object Error : ApiAnswer()
}