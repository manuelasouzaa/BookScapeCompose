package br.com.bookscapecompose.ui.repositories

import android.content.Context
import br.com.bookscapecompose.database.BookScapeDatabase
import br.com.bookscapecompose.model.Book
import br.com.bookscapecompose.model.SavedBook
import br.com.bookscapecompose.ui.viewmodels.ApiAnswer
import br.com.bookscapecompose.ui.viewmodels.RootViewModel
import br.com.bookscapecompose.web.json.GoogleApiAnswer
import br.com.bookscapecompose.web.retrofit.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import java.util.UUID

class BookRepositoryImpl(rootViewModel: RootViewModel, context: Context) : BookRepository {

    private val bookDao = BookScapeDatabase.getDatabaseInstance(context).SavedBookDao()
    private val service by lazy { RetrofitInstance.api }
    override val userPreferences = rootViewModel.userPreferences()

    private val _foundBooks: MutableStateFlow<List<Book?>> = MutableStateFlow(emptyList())
    override val foundBooks = _foundBooks.asStateFlow()

    private val _clickedBook: MutableStateFlow<Book?> = MutableStateFlow(null)
    override val clickedBook = _clickedBook.asStateFlow()

    private val _apiAnswer: MutableStateFlow<ApiAnswer> = MutableStateFlow(ApiAnswer.Initial)
    override val apiAnswer = _apiAnswer.asStateFlow()

    override suspend fun verifyApiAnswer(searchText: String): List<Book?>? {
        val answer = service.getBooks(searchText)
        return when {
            answer.totalItems > 0 -> {
                val list = getList(answer)
                _foundBooks.emit(list)
                list
            }

            else -> {
                _foundBooks.emit(emptyList())
                null
            }
        }
    }

    private fun getList(answer: GoogleApiAnswer): List<Book?> {
        return answer.items.map { completeBook ->
            completeBook.volumeInfo?.let {
                val book = Book(
                    id = completeBook.id,
                    title = it.title ?: "",
                    authors = it.authors?.toString() ?: "",
                    description = it.description ?: "",
                    image = it.imageLinks?.thumbnail,
                    link = it.infoLink.orEmpty()
                )
                it.title?.let {
                    book
                }
            }
        }
    }

    override suspend fun verifyIfBookIsSaved(): Boolean {
        return clickedBook.value?.let { clickedBook ->
            verification(clickedBook)
        } == true
    }

    private suspend fun verification(clickedBook: Book): Boolean {
        var existentBook: SavedBook? = null

        userPreferences.first().loggedUserEmail?.let { userEmail ->
            existentBook = if (userEmail != "") {
                bookDao.verifyIfBookIsSaved(clickedBook.id, userEmail)
            } else null
        }

        return existentBook != null
    }

    override suspend fun saveBook(): Boolean {
        return userPreferences.first().loggedUserEmail?.let { userEmail ->
            clickedBook.value?.let { book ->
                val savedBook = SavedBook(
                    savedBookId = UUID.randomUUID().toString(),
                    userEmail = userEmail,
                    bookTitle = book.title,
                    bookAuthor = book.authors ?: "",
                    bookDescription = book.description ?: "",
                    bookImage = book.image ?: "",
                    bookApiId = book.id,
                    bookLink = book.link
                )
                bookDao.saveBook(savedBook)
            }
            true
        } == true
    }

    override suspend fun sendBook(book: Book) {
        _clickedBook.emit(book)
    }

    override suspend fun cleanApiAnswer() {
        _foundBooks.emit(emptyList())
    }

    override suspend fun updateApiAnswerState(state: ApiAnswer) {
        _apiAnswer.emit(state)
    }

    override suspend fun clearClickedBook() {
        _clickedBook.emit(null)
    }

}