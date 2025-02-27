package br.com.bookscapecompose.ui.viewmodels

import androidx.lifecycle.ViewModel
import br.com.bookscapecompose.web.retrofit.RetrofitInstance

class MainActivityViewModel : ViewModel() {

    private val service by lazy {
        RetrofitInstance.api
    }

    suspend fun searchBooks(searchText: String) =
        service.getBooks(search = searchText)
}