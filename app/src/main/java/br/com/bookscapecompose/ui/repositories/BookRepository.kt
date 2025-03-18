package br.com.bookscapecompose.ui.repositories

import br.com.bookscapecompose.model.Book
import br.com.bookscapecompose.ui.viewmodels.ApiAnswer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface BookRepository {

    val foundBooks: StateFlow<List<Book?>>
    val clickedBook: StateFlow<Book?>
    val apiAnswer: StateFlow<ApiAnswer>
    val userEmail: Flow<String?>

    suspend fun verifyApiAnswer(searchText: String): List<Book?>?

    suspend fun verifyIfBookIsSaved(): Boolean

    suspend fun saveBook(): Boolean

    suspend fun sendBook(book: Book)

    suspend fun cleanApiAnswer()

    suspend fun updateApiAnswerState(state: ApiAnswer)

    suspend fun clearClickedBook()

}