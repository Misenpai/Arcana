package com.example.arcana.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.arcana.R
import com.example.arcana.domain.model.Resource
import com.example.arcana.presentation.ui.components.LanguageItem
import com.example.arcana.presentation.ui.components.LoadingScreen
import com.example.arcana.presentation.ui.themes.ArcanaThemeWithViewModel
import com.example.arcana.presentation.viewmodel.ArcanaViewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.text.TextStyle
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArcanaListScreen(
    navController: NavHostController,
    viewModel: ArcanaViewModel = hiltViewModel()
) {
    val languagesState = viewModel.languagesState.collectAsState().value
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val isDarkTheme by viewModel.isDarkTheme.collectAsState() // Collect isDarkTheme as state

    // Filter languages based on search query
    val filteredLanguages = when (languagesState) {
        is Resource.Success -> languagesState.data.filter {
            it.name.contains(searchQuery.text, ignoreCase = true)
        }
        else -> emptyList()
    }

    // Add LaunchedEffect for debounce
    LaunchedEffect(searchQuery.text) {
        snapshotFlow { searchQuery.text }
            .debounce(300) // 300ms debounce
            .distinctUntilChanged()
            .collect { query ->
                // No additional action needed; filtering is handled in filteredLanguages
            }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("settings") }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = MaterialTheme.colorScheme.onPrimary // Ensures visibility
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.toggleTheme() }) {
                        Icon(
                            imageVector = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                            contentDescription = "Toggle Theme",
                            tint = MaterialTheme.colorScheme.onPrimary // Ensures visibility
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            // Search Box with icon and border
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                placeholder = { Text("Search languages...") },
                singleLine = true,
                shape = MaterialTheme.shapes.extraLarge, // Makes the TextField rounded
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    cursorColor = MaterialTheme.colorScheme.primary,
                    focusedIndicatorColor = Color.Transparent, // Removes the focus indicator line
                    unfocusedIndicatorColor = Color.Transparent, // Removes the unfocused indicator line
                )
            )

            when (languagesState) {
                is Resource.Loading -> LoadingScreen()
                is Resource.Success -> {
                    LazyColumn(contentPadding = PaddingValues(8.dp)) {
                        items(filteredLanguages) { language ->
                            Column(modifier = Modifier.padding(vertical = 4.dp)) {
                                LanguageItem(
                                    item = language,
                                    onItemClick = {
                                        Log.d("ArcanaListScreen", "Navigating to language_detail with id: ${language.id}")
                                        navController.navigate("language_detail/${language.id}?title=${language.name}")
                                    },
                                    backgroundColor = when (language.name) {
                                        "Python" -> Color(0xFF4A90E2)
                                        "Javascript" -> Color(0xFFF5A623)
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
                }
                is Resource.Error -> Text(text = "Error: ${languagesState.exception.message}")
            }
        }
    }
}