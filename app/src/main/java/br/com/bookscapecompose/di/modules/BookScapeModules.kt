package br.com.bookscapecompose.di.modules

import br.com.bookscapecompose.ui.viewmodels.BookListViewModel
import br.com.bookscapecompose.ui.viewmodels.MainViewModel
import br.com.bookscapecompose.ui.viewmodels.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val bookScapeModules: Module = module {

    viewModel {
        MainViewModel(get())
    }

    viewModel {
        SearchViewModel(get())
    }

    viewModel {
        BookListViewModel(get())
    }

}
