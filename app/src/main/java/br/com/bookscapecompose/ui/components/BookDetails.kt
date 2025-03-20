package br.com.bookscapecompose.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.bookscapecompose.R
import br.com.bookscapecompose.sampledata.sampleBook
import br.com.bookscapecompose.ui.theme.BookScapeComposeTheme
import coil3.compose.AsyncImage

@Composable
fun BookDetails(
    returnClick: () -> Unit,
    bookImageUrl: String,
    bookmarkIcon: Int,
    bookmarkIconClick: () -> Unit,
    bookTitle: String,
    bookAuthors: String,
    bookDesc: String,
    purchaseButtonClick: () -> Unit,
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState()),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .size(50.dp)
                    .clickable { returnClick() },
            )

            AsyncImage(
                model = bookImageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth(.5f)
                    .padding(10.dp)
                    .shadow(6.dp),
                placeholder = painterResource(R.drawable.no_image),
                error = painterResource(R.drawable.no_image),
            )

            Icon(
                imageVector = ImageVector.vectorResource(bookmarkIcon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .size(50.dp)
                    .clickable { bookmarkIconClick() },
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = bookTitle,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onTertiary,
                modifier = Modifier.padding(16.dp)
            )

            Text(
                text = bookAuthors,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(16.dp)
            )

            Text(
                text = bookDesc,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onTertiary,
                modifier = Modifier.padding(
                    top = 16.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 20.dp
                )
            )

            PersonalizedButton(
                modifier = Modifier.padding(20.dp),
                onClick = { purchaseButtonClick() },
                text = "Purchase",
                imageVector = Icons.Default.ShoppingCart
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BookDetailsLightThemePreview() {
    BookScapeComposeTheme {
        BookDetails(
            returnClick = {},
            bookImageUrl = sampleBook.image.orEmpty(),
            bookmarkIcon = R.drawable.ic_added,
            purchaseButtonClick = {},
            bookTitle = sampleBook.title,
            bookAuthors = sampleBook.authors.orEmpty(),
            bookDesc = sampleBook.description.orEmpty(),
            bookmarkIconClick = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun BookDetailsDarkThemePreview() {
    BookScapeComposeTheme {
        BookDetails(
            returnClick = {},
            bookImageUrl = sampleBook.image.orEmpty(),
            bookmarkIcon = R.drawable.ic_added,
            purchaseButtonClick = {},
            bookTitle = sampleBook.title,
            bookAuthors = sampleBook.authors.orEmpty(),
            bookDesc = sampleBook.description.orEmpty(),
            bookmarkIconClick = {}
        )
    }
}