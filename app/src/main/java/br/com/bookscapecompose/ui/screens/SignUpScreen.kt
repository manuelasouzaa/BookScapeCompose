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
import br.com.bookscapecompose.sampledata.sampleSignUpState
import br.com.bookscapecompose.ui.components.BookScapeTextField
import br.com.bookscapecompose.ui.theme.BookScapeComposeTheme
import br.com.bookscapecompose.ui.uistate.SignUpScreenUiState
import br.com.bookscapecompose.ui.viewmodels.SignUpMessage
import br.com.bookscapecompose.ui.viewmodels.SignUpViewModel

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel,
    navController: NavController,
) {
    BackHandler { navController.navigateUp() }

    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val signUpMessage by viewModel.signUpMessage.collectAsState()

    fun validateAndShowToasts() {
        viewModel.addNewUser(
            email = uiState.email,
            username = uiState.username,
            password = uiState.password
        )
    }

    LaunchedEffect(signUpMessage) {
        when (signUpMessage) {
            SignUpMessage.Initial -> {}

            SignUpMessage.Error ->
                toast(context, context.getString(R.string.an_error_occurred_please_try_again))

            SignUpMessage.MissingInformation ->
                toast(context, context.getString(R.string.missing_fields))

            SignUpMessage.UserIsAlreadyAdded ->
                toast(context, context.getString(R.string.already_signed_up))

            SignUpMessage.UserSuccessfullyAdded -> {
                toast(context, context.getString(R.string.user_added_successfully))
                navController.navigate("SignInScreen")
            }
        }
    }

    SignUpScreenContent(
        uiState = uiState,
        showToasts = { validateAndShowToasts() },
        navigate = { navController.navigate(it) },
        resetSignUpMessage = { viewModel.clearSignUpMessage() }
    )
}

@Composable
fun SignUpScreenContent(
    uiState: SignUpScreenUiState,
    showToasts: () -> Unit,
    navigate: (String) -> Unit,
    resetSignUpMessage: () -> Unit,
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
                text = stringResource(R.string.sign_up_title),
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
                value = uiState.username,
                onValueChange = uiState.onUsernameChange,
                label = stringResource(R.string.username),
                modifier = Modifier.fillMaxWidth(.9f),
                keyboardType = KeyboardType.Text,
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
                onClick = { showToasts() },
                content = {
                    Text(
                        text = stringResource(R.string.sign_up),
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            )
            Row {
                Text(
                    text = stringResource(R.string.already_have_an_account),
                    style = MaterialTheme.typography.labelSmall
                )
                Text(
                    text = stringResource(R.string.sign_in),
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.onSecondaryContainer)
                        .clickable {
                            navigate("SignInScreen")
                            resetSignUpMessage()
                        }
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun SignUpScreenLightModePreview() {
    BookScapeComposeTheme {
        SignUpScreenContent(
            uiState = sampleSignUpState,
            showToasts = {},
            navigate = {},
            resetSignUpMessage = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SignUpScreenDarkModePreview() {
    BookScapeComposeTheme {
        SignUpScreenContent(
            uiState = sampleSignUpState,
            showToasts = {},
            navigate = {},
            resetSignUpMessage = {}
        )
    }
}