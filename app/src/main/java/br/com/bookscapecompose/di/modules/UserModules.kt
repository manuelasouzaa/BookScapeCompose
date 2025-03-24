package br.com.bookscapecompose.di.modules

import br.com.bookscapecompose.preferences.UserPreferences
import br.com.bookscapecompose.ui.repositories.UserRepository
import br.com.bookscapecompose.ui.repositories.UserRepositoryImpl
import br.com.bookscapecompose.ui.viewmodels.AccountViewModel
import br.com.bookscapecompose.ui.viewmodels.RootViewModel
import br.com.bookscapecompose.ui.viewmodels.SignInViewModel
import br.com.bookscapecompose.ui.viewmodels.SignUpViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val userModules: Module = module {

    single<UserRepository> {
        UserRepositoryImpl(get(), get())
    }

    viewModel {
        SignInViewModel(get())
    }

    viewModel {
        SignUpViewModel(get())
    }

    viewModel {
        AccountViewModel(get())
    }

    viewModel {
        RootViewModel(get())
    }

    single {
        UserPreferences(androidContext())
    }

}