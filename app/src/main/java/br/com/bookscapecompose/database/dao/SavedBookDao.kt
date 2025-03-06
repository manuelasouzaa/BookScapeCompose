package br.com.bookscapecompose.database.dao

import androidx.room.Dao
import androidx.room.Insert
import br.com.bookscapecompose.model.SavedBook

@Dao
interface SavedBookDao {

    @Insert
    fun saveBook(book: SavedBook)

}