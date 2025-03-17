package br.com.bookscapecompose.ui.repositories

import br.com.bookscapecompose.model.Book
import kotlinx.coroutines.flow.StateFlow

interface SavedBookRepository {

    val clickedBook: StateFlow<Book?>
    val bookList: StateFlow<List<Book?>>

    suspend fun sendBook(book: Book)

    suspend fun showBooks(): List<Book?>

    suspend fun deleteBook(bookId: String, userEmail: String): Boolean

    suspend fun reset()

}