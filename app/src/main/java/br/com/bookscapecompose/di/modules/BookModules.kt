package br.com.bookscapecompose.di.modules

import br.com.bookscapecompose.ui.repositories.BookRepository
import br.com.bookscapecompose.ui.repositories.BookRepositoryImpl
import br.com.bookscapecompose.ui.repositories.SavedBookRepository
import br.com.bookscapecompose.ui.repositories.SavedBookRepositoryImpl
import br.com.bookscapecompose.ui.viewmodels.BookDetailsViewModel
import br.com.bookscapecompose.ui.viewmodels.SavedBookDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val bookModules: Module = module {

    single<BookRepository> {
        BookRepositoryImpl(get(), get())
    }

    single<SavedBookRepository> {
        SavedBookRepositoryImpl(get(), get())
    }

    viewModel {
        BookDetailsViewModel(get())
    }

    viewModel {
        SavedBookDetailsViewModel(get())
    }

}