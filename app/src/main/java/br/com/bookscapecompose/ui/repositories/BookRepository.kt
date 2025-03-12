package br.com.bookscapecompose.ui.repositories

import android.content.Context
import br.com.bookscapecompose.model.Book

interface BookRepository {

    suspend fun verifyApiAnswer(searchText: String): List<Book?>?

    fun verifyIfBookIsSaved(context: Context, bookId: String, userEmail: String): Boolean

    suspend fun saveBook(context: Context, book: Book, userEmail: String): Boolean

    suspend fun showBooks(context: Context, userEmail: String): List<Book?>

    suspend fun deleteBook(context: Context, bookId: String, userEmail: String): Boolean

}