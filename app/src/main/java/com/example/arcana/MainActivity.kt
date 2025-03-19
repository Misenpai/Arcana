package com.example.arcana

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.arcana.domain.model.Resource
import com.example.arcana.presentation.navigation.AppNavigation
import com.example.arcana.presentation.ui.screens.ArcanaListScreen
import com.example.arcana.presentation.ui.themes.ArcanaTheme
import com.example.arcana.presentation.viewmodel.ArcanaViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArcanaTheme {
                val navController = rememberNavController()
                val viewModel: ArcanaViewModel = hiltViewModel()
                val languagesState by viewModel.languagesState.collectAsStateWithLifecycle()

                // Trigger initial data fetch
                if (languagesState !is Resource.Success) {
                    viewModel.fetchLanguages()
                }

                AppNavigation(navController = navController)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ArcanaTheme {
        Greeting("Android")
    }
}