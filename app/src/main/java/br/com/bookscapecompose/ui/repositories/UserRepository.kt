package br.com.bookscapecompose.ui.repositories

import android.content.Context
import br.com.bookscapecompose.model.User

interface UserRepository {

    fun fetchUserByEmail(context: Context, email: String): User?

    fun fetchAndAuthenticateUser(context: Context, email: String, password: String): User?

}
