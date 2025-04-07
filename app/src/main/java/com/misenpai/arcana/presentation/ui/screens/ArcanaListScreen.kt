package com.misenpai.arcana.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.navigation.NavHostController
import com.misenpai.arcana.R
import com.misenpai.arcana.domain.model.Resource
import com.misenpai.arcana.presentation.ui.components.LanguageItem
import com.misenpai.arcana.presentation.ui.components.LoadingScreen
import com.misenpai.arcana.presentation.viewmodel.ArcanaViewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.platform.LocalContext
import androidx.activity.ComponentActivity
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArcanaListScreen(
    navController: NavHostController,
    viewModel: ArcanaViewModel
) {
    val languagesState = viewModel.languagesState.collectAsState().value
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()
    var showErrorDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

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

    // Custom AlertDialog for connection issues
    if (showErrorDialog && languagesState is Resource.Error) {
        AlertDialog(
            icon = {
                Icon(
                    imageVector = Icons.Default.Error,
                    contentDescription = "Error Icon",
                    tint = MaterialTheme.colorScheme.error
                )
            },
            title = {
                Text(
                    text = "Connection Issue",
                    style = MaterialTheme.typography.headlineSmall
                )
            },
            text = {
                Text(
                    text = "Failed to connect to the server. Check your internet connection.",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            onDismissRequest = {
                showErrorDialog = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showErrorDialog = false
                        // Retry fetching languages
                        viewModel.fetchLanguages()
                    }
                ) {
                    Text("Retry")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showErrorDialog = false
                        // Exit the app
                        (context as? ComponentActivity)?.finish()
                    }
                ) {
                    Text("Exit")
                }
            }
        )
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
                            text = stringResource(id = R.string.app_name).uppercase(),
                            fontFamily = FontFamily(Font(R.font.poppins_bold)),
                            modifier = Modifier.align(Alignment.CenterVertically),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("settings") }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.toggleTheme() }) {
                        Icon(
                            imageVector = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                            contentDescription = "Toggle Theme",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                    actionIconContentColor = MaterialTheme.colorScheme.onSurface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
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
                shape = MaterialTheme.shapes.extraLarge,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    cursorColor = MaterialTheme.colorScheme.primary,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
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
                                        "Css" -> Color(0xFF2965F1)
                                        "Kotlin" -> Color(0xFF7F52FF)
                                        "Java" -> Color(0xFF5382A1)
                                        "C++" -> Color(0xFF00599C)
                                        else -> Color.Gray
                                    }
                                )
                            }
                        }
                    }
                }
                is Resource.Error -> {
                    // Trigger dialog when error state is detected
                    showErrorDialog = true
                }
            }
        }
    }
}