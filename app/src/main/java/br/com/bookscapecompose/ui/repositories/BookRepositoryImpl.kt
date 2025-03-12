package br.com.bookscapecompose.ui.repositories

import android.content.Context
import br.com.bookscapecompose.database.BookScapeDatabase
import br.com.bookscapecompose.model.Book
import br.com.bookscapecompose.model.SavedBook
import br.com.bookscapecompose.web.json.GoogleApiAnswer
import br.com.bookscapecompose.web.retrofit.RetrofitInstance
import kotlinx.coroutines.runBlocking
import java.util.UUID

class BookRepositoryImpl : BookRepository {

    private val database = BookScapeDatabase

    private val service by lazy { RetrofitInstance.api }

    override suspend fun verifyApiAnswer(searchText: String): List<Book?>? {
        val answer = service.getBooks(searchText)
        return when {
            answer.totalItems == 0 ->
                null

            answer.totalItems > 0 ->
                getList(answer)

            else ->
                null
        }
    }

    private fun getList(answer: GoogleApiAnswer): List<Book?> {
        return answer.items.map { completeBook ->
            completeBook.volumeInfo?.let {
                val book = Book(
                    id = completeBook.id,
                    title = it.title ?: "",
                    authors = it.authors?.toString() ?: "",
                    description = it.description ?: "",
                    image = it.imageLinks?.thumbnail,
                    link = it.infoLink.orEmpty()
                )
                it.title?.let {
                    book
                }
            }
        }
    }

    override fun verifyIfBookIsSaved(context: Context, bookId: String, userEmail: String): Boolean {
        val dao = database.getDatabaseInstance(context).SavedBookDao()
        val existentBook: SavedBook? = runBlocking { dao.verifyIfBookIsSaved(bookId, userEmail) }
        return existentBook != null
    }

    override suspend fun saveBook(context: Context, book: Book, userEmail: String): Boolean {
        val dao = database.getDatabaseInstance(context).SavedBookDao()
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
        return try {
            dao.saveBook(savedBook)
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun showBooks(context: Context, userEmail: String): List<Book?> {
        val dao = database.getDatabaseInstance(context).SavedBookDao()
        val list: List<SavedBook?> = dao.showSavedBooks(userEmail)
        return formattingList(list)
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

    override suspend fun deleteBook(context: Context, bookId: String, userEmail: String): Boolean {
        val dao = database.getDatabaseInstance(context).SavedBookDao()
        val savedBook = fetchBook(context, bookId, userEmail)
        return try {
            dao.deleteSavedBook(savedBook)
            true
        } catch (e: Exception) {
            false
        }
    }

    private suspend fun fetchBook(context: Context, bookId: String, userEmail: String): SavedBook {
        val dao = database.getDatabaseInstance(context).SavedBookDao()
        val savedBook = dao.fetchSavedBook(bookId, userEmail)
        return savedBook
    }
}