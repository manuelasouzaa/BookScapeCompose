package br.com.bookscapecompose.database.dao

import androidx.room.Dao
import androidx.room.Insert
import br.com.bookscapecompose.model.User

@Dao
interface UserDao {

    @Insert
    fun saveUser(user: User)

}