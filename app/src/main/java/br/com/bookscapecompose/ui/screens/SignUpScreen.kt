package br.com.bookscapecompose.ui.screens

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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.bookscapecompose.R
import br.com.bookscapecompose.expressions.toast
import br.com.bookscapecompose.ui.components.BookScapeTextField
import br.com.bookscapecompose.ui.viewmodels.SignUpMessage
import br.com.bookscapecompose.ui.viewmodels.SignUpViewModel

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel,
    navController: NavController,
) {
    BackHandler { navController.navigateUp() }

    val context = LocalContext.current
    val state by viewModel.uiState.collectAsState()
    val message by viewModel.signUpMessage.collectAsState()

    fun validateAndShowToasts() {
        viewModel.addNewUser(
            email = state.email,
            username = state.username,
            password = state.password
        )
    }

    LaunchedEffect(message) {
        when (message) {
            SignUpMessage.Initial -> {}

            SignUpMessage.Error ->
                toast(context, "An error occurred. Please try again")

            SignUpMessage.MissingInformation ->
                toast(context, "Please, complete the missing fields!")

            SignUpMessage.UserIsAlreadyAdded ->
                toast(context, "You're already signed up. Please log in")

            SignUpMessage.UserSuccessfullyAdded -> {
                toast(context, "User added successfully!")
                navController.navigate("SignInScreen")
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
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
                text = stringResource(R.string.bookscape),
                fontSize = 26.sp,
                modifier = Modifier.padding(top = 16.dp),
                fontFamily = FontFamily(listOf(Font(R.font.kavoon))),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Sign Up",
                fontSize = 34.sp,
                fontFamily = FontFamily(listOf(Font(R.font.kavoon))),
                color = MaterialTheme.colorScheme.secondary
            )
            BookScapeTextField(
                value = state.email,
                onValueChange = state.onEmailChange,
                label = "E-mail",
                modifier = Modifier.fillMaxWidth(.9f),
                keyboardType = KeyboardType.Email,
                isPassword = false
            )
            BookScapeTextField(
                value = state.username,
                onValueChange = state.onUsernameChange,
                label = "Username",
                modifier = Modifier.fillMaxWidth(.9f),
                keyboardType = KeyboardType.Text,
                isPassword = false
            )
            BookScapeTextField(
                value = state.password,
                onValueChange = state.onPasswordChange,
                label = "Password",
                modifier = Modifier.fillMaxWidth(.9f),
                keyboardType = KeyboardType.Password,
                isPassword = true
            )
            Button(
                onClick = { validateAndShowToasts() },
                content = {
                    Text(
                        text = "Sign Up",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontFamily = FontFamily(listOf(Font(R.font.kavoon))),
                        fontSize = 18.sp
                    )
                }
            )
            Row {
                Text(
                    text = "Already have an account? ",
                    fontSize = 15.sp
                )
                Text(
                    text = "Sign in",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.onSecondaryContainer)
                        .clickable { navController.navigate("SignInScreen") }
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SignUpScreenPreview() {
//    SignUpScreen(viewModel(), rememberNavController())
}