package br.com.bookscapecompose.ui.screens

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.bookscapecompose.R
import br.com.bookscapecompose.model.User
import br.com.bookscapecompose.sampledata.sampleUser
import br.com.bookscapecompose.ui.components.PersonalizedButton
import br.com.bookscapecompose.ui.theme.BookScapeComposeTheme
import br.com.bookscapecompose.ui.viewmodels.AccountViewModel

@Composable
fun AccountScreen(viewModel: AccountViewModel, navController: NavController) {
    BackHandler { navController.navigateUp() }

    val isLoading by viewModel.loading.collectAsState()
    val user = viewModel.user.collectAsState()

    viewModel.account()

    fun logout() = {
        viewModel.logout()
    }

    AccountScreenContent(
        isLoading = isLoading,
        user = user,
        returnClick = { navController.navigateUp() },
        logout = logout(),
        navigate = { navController.navigate(it) }
    )
}

@Composable
fun AccountScreenContent(
    isLoading: Boolean,
    user: State<User?>,
    returnClick: () -> Boolean,
    logout: () -> Unit,
    navigate: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        if (isLoading) {
            Column(Modifier.fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(16.dp)
                        .size(40.dp)
                )
            }
        } else {
            user.value?.let { loggedUser ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.2f)
                        .background(MaterialTheme.colorScheme.primary),
                    verticalArrangement = Arrangement.Top,
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier
                                .size(50.dp)
                                .clickable { returnClick() }
                        )
                    }
                }

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(3.dp)
                        .background(MaterialTheme.colorScheme.tertiary)
                )

                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null,
                    modifier = Modifier
                        .offset(y = (-100).dp)
                        .size(180.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.tertiary)
                        .scale(1.15f),
                    tint = MaterialTheme.colorScheme.onSecondary,
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .offset(y = (-80).dp),
                    verticalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = loggedUser.username,
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(16.dp),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth(.35f)
                                .height(2.dp)
                                .background(MaterialTheme.colorScheme.secondary)
                        )
                    }

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.e_mail),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(top = 50.dp, bottom = 5.dp)
                        )

                        Text(
                            text = loggedUser.userEmail,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onTertiary,
                            modifier = Modifier.padding(
                                top = 16.dp,
                                bottom = 30.dp,
                                start = 16.dp,
                                end = 16.dp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = { navigate("BookListScreen") },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.onSecondaryContainer
                            ),
                            modifier = Modifier
                                .fillMaxWidth(.5f)
                                .padding(vertical = 21.dp),
                        ) {
                            Text(
                                text = stringResource(R.string.my_book_list),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier.padding(5.dp)
                            )
                        }

                        PersonalizedButton(
                            modifier = Modifier
                                .fillMaxWidth(.5f)
                                .padding(top = 5.dp),
                            onClick = { logout() },
                            text = stringResource(R.string.logout),
                            imageVector = Icons.Default.ExitToApp
                        )
                    }
                }
            } ?: navigate("SignInScreen")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AccountScreenLightThemePreview() {
    BookScapeComposeTheme {
        AccountScreenContent(
            isLoading = false,
            user = remember { mutableStateOf(sampleUser) },
            returnClick = { true },
            logout = {},
            navigate = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AccountScreenLoadingLightThemePreview() {
    BookScapeComposeTheme {
        AccountScreenContent(
            isLoading = true,
            user = remember { mutableStateOf(sampleUser) },
            returnClick = { true },
            logout = {},
            navigate = {}
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AccountScreenDarkThemePreview() {
    BookScapeComposeTheme {
        AccountScreenContent(
            isLoading = false,
            user = remember { mutableStateOf(sampleUser) },
            returnClick = { true },
            logout = {},
            navigate = {}
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AccountScreenLoadingDarkThemePreview() {
    BookScapeComposeTheme {
        AccountScreenContent(
            isLoading = true,
            user = remember { mutableStateOf(sampleUser) },
            returnClick = { true },
            logout = {},
            navigate = {}
        )
    }
}