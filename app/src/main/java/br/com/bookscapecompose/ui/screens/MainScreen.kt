package br.com.bookscapecompose.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.bookscapecompose.R
import br.com.bookscapecompose.ui.components.BookScapeIconTextField
import br.com.bookscapecompose.ui.components.PersonalizedButton
import br.com.bookscapecompose.ui.viewmodels.SharedViewModel
import kotlinx.coroutines.runBlocking

@Composable
fun MainScreen(
    viewModel: SharedViewModel,
    navController: NavController,
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
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
                text = stringResource(R.string.bookscape),
                fontSize = 28.sp,
                modifier = Modifier.padding(16.dp),
                fontFamily = FontFamily(
                    listOf(Font(R.font.kavoon))
                ),
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        BookScapeIconTextField(
            modifier = Modifier.fillMaxWidth(),
            searchText = state.searchText,
            onSearchChange = state.onSearchChange,
            onClick = {
                runBlocking {
                    state.searchText.let {
                        if (it.isNotEmpty()) {
                            viewModel.searchBooks(state.searchText)
                            if (viewModel.bookList.value.isEmpty())
                                Toast.makeText(context, "Livro não encontrado", Toast.LENGTH_SHORT)
                                    .show()
                            if (viewModel.bookList.value.isNotEmpty())
                                navController.navigate("SearchScreen")
                        }
                    }
                }
            }
        )

        PersonalizedButton(
            modifier = Modifier.padding(top = 80.dp, bottom = 20.dp),
            onClick = {},
            text = "My BookList",
            imageVector = Icons.Default.List
        )

        PersonalizedButton(
            modifier = Modifier,
            onClick = {},
            text = "My Account",
            imageVector = Icons.Default.AccountCircle
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MainScreenPreview() {
    MainScreen(SharedViewModel(), rememberNavController())
}