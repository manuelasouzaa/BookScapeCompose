package br.com.bookscapecompose.ui.repositories

import android.content.Context
import br.com.bookscapecompose.database.BookScapeDatabase
import br.com.bookscapecompose.model.User

class UserRepositoryImpl : UserRepository {

    private val database = BookScapeDatabase

    fun addUser(context: Context, user: User) {
        val dao = database.getDatabaseInstance(context).UserDao()
        dao.saveUser(user)
    }

    override fun fetchUserByEmail(context: Context, email: String): User? {
        val dao = database.getDatabaseInstance(context).UserDao()
        return dao.fetchUserByEmail(email)
    }

    override fun fetchAndAuthenticateUser(
        context: Context, email: String, password: String,
    ): User? {
        val dao = database.getDatabaseInstance(context).UserDao()
        return dao.fetchAndAuthenticateUser(email, password)
    }
}