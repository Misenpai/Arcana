package com.example.arcana.presentation.ui.screens

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.arcana.domain.model.HeaderDomainModel
import com.example.arcana.domain.model.SubheaderDomainModel
import com.example.arcana.presentation.ui.components.HeaderItem
import com.example.arcana.presentation.ui.components.LoadingScreen
import com.example.arcana.presentation.ui.components.SectionItem
import com.example.arcana.presentation.ui.components.SubheaderItem
import com.example.arcana.presentation.viewmodel.ArcanaViewModel
import com.example.arcana.presentation.viewmodel.DetailState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArcanaDetailScreen(
    type: String,
    itemId: Int,
    title: String,
    navController: NavHostController,
    viewModel: ArcanaViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val detailState = viewModel.detailState.collectAsState()
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()

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

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.toggleTheme() }) {
                        Icon(
                            imageVector = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                            contentDescription = "Toggle Theme",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
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
                is DetailState.Error -> Text(text = "Error: ${state.exception.message}")
            }
        }
    }
}