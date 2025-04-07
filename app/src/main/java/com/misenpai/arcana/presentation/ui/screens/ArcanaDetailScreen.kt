package com.misenpai.arcana.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.misenpai.arcana.R
import com.misenpai.arcana.domain.model.HeaderDomainModel
import com.misenpai.arcana.domain.model.SubheaderDomainModel
import com.misenpai.arcana.presentation.ui.components.HeaderItem
import com.misenpai.arcana.presentation.ui.components.LoadingScreen
import com.misenpai.arcana.presentation.ui.components.SectionItem
import com.misenpai.arcana.presentation.ui.components.SubheaderItem
import com.misenpai.arcana.presentation.viewmodel.ArcanaViewModel
import com.misenpai.arcana.presentation.viewmodel.DetailState
import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArcanaDetailScreen(
    type: String,
    itemId: Int,
    title: String,
    navController: NavHostController,
    viewModel: ArcanaViewModel,
    onBackClick: () -> Unit
) {
    val detailState = viewModel.detailState.collectAsState()
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()
    var showErrorDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(type, itemId) {
        Log.d("ArcanaDetailScreen", "Received $type with itemId: $itemId")
        if (itemId == 0) {
            Log.e("ArcanaDetailScreen", "Invalid itemId (0) for type: $type")
            viewModel.setErrorState(Exception("Invalid $type ID"))
        } else {
            when (type) {
                "language" -> viewModel.fetchSections(itemId)
                "section" -> viewModel.fetchHeaders(itemId)
                "header" -> viewModel.selectHeader(itemId)
            }
        }
    }

    // Custom AlertDialog for connection issues
    if (showErrorDialog && detailState.value is DetailState.Error) {
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
                        // Retry based on the current type and itemId
                        when (type) {
                            "language" -> viewModel.fetchSections(itemId)
                            "section" -> viewModel.fetchHeaders(itemId)
                            "header" -> viewModel.selectHeader(itemId)
                        }
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
                    Text(
                        text = title.uppercase(),
                        fontFamily = FontFamily(Font(R.font.poppins_bold)),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.toggleTheme()
                        Log.d("ThemeToggle", "IconButton clicked at ${System.currentTimeMillis()}")
                    }) {
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
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            when (val state = detailState.value) {
                is DetailState.Loading -> LoadingScreen()
                is DetailState.LanguageDetail -> {
                    Log.d("ArcanaDetailScreen", "Displaying ${state.sections.size} sections")
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(state.sections) { section ->
                            SectionItem(
                                section = section,
                                onItemClick = {
                                    navController.navigate("section_detail/${section.id}?title=${section.title}")
                                }
                            )
                        }
                    }
                }
                is DetailState.SectionDetail -> {
                    Log.d("ArcanaDetailScreen", "Displaying ${state.headers.size} headers")
                    LazyColumn {
                        items(state.headers.flatMap { header ->
                            listOf(header) + header.subheaders
                        }) { item ->
                            when (item) {
                                is HeaderDomainModel -> {
                                    HeaderItem(
                                        header = item,
                                        onItemClick = {}
                                    )
                                }
                                is SubheaderDomainModel -> {
                                    SubheaderItem(
                                        subheader = item,
                                        onItemClick = {}
                                    )
                                }
                            }
                        }
                    }
                }
                is DetailState.HeaderDetail -> {
                    Log.d("ArcanaDetailScreen", "Rendering ${state.subheaders.size} subheaders")
                    LazyColumn {
                        items(state.subheaders) { subheader ->
                            SubheaderItem(
                                subheader = subheader,
                                onItemClick = {}
                            )
                        }
                    }
                }
                is DetailState.Error -> {
                    // Trigger dialog when error state is detected
                    showErrorDialog = true
                }
            }
        }
    }
}