package br.com.bookscapecompose.ui.repositories

import br.com.bookscapecompose.model.Book
import br.com.bookscapecompose.preferences.UserConfig
import br.com.bookscapecompose.ui.viewmodels.SavedBookMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface SavedBookRepository {

    val clickedBook: StateFlow<Book?>
    val bookList: StateFlow<List<Book?>>
    val savedBookMessage: StateFlow<SavedBookMessage>
    val userPreferences: Flow<UserConfig>

    suspend fun sendBook(book: Book)

    suspend fun showBooks(): List<Book?>

    suspend fun deleteBook()

    suspend fun verifyIfBookIsSaved()

    suspend fun clearClickedBook()

    suspend fun clearBookMessage()

    suspend fun saveBook()

}