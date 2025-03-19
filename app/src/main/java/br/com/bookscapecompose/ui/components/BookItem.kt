package br.com.bookscapecompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.bookscapecompose.R
import br.com.bookscapecompose.model.Book
import br.com.bookscapecompose.sampledata.sampleBook
import coil3.compose.AsyncImage

@Composable
fun BookItem(
    modifier: Modifier = Modifier,
    book: Book,
    onClick: () -> Unit,
) {
    Row(
        modifier
            .fillMaxWidth()
            .height(160.dp)
            .clip(RoundedCornerShape(10))
            .background(MaterialTheme.colorScheme.primary)
            .padding(0.dp)
            .clickable {
                onClick()
            },
    ) {
        AsyncImage(
            model = book.image ?: "",
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(.32f)
                .fillMaxHeight()
                .padding(12.dp)
                .shadow(6.dp),
            placeholder = painterResource(R.drawable.no_image),
            error = painterResource(R.drawable.no_image),
            contentScale = ContentScale.Crop,
        )

        Column(
            Modifier
                .fillMaxHeight()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = book.title,
                fontSize = 21.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = book.authors.orEmpty(),
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onTertiary,
                modifier = Modifier.padding(top = 10.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BookItemPreview(modifier: Modifier = Modifier) {
    BookItem(book = sampleBook, onClick = {})
}