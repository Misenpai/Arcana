package com.example.arcana

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.arcana.presentation.ui.navigation.AppNavigation
import com.example.arcana.presentation.ui.themes.ArcanaTheme
import com.example.arcana.presentation.ui.themes.ArcanaThemeWithViewModel
import com.example.arcana.presentation.viewmodel.ArcanaViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val viewModel: ArcanaViewModel = hiltViewModel()
            ArcanaThemeWithViewModel(viewModel = viewModel) {
                AppNavigation(navController = navController)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    androidx.compose.material3.Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ArcanaTheme(darkTheme = false) {
        Greeting("Android")
    }
}