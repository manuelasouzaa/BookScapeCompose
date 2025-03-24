package br.com.bookscapecompose.ui.repositories

import br.com.bookscapecompose.model.User
import br.com.bookscapecompose.preferences.UserConfig
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    val userPreferences: Flow<UserConfig>

    fun addUser(user: User)

    fun fetchUserByEmail(email: String): User?

    suspend fun fetchAndAuthenticateUser(email: String, password: String): User?

    suspend fun login(email: String)

    suspend fun logout()

    suspend fun fetchUserByEmailPreference(): User?

}
