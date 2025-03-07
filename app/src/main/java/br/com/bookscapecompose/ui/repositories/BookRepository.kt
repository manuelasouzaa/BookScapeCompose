package br.com.bookscapecompose.ui.repositories

import android.content.Context
import br.com.bookscapecompose.model.Book
import br.com.bookscapecompose.model.SavedBook

interface BookRepository {

    suspend fun verifyApiAnswer(searchText: String): List<Book?>?

    fun verifyIfBookIsSaved(context: Context, bookId: String): Boolean

    fun saveBook(context: Context, book: Book, userEmail: String): Boolean

    fun showBooks(context: Context, userEmail: String): List<SavedBook?>

}