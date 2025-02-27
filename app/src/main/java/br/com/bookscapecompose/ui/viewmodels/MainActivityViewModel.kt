package br.com.bookscapecompose.ui.viewmodels

import androidx.lifecycle.ViewModel
import br.com.bookscapecompose.model.Book
import br.com.bookscapecompose.ui.uistate.MainScreenUiState
import br.com.bookscapecompose.web.json.GoogleApiAnswer
import br.com.bookscapecompose.web.retrofit.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainActivityViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<MainScreenUiState> =
        MutableStateFlow(MainScreenUiState())
    val uiState get() = _uiState.asStateFlow()

    private val _booklist: MutableStateFlow<List<Book?>> = MutableStateFlow(emptyList())
    val bookList = _booklist.asStateFlow()

    private val service by lazy {
        RetrofitInstance.api
    }

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
        val answer = service.getBooks(search = searchText)
        val bookList = verifyAnswer(answer)

        //TODO: to verify if the list is null

        bookList?.let { _booklist.emit(bookList) }
    }


    private fun verifyAnswer(answer: GoogleApiAnswer): List<Book?>? {
        return when {
            answer.totalItems == 0 ->
                null

            answer.totalItems > 0 ->
                getList(answer)

            else ->
                null
        }
    }

    private fun getList(answer: GoogleApiAnswer): List<Book?> {
        return answer.items.map { completeBook ->
            completeBook.volumeInfo?.let {
                val book = Book(
                    id = completeBook.id,
                    title = it.title,
                    authors = it.authors.toString(),
                    description = it.description.toString(),
                    image = it.imageLinks?.thumbnail,
                    link = it.infoLink.orEmpty()
                )
                book
            }
        }
    }
}