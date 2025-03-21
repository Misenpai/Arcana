package com.example.arcana.presentation.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.arcana.R
import com.example.arcana.domain.model.Resource
import com.example.arcana.presentation.ui.components.ArcanaItem
import com.example.arcana.presentation.ui.components.LoadingScreen
import com.example.arcana.presentation.viewmodel.ArcanaViewModel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArcanaListScreen(
    navController: NavHostController,
    viewModel: ArcanaViewModel = hiltViewModel()
) {
    val languagesState = viewModel.languagesState.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) }
            )
        }
    ) { paddingValues ->
        when (languagesState) {
            is Resource.Loading -> LoadingScreen()
            is Resource.Success -> {
                LazyColumn(contentPadding = paddingValues) {
                    items(languagesState.data) { language ->
                        ArcanaItem(
                            item = language,
                            onItemClick = {
                                navController.navigate("language_detail/${language.id}?title=${language.name}")
                            },
                            backgroundColor = when (language.name) {
                                "Python" -> Color(0xFF4A90E2)
                                "JavaScript" -> Color(0xFFF5A623)
                                "Swift" -> Color(0xFFFA6400)
                                "Rust" -> Color(0xFFCE4A2F)
                                "Kotlin" -> Color(0xFF7F52FF)
                                "Dart" -> Color(0xFF35C2C1)
                                "Go" -> Color(0xFF00ADD8)
                                else -> Color.Gray
                            }
                        )
                    }
                }
            }
            is Resource.Error -> Text(text = "Error: ${languagesState.exception.message}")
        }
    }
}