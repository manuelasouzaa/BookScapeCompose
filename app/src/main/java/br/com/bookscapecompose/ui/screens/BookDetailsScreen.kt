package br.com.bookscapecompose.ui.screens

import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.bookscapecompose.R
import br.com.bookscapecompose.ui.components.PersonalizedButton
import br.com.bookscapecompose.ui.viewmodels.SharedViewModel
import coil3.compose.AsyncImage
import kotlinx.coroutines.runBlocking

@Composable
fun BookDetailsScreen(
    viewModel: SharedViewModel,
    navController: NavController,
) {
    var bookTitle = ""
    var bookAuthors = ""
    var bookDesc = ""
    var bookImage = ""
    var bookLink = ""

    val context = LocalContext.current

    BackHandler {
        navController.navigateUp()
    }

    runBlocking {
        viewModel.clickedBook.value?.let { book ->
            bookTitle = book.title
            bookAuthors = book.authors ?: ""
            bookDesc = book.description ?: ""
            bookImage = book.image ?: ""
            bookLink = book.link
        } ?: navController.navigate("MainScreen")
    }

    val uriHandler = LocalUriHandler.current

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState()),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .size(50.dp)
                    .clickable {
                        navController.navigateUp()
                    },
            )

            AsyncImage(
                model = bookImage,
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
                imageVector = ImageVector.vectorResource(R.drawable.ic_add),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .size(50.dp)
                    .clickable {
                        runBlocking { viewModel.saveBook(context) }
                    },
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = bookTitle,
                fontSize = 27.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onTertiary,
                textAlign = TextAlign.Center
            )

            Text(
                text = bookAuthors,
                fontSize = 23.sp,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

            Text(
                text = bookDesc,
                fontSize = 22.sp,
                color = MaterialTheme.colorScheme.onTertiary,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(
                    top = 16.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 20.dp
                )
            )

            PersonalizedButton(
                modifier = Modifier.padding(20.dp),
                onClick = {
                    uriHandler.openUri(bookLink)
                },
                text = "Purchase",
                imageVector = Icons.Default.ShoppingCart
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BookDetailsScreenPreview() {
    BookDetailsScreen(SharedViewModel(), rememberNavController())
}