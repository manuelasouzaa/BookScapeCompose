package br.com.bookscapecompose.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import br.com.bookscapecompose.ui.navigation.BookScapeNavHost
import br.com.bookscapecompose.ui.theme.BookScapeComposeTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        val keepSplashScreen = mutableStateOf(true)

        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition { keepSplashScreen.value }

        lifecycleScope.launch {
            delay(100)
            keepSplashScreen.value = false
        }

        enableEdgeToEdge()
        setContent {
            BookScapeComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        val navController = rememberNavController()
                        BookScapeNavHost(navController)
                    }
                }
            }
        }
    }
}