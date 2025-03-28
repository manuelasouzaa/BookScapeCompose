package br.com.bookscapecompose.ui.screens

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.bookscapecompose.R
import br.com.bookscapecompose.expressions.toast
import br.com.bookscapecompose.sampledata.sampleMainUiState
import br.com.bookscapecompose.ui.components.BookScapeIconTextField
import br.com.bookscapecompose.ui.components.PersonalizedButton
import br.com.bookscapecompose.ui.theme.BookScapeComposeTheme
import br.com.bookscapecompose.ui.uistate.MainScreenUiState
import br.com.bookscapecompose.ui.viewmodels.ApiAnswer
import br.com.bookscapecompose.ui.viewmodels.MainViewModel

@Composable
fun MainScreen(viewModel: MainViewModel, navController: NavController) {

    BackHandler { navController.navigateUp() }

    val state by viewModel.uiState.collectAsState()
    val googleApiAnswer by viewModel.apiAnswer.collectAsState()
    val context = LocalContext.current
    val isLoading = googleApiAnswer == ApiAnswer.Loading

    LaunchedEffect(googleApiAnswer) {
        when (googleApiAnswer) {
            ApiAnswer.EmptyList ->
                toast(context, context.getString(R.string.book_not_found))

            ApiAnswer.Success ->
                navController.navigate("SearchScreen")

            ApiAnswer.Error ->
                toast(context, context.getString(R.string.something_went_wrong_try_again))

            else -> {}
        }
    }

    MainScreenContent(
        state = state,
        navigate = { navController.navigate(it) },
        isLoading = isLoading,
        searchBooks = { viewModel.searchBooks(it) }
    )
}

@Composable
fun MainScreenContent(
    state: MainScreenUiState,
    navigate: (String) -> Unit,
    isLoading: Boolean,
    searchBooks: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painterResource(R.drawable.logo_bookscape),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(.7f),
            )
            Text(
                text = stringResource(R.string.book_scape),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        BookScapeIconTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            searchText = state.searchText,
            onSearchChange = state.onSearchChange,
            onClick = {
                if (state.searchText.isNotEmpty()) {
                    searchBooks(state.searchText)
                }
            }
        )

        if (isLoading)
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))

        PersonalizedButton(
            modifier = Modifier.padding(top = 80.dp, bottom = 20.dp),
            onClick = { navigate("BookListScreen") },
            text = "My BookList",
            imageVector = Icons.AutoMirrored.Filled.List
        )

        PersonalizedButton(
            modifier = Modifier,
            onClick = { navigate("AccountScreen") },
            text = "My Account",
            imageVector = Icons.Default.AccountCircle
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MainScreenLightThemePreview() {
    BookScapeComposeTheme {
        MainScreenContent(
            state = sampleMainUiState,
            navigate = {},
            searchBooks = {},
            isLoading = false
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MainScreenLoadingLightThemePreview() {
    BookScapeComposeTheme {
        MainScreenContent(
            state = sampleMainUiState,
            navigate = {},
            searchBooks = {},
            isLoading = true
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MainScreenDarkThemePreview() {
    BookScapeComposeTheme {
        MainScreenContent(
            state = sampleMainUiState,
            navigate = {},
            searchBooks = {},
            isLoading = false
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MainScreenLoadingDarkThemePreview() {
    BookScapeComposeTheme {
        MainScreenContent(
            state = sampleMainUiState,
            navigate = {},
            searchBooks = {},
            isLoading = true
        )
    }
}