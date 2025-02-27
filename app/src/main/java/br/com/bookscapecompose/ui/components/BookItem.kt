package br.com.bookscapecompose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.bookscapecompose.R

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BookItem(modifier: Modifier = Modifier) {
    Row(
        modifier
            .fillMaxWidth()
            .height(160.dp)
            .clip(RoundedCornerShape(10))
            .background(MaterialTheme.colorScheme.primary)
            .padding(0.dp),
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth(.32f)
                .fillMaxHeight()
                .padding(12.dp)
        )

        Column (
            Modifier
                .fillMaxHeight()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    text = LoremIpsum(50).values.first(),
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(.8f)
                )
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_add),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.size(40.dp),
                )
            }
            Text(
                text = LoremIpsum(50).values.first(),
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onTertiary,
                modifier = Modifier.padding(top = 10.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}