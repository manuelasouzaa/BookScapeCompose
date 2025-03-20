package br.com.bookscapecompose.ui.screens

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.bookscapecompose.R
import br.com.bookscapecompose.expressions.toast
import br.com.bookscapecompose.sampledata.sampleSignInState
import br.com.bookscapecompose.ui.components.BookScapeTextField
import br.com.bookscapecompose.ui.theme.BookScapeComposeTheme
import br.com.bookscapecompose.ui.uistate.SignInScreenUiState
import br.com.bookscapecompose.ui.viewmodels.SignInMessage
import br.com.bookscapecompose.ui.viewmodels.SignInViewModel

@Composable
fun SignInScreen(viewModel: SignInViewModel, navController: NavController) {

    BackHandler { navController.navigateUp() }

    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val signInMessage by viewModel.signInMessage.collectAsState()

    fun validateAndShowToasts() {
        if (uiState.email.isEmpty() || uiState.password.isEmpty()) {
            toast(context, context.getString(R.string.missing_fields))
        } else {
            viewModel.authenticate(uiState.email, uiState.password)
        }
    }

    LaunchedEffect(signInMessage) {
        when (signInMessage) {
            SignInMessage.UserLoggedIn -> {
                toast(context, context.getString(R.string.logged_in))
                navController.navigate("MainScreen")
            }

            SignInMessage.Error ->
                toast(context, context.getString(R.string.an_error_occurred_please_try_again))

            SignInMessage.MissingInformation ->
                toast(context, context.getString(R.string.missing_fields))

            SignInMessage.WrongPassword ->
                toast(context, context.getString(R.string.incorrect_password))

            SignInMessage.UserDoesNotExist ->
                toast(context, context.getString(R.string.user_not_found))

            SignInMessage.Initial -> {}
        }
    }

    fun clearSignInMessage() = { viewModel.clearSignInMessage() }

    SignInScreenContent(
        uiState = uiState,
        authAndShowToasts = { validateAndShowToasts() },
        navigate = { navController.navigate(it) },
        resetSignInMessage = { clearSignInMessage() }
    )
}

@Composable
fun SignInScreenContent(
    uiState: SignInScreenUiState,
    authAndShowToasts: () -> Unit,
    navigate: (String) -> Unit,
    resetSignInMessage: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painterResource(R.drawable.logo_bookscape),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(.6f),
            )
            Text(
                text = stringResource(R.string.book_scape),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(top = 16.dp),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(R.string.sign_in),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.secondary
            )
            BookScapeTextField(
                value = uiState.email,
                onValueChange = uiState.onEmailChange,
                label = stringResource(R.string.e_mail),
                modifier = Modifier.fillMaxWidth(.9f),
                keyboardType = KeyboardType.Email,
                isPassword = false
            )
            BookScapeTextField(
                value = uiState.password,
                onValueChange = uiState.onPasswordChange,
                label = stringResource(R.string.password),
                modifier = Modifier.fillMaxWidth(.9f),
                keyboardType = KeyboardType.Password,
                isPassword = true
            )
            Button(
                onClick = { authAndShowToasts() },
                content = {
                    Text(
                        text = stringResource(R.string.sign_in),
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            )
            Row {
                Text(
                    text = stringResource(R.string.don_t_have_an_account),
                    style = MaterialTheme.typography.labelSmall
                )
                Text(
                    text = stringResource(R.string.sign_up),
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.onSecondaryContainer)
                        .clickable {
                            navigate("SignUpScreen")
                            resetSignInMessage()
                        }
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun SignInScreenLightThemePreview() {
    BookScapeComposeTheme {
        SignInScreenContent(
            uiState = sampleSignInState,
            authAndShowToasts = {},
            navigate = {},
            resetSignInMessage = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SignInScreenDarkThemePreview() {
    BookScapeComposeTheme {
        SignInScreenContent(
            uiState = sampleSignInState,
            authAndShowToasts = {},
            navigate = {},
            resetSignInMessage = {}
        )
    }
}