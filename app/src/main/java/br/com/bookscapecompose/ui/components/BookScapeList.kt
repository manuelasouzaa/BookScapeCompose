package br.com.bookscapecompose.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.bookscapecompose.model.Book
import br.com.bookscapecompose.sampledata.sampleList
import br.com.bookscapecompose.ui.theme.BookScapeComposeTheme

@Composable
fun BookScapeList(
    returnClick: () -> Unit,
    title: String,
    list: List<Book?>,
    onClick: (Book) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .size(50.dp)
                    .clickable { returnClick() },
            )

            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )

            Spacer(Modifier.size(50.dp))
        }

        if (list.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "No books here yet!\nBegin adding your favorites now.",
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        if (list.isNotEmpty()) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp)
            ) {
                items(list) { bookItem ->
                    bookItem?.let { book ->
                        BookItem(book = book, onClick = { onClick(book) })
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BookScapeListLightModePreview() {
    BookScapeComposeTheme {
        BookScapeList(
            returnClick = {},
            title = "List",
            list = sampleList,
            onClick = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BookScapeEmptyListLightModePreview() {
    BookScapeComposeTheme {
        BookScapeList(
            returnClick = {},
            title = "List",
            list = emptyList(),
            onClick = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun BookScapeListDarkModePreview() {
    BookScapeComposeTheme {
        BookScapeList(
            returnClick = {},
            title = "List",
            list = sampleList,
            onClick = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun BookScapeEmptyListDarkModePreview() {
    BookScapeComposeTheme {
        BookScapeList(
            returnClick = {},
            title = "List",
            list = emptyList(),
            onClick = {}
        )
    }
}