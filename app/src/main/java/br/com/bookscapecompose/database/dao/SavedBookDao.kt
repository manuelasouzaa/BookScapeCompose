package br.com.bookscapecompose.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.bookscapecompose.model.SavedBook

@Dao
interface SavedBookDao {

    @Insert
    fun saveBook(book: SavedBook)

    @Query("SELECT * FROM savedbook WHERE bookApiId = :bookId")
    fun fetchSavedBook(bookId: String): SavedBook?

    @Query("SELECT * FROM savedbook WHERE userEmail = :userEmail")
    fun showSavedBooks(userEmail: String): List<SavedBook?>

}