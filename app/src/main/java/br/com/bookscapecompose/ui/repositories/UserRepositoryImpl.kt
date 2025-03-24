package br.com.bookscapecompose.ui.repositories

import android.content.Context
import br.com.bookscapecompose.database.BookScapeDatabase
import br.com.bookscapecompose.model.User
import br.com.bookscapecompose.preferences.UserConfig
import br.com.bookscapecompose.ui.viewmodels.RootViewModel
import kotlinx.coroutines.flow.first

class UserRepositoryImpl(private val rootViewModel: RootViewModel, context: Context) :
    UserRepository {

    private val userDao = BookScapeDatabase.getDatabaseInstance(context).UserDao()
    override val userPreferences = rootViewModel.userPreferences()

    override fun addUser(user: User) {
        userDao.saveUser(user)
    }

    override fun fetchUserByEmail(email: String): User? {
        return userDao.fetchUserByEmail(email)
    }

    override suspend fun fetchUserByEmailPreference(): User? {
        return userPreferences.first().loggedUserEmail?.let { userEmail ->
            userDao.fetchUserByEmail(userEmail)
        }
    }

    override suspend fun fetchAndAuthenticateUser(email: String, password: String): User? {
        return userDao.fetchAndAuthenticateUser(email, password)
    }

    override suspend fun login(email: String) {
        rootViewModel.updateUserPreferences(
            UserConfig(loggedUserEmail = email, loggedUserState = true)
        )
    }

    override suspend fun logout() {
        rootViewModel.logout()
    }
}