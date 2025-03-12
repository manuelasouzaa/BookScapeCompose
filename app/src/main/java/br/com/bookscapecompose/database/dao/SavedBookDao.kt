package br.com.bookscapecompose.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import br.com.bookscapecompose.model.SavedBook

@Dao
interface SavedBookDao {

    @Insert
    suspend fun saveBook(book: SavedBook)

    @Query("SELECT * FROM savedbook WHERE bookApiId = :bookId AND userEmail = :userEmail")
    suspend fun verifyIfBookIsSaved(bookId: String, userEmail: String): SavedBook?

    @Query("SELECT * FROM savedbook WHERE bookApiId = :bookId AND userEmail = :userEmail")
    suspend fun fetchSavedBook(bookId: String, userEmail: String): SavedBook

    @Query("SELECT * FROM savedbook WHERE userEmail = :userEmail")
    suspend fun showSavedBooks(userEmail: String): List<SavedBook?>

    @Delete
    fun deleteSavedBook(savedBook: SavedBook)

}