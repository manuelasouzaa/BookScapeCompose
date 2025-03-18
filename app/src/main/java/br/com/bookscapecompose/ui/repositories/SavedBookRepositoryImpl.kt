package br.com.bookscapecompose.ui.repositories

import android.content.Context
import br.com.bookscapecompose.database.BookScapeDatabase
import br.com.bookscapecompose.model.Book
import br.com.bookscapecompose.model.SavedBook
import br.com.bookscapecompose.ui.navigation.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first

class SavedBookRepositoryImpl(userPreferences: UserPreferences, context: Context) :
    SavedBookRepository {

    private val bookDao = BookScapeDatabase.getDatabaseInstance(context).SavedBookDao()
    override val userEmail: Flow<String?> = userPreferences.userEmail

    private val _clickedBook: MutableStateFlow<Book?> = MutableStateFlow(null)
    override val clickedBook = _clickedBook.asStateFlow()

    private val _bookList: MutableStateFlow<List<Book?>> = MutableStateFlow(emptyList())
    override val bookList = _bookList.asStateFlow()

    override suspend fun sendBook(book: Book) {
        _clickedBook.emit(book)
    }

    override suspend fun showBooks(): List<Book?> {
        userEmail.first()?.let { email ->
            val formattedList = formattingList(bookDao.showSavedBooks(email))
            _bookList.emit(formattedList)
            return formattedList
        } ?: return emptyList()
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

    override suspend fun deleteBook() {
        userEmail.first()?.let { userEmail ->
            clickedBook.value?.let { book ->
                val savedBook = fetchBook(book.id, userEmail)
                bookDao.deleteSavedBook(savedBook)
            }
        }
    }

    override suspend fun verifyIfBookIsSaved(): Boolean {
        return clickedBook.value?.let { clickedBook ->
            verification(clickedBook)
        } ?: false
    }

    private suspend fun verification(clickedBook: Book): Boolean {
        var existentBook: SavedBook? = null

        userEmail.first()?.let { userEmail ->
            existentBook = if (userEmail != "") {
                bookDao.verifyIfBookIsSaved(clickedBook.id, userEmail)
            } else null
        }

        return existentBook != null
    }

    private suspend fun fetchBook(bookId: String, userEmail: String): SavedBook {
        val savedBook = bookDao.fetchSavedBook(bookId, userEmail)
        return savedBook
    }

    override suspend fun clearClickedBook() {
        _clickedBook.emit(null)
    }
}