package br.com.bookscapecompose.ui.repositories

import br.com.bookscapecompose.model.Book
import br.com.bookscapecompose.ui.viewmodels.ApiAnswer
import kotlinx.coroutines.flow.StateFlow

interface BookRepository {

    val foundBooks: StateFlow<List<Book?>>
    val clickedBook: StateFlow<Book?>
    val apiAnswer: StateFlow<ApiAnswer>

    suspend fun verifyApiAnswer(searchText: String): List<Book?>?

    fun verifyIfBookIsSaved(bookId: String, userEmail: String): Boolean

    suspend fun saveBook(book: Book, userEmail: String): Boolean

    suspend fun sendBook(book: Book)

    suspend fun cleanApiAnswer()

    suspend fun updateApiAnswerState(state: ApiAnswer)

}