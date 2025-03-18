package br.com.bookscapecompose.ui.repositories

import android.content.Context
import androidx.lifecycle.asLiveData
import br.com.bookscapecompose.database.BookScapeDatabase
import br.com.bookscapecompose.model.User
import br.com.bookscapecompose.ui.navigation.UserPreferences

class UserRepositoryImpl(private val preferences: UserPreferences, context: Context) :
    UserRepository {

    private val userDao = BookScapeDatabase.getDatabaseInstance(context).UserDao()
    private val userEmail = preferences.userEmail.asLiveData()

    override fun addUser(user: User) {
        userDao.saveUser(user)
    }

    override fun fetchUserByEmail(email: String): User? {
        return userDao.fetchUserByEmail(email)
    }

    override fun fetchUserByEmailPreference(): User? {
        return userEmail.value?.let {
            userDao.fetchUserByEmail(it)
        }
    }

    override suspend fun fetchAndAuthenticateUser(email: String, password: String): User? {
        return userDao.fetchAndAuthenticateUser(email, password)
    }

    override suspend fun login(email: String) {
        preferences.updateLoggedState(true)
        preferences.updateUserEmail(email)
    }

    override suspend fun logout() {
        preferences.logout()
    }
}