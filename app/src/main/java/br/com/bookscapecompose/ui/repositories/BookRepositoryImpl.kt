package br.com.bookscapecompose.ui.repositories

import android.content.Context
import androidx.lifecycle.asLiveData
import br.com.bookscapecompose.database.BookScapeDatabase
import br.com.bookscapecompose.model.Book
import br.com.bookscapecompose.model.SavedBook
import br.com.bookscapecompose.ui.navigation.UserPreferences
import br.com.bookscapecompose.ui.viewmodels.ApiAnswer
import br.com.bookscapecompose.web.json.GoogleApiAnswer
import br.com.bookscapecompose.web.retrofit.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking
import java.util.UUID

class BookRepositoryImpl(preferences: UserPreferences, context: Context) : BookRepository {

    private val bookDao = BookScapeDatabase.getDatabaseInstance(context).SavedBookDao()

    private val service by lazy { RetrofitInstance.api }
    private val email = preferences.userEmail.asLiveData().value

    private val _foundBooks: MutableStateFlow<List<Book?>> =
        MutableStateFlow(emptyList())
    override val foundBooks = _foundBooks.asStateFlow()

    private val _clickedBook: MutableStateFlow<Book?> =
        MutableStateFlow(null)
    override val clickedBook = _clickedBook.asStateFlow()

    private val _apiAnswer: MutableStateFlow<ApiAnswer> =
        MutableStateFlow(ApiAnswer.Initial)
    override val apiAnswer = _apiAnswer.asStateFlow()

    override suspend fun verifyApiAnswer(searchText: String): List<Book?>? {
        val answer = service.getBooks(searchText)
        return when {
            answer.totalItems == 0 -> {
                _foundBooks.emit(emptyList())
                null
            }

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

    override fun verifyIfBookIsSaved(bookId: String, userEmail: String): Boolean {
        val existentBook: SavedBook? = email?.let {
            runBlocking { bookDao.verifyIfBookIsSaved(bookId, it) }
        }
        return existentBook != null
    }

    override suspend fun saveBook(book: Book, userEmail: String): Boolean {
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
        return try {
            bookDao.saveBook(savedBook)
            true
        } catch (e: Exception) {
            false
        }
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
}