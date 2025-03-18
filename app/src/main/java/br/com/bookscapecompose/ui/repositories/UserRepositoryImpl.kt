package br.com.bookscapecompose.ui.repositories

import android.content.Context
import br.com.bookscapecompose.database.BookScapeDatabase
import br.com.bookscapecompose.model.User
import br.com.bookscapecompose.ui.navigation.UserPreferences
import kotlinx.coroutines.flow.first

class UserRepositoryImpl(private val preferences: UserPreferences, context: Context) :
    UserRepository {

    private val userDao = BookScapeDatabase.getDatabaseInstance(context).UserDao()
    private val userEmail = preferences.userEmail

    override fun addUser(user: User) {
        userDao.saveUser(user)
    }

    override fun fetchUserByEmail(email: String): User? {
        return userDao.fetchUserByEmail(email)
    }

    override suspend fun fetchUserByEmailPreference(): User? {
        return userEmail.first()?.let { email ->
            userDao.fetchUserByEmail(email)
        }
    }

    override suspend fun fetchAndAuthenticateUser(email: String, password: String): User? {
        return userDao.fetchAndAuthenticateUser(email, password)
    }

    override suspend fun login(email: String) {
        preferences.updateUserEmail(email)
        preferences.updateLoggedState(true)
    }

    override suspend fun logout() {
        preferences.logout()
    }
}