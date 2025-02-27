package br.com.bookscapecompose.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.bookscapecompose.R
import br.com.bookscapecompose.ui.components.BookItem
import br.com.bookscapecompose.ui.components.BookScapeTextField
import br.com.bookscapecompose.ui.viewmodels.MainActivityViewModel

@Composable
fun MainScreen(
    viewModel: MainActivityViewModel,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painterResource(R.drawable.logo_bookscape),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(.4f),
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

        BookScapeTextField(
            modifier = Modifier.fillMaxWidth(),
            searchText = "percy",
            onSearchChange = {},
            viewModel = viewModel
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(top = 16.dp)
        ) {
            BookItem(Modifier.fillMaxWidth())
            BookItem(Modifier.fillMaxWidth())
            BookItem(Modifier.fillMaxWidth())
            BookItem(Modifier.fillMaxWidth())
            BookItem(Modifier.fillMaxWidth())
            BookItem(Modifier.fillMaxWidth())
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MainScreenPreview() {
    MainScreen(MainActivityViewModel())
}