package com.misenpai.arcana

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.misenpai.arcana.presentation.ui.navigation.AppNavigation
import com.misenpai.arcana.presentation.ui.themes.ArcanaTheme
import com.misenpai.arcana.presentation.ui.themes.ArcanaThemeWithViewModel
import com.misenpai.arcana.presentation.viewmodel.ArcanaViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: ArcanaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            ArcanaThemeWithViewModel(viewModel = viewModel) {
                AppNavigation(navController = navController, viewModel = viewModel)
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