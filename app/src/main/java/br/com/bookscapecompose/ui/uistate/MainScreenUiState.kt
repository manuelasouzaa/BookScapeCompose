package br.com.bookscapecompose.ui.uistate

data class MainScreenUiState(
    val searchText: String = "",
    val onSearchChange: (String) -> Unit = {}
)