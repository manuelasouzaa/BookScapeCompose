package br.com.bookscapecompose.ui.repositories

import android.content.Context
import android.util.Log
import br.com.bookscapecompose.database.BookScapeDatabase
import br.com.bookscapecompose.model.Book
import br.com.bookscapecompose.model.SavedBook
import br.com.bookscapecompose.ui.viewmodels.RootViewModel
import br.com.bookscapecompose.ui.viewmodels.SavedBookMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import java.util.UUID

class SavedBookRepositoryImpl(rootViewModel: RootViewModel, context: Context) :
    SavedBookRepository {

    private val bookDao = BookScapeDatabase.getDatabaseInstance(context).SavedBookDao()
    override val userPreferences = rootViewModel.userPreferences()

    private val _clickedBook: MutableStateFlow<Book?> = MutableStateFlow(null)
    override val clickedBook = _clickedBook.asStateFlow()

    private val _bookList: MutableStateFlow<List<Book?>> = MutableStateFlow(emptyList())
    override val bookList = _bookList.asStateFlow()

    private val _savedBookMessage: MutableStateFlow<SavedBookMessage> =
        MutableStateFlow(SavedBookMessage.Initial)
    override val savedBookMessage = _savedBookMessage.asStateFlow()

    override suspend fun sendBook(book: Book) {
        _clickedBook.emit(book)
    }

    override suspend fun showBooks(): List<Book?> {
        userPreferences.first().loggedUserEmail?.let { email ->
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
        userPreferences.first().loggedUserEmail?.let { userEmail ->
            clickedBook.value?.let { book ->
                val savedBook = fetchBook(book.id, userEmail)
                try {
                    bookDao.deleteSavedBook(savedBook)
                    _savedBookMessage.emit(SavedBookMessage.DeletedBook)
                } catch (e: Exception) {
                    _savedBookMessage.emit(SavedBookMessage.Error)
                    Log.e("TAG", "deleteBook: Error when trying to delete book", e)
                }
            }
        }
    }

    override suspend fun verifyIfBookIsSaved() {
        clickedBook.value?.let { clickedBook ->
            userPreferences.first().loggedUserEmail?.let { userEmail ->
                val isBookSaved = verification(clickedBook, userEmail)
                if (!isBookSaved && savedBookMessage.value != SavedBookMessage.DeletedBook)
                    _savedBookMessage.emit(SavedBookMessage.Error)
            }
        }
    }

    private suspend fun verification(clickedBook: Book, userEmail: String): Boolean {
        val existentBook = bookDao.verifyIfBookIsSaved(clickedBook.id, userEmail)
        return existentBook != null
    }

    private suspend fun fetchBook(bookId: String, userEmail: String): SavedBook {
        return bookDao.fetchSavedBook(bookId, userEmail)
    }

    override suspend fun clearClickedBook() {
        _clickedBook.emit(null)
    }

    override suspend fun clearBookMessage() {
        _savedBookMessage.emit(SavedBookMessage.Initial)
    }

    override suspend fun saveBook() {
        userPreferences.first().loggedUserEmail?.let { userEmail ->
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
                try {
                    bookDao.saveBook(savedBook)
                    _savedBookMessage.emit(SavedBookMessage.AddedBook)
                } catch (e: Exception) {
                    _savedBookMessage.emit(SavedBookMessage.Error)
                    Log.e("TAG", "saveBook: Error when trying to save book", e)
                }
            }
        }
    }
}