package br.com.bookscapecompose.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.bookscapecompose.model.User

@Dao
interface UserDao {

    @Insert
    fun saveUser(user: User)

    @Query("SELECT * FROM User WHERE userEmail = :email")
    fun fetchUserByEmail(email: String): User?

    @Query("SELECT * FROM User WHERE userEmail = :email AND password = :password")
    fun fetchAndAuthenticateUser(email: String, password: String): User?

}