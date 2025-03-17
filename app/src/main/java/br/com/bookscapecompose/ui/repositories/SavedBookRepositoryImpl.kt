package br.com.bookscapecompose.ui.repositories

import android.content.Context
import androidx.lifecycle.asLiveData
import br.com.bookscapecompose.database.BookScapeDatabase
import br.com.bookscapecompose.model.Book
import br.com.bookscapecompose.model.SavedBook
import br.com.bookscapecompose.ui.navigation.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SavedBookRepositoryImpl(userPreferences: UserPreferences, context: Context) :
    SavedBookRepository {

    private val bookDao = BookScapeDatabase.getDatabaseInstance(context).SavedBookDao()
    private val userEmail = userPreferences.userEmail.asLiveData()

    private val _clickedBook: MutableStateFlow<Book?> =
        MutableStateFlow(null)
    override val clickedBook = _clickedBook.asStateFlow()

    private val _bookList: MutableStateFlow<List<Book?>> =
        MutableStateFlow(emptyList())
    override val bookList = _bookList.asStateFlow()

    override suspend fun sendBook(book: Book) {
        _clickedBook.emit(book)
    }

    override suspend fun showBooks(): List<Book?> {
        userEmail.value?.let { email ->
            val formattedList = formattingList(bookDao.showSavedBooks(email))
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

    override suspend fun deleteBook(bookId: String, userEmail: String): Boolean {
        val savedBook = fetchBook(bookId, userEmail)
        return try {
            bookDao.deleteSavedBook(savedBook)
            true
        } catch (e: Exception) {
            false
        }
    }

    private suspend fun fetchBook(bookId: String, userEmail: String): SavedBook {
        val savedBook = bookDao.fetchSavedBook(bookId, userEmail)
        return savedBook
    }

    override suspend fun reset() {
        _clickedBook.emit(null)
    }
}