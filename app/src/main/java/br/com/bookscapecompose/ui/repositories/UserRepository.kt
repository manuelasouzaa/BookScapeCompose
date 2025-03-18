package br.com.bookscapecompose.ui.repositories

import br.com.bookscapecompose.model.User

interface UserRepository {

    fun addUser(user: User)

    fun fetchUserByEmail(email: String): User?

    suspend fun fetchAndAuthenticateUser(email: String, password: String): User?

    suspend fun login(email: String)

    suspend fun logout()

    suspend fun fetchUserByEmailPreference(): User?

}
