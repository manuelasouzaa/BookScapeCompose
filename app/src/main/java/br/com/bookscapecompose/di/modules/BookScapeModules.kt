package br.com.bookscapecompose.di.modules

import br.com.bookscapecompose.ui.navigation.UserPreferences
import br.com.bookscapecompose.ui.repositories.BookRepository
import br.com.bookscapecompose.ui.repositories.BookRepositoryImpl
import br.com.bookscapecompose.ui.repositories.SavedBookRepository
import br.com.bookscapecompose.ui.repositories.SavedBookRepositoryImpl
import br.com.bookscapecompose.ui.repositories.UserRepository
import br.com.bookscapecompose.ui.repositories.UserRepositoryImpl
import br.com.bookscapecompose.ui.viewmodels.AccountViewModel
import br.com.bookscapecompose.ui.viewmodels.BookDetailsViewModel
import br.com.bookscapecompose.ui.viewmodels.BookListViewModel
import br.com.bookscapecompose.ui.viewmodels.MainViewModel
import br.com.bookscapecompose.ui.viewmodels.SavedBookDetailsViewModel
import br.com.bookscapecompose.ui.viewmodels.SearchViewModel
import br.com.bookscapecompose.ui.viewmodels.SignInViewModel
import br.com.bookscapecompose.ui.viewmodels.SignUpViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val bookScapeModules: Module = module {

    single<UserRepository> {
        UserRepositoryImpl(get(), get())
    }

    single<BookRepository> {
        BookRepositoryImpl(get(), get())
    }

    single<SavedBookRepository> {
        SavedBookRepositoryImpl(get(), get())
    }

    viewModel {
        SignInViewModel(get())
    }

    viewModel {
        SignUpViewModel(get())
    }

    viewModel {
        MainViewModel(get(), get())
    }

    viewModel {
        SearchViewModel(get(), get())
    }

    viewModel {
        BookDetailsViewModel(get())
    }

    viewModel {
        SavedBookDetailsViewModel(get())
    }

    viewModel {
        BookListViewModel(get())
    }

    viewModel {
        AccountViewModel(get())
    }

    single {
        UserPreferences(androidContext())
    }

}
