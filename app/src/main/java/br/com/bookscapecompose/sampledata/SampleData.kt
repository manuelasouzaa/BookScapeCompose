package br.com.bookscapecompose.sampledata

import br.com.bookscapecompose.model.Book
import br.com.bookscapecompose.model.User
import br.com.bookscapecompose.ui.uistate.MainScreenUiState
import br.com.bookscapecompose.ui.uistate.SignInScreenUiState
import br.com.bookscapecompose.ui.uistate.SignUpScreenUiState

val sampleBook: Book = Book(
    id = "id",
    title = "Percy Jackson and the Olympians - The Lightning Thief",
    authors = "Rick Riordan",
    description = null,
    image = "http://books.google.com/books/content?id=V6A0zgEACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api",
    link = ""
)

val sampleList: List<Book> = listOf(
    sampleBook, sampleBook, sampleBook, sampleBook, sampleBook, sampleBook, sampleBook, sampleBook,
)

val sampleUser: User = User(
    userEmail = "user@gmail.com",
    username = "username",
    password = "password"
)

val sampleSignInState: SignInScreenUiState = SignInScreenUiState(
    email = "user@gmail.com",
    onEmailChange = {},
    password = "password",
    onPasswordChange = {}
)

val sampleSignUpState: SignUpScreenUiState = SignUpScreenUiState(
    email = "user@gmail.com",
    onEmailChange = {},
    username = "username",
    onUsernameChange = {},
    password = "password",
    onPasswordChange = {}
)

val sampleMainUiState: MainScreenUiState = MainScreenUiState(
    searchText = "example",
    onSearchChange = {}
)