package br.com.bookscapecompose.ui.repositories

import android.content.Context
import br.com.bookscapecompose.database.BookScapeDatabase
import br.com.bookscapecompose.model.Book
import br.com.bookscapecompose.model.SavedBook
import java.util.UUID

class BookRepositoryImpl : BookRepository {

    private val database = BookScapeDatabase

    override fun verifyIfBookIsSaved(context: Context, bookId: String): Boolean {
        var doesBookExist = false
        val dao = database.getDatabaseInstance(context).SavedBookDao()
        val existentBook: SavedBook? = dao.fetchSavedBook(bookId)
        if (existentBook != null)
            doesBookExist = true

        return doesBookExist
    }

    override fun saveBook(context: Context, book: Book, userEmail: String) {
        val dao = database.getDatabaseInstance(context).SavedBookDao()
        val savedBook = SavedBook(
            savedBookId = UUID.randomUUID().toString(),
            userEmail = userEmail,
            bookTitle = book.title,
            bookAuthor = book.authors ?: "",
            bookDescription = book.description ?: "",
            bookImage = book.image ?: "",
            bookApiId = book.id
        )
        dao.saveBook(savedBook)
    }

    override fun showBooks(context: Context, userEmail: String): List<SavedBook?> {
        val dao = database.getDatabaseInstance(context).SavedBookDao()
        return dao.showSavedBooks(userEmail)
    }
}