package com.example.arcana.presentation.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.arcana.presentation.ui.components.ArcanaItem
import com.example.arcana.presentation.ui.components.LoadingScreen
import com.example.arcana.presentation.ui.components.SubheaderItem
import com.example.arcana.presentation.viewmodel.ArcanaViewModel
import com.example.arcana.presentation.viewmodel.DetailState

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

    LaunchedEffect(type, itemId) {
        when (type) {
            "language" -> viewModel.fetchSections(itemId)
            "section" -> viewModel.fetchHeaders(itemId)
            "header" -> viewModel.fetchSubheaders(itemId)
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = title, style = MaterialTheme.typography.headlineSmall)
        when (val state = detailState.value) {
            is DetailState.Loading -> LoadingScreen()
            is DetailState.LanguageDetail -> {
                LazyColumn {
                    items(state.sections) { section ->
                        ArcanaItem(
                            item = section,
                            onItemClick = {
                                navController.navigate("section_detail/${section.id}?title=${section.title}")
                            }
                        )
                    }
                }
            }
            is DetailState.SectionDetail -> {
                LazyColumn {
                    items(state.headers) { header ->
                        ArcanaItem(
                            item = header,
                            onItemClick = {
                                navController.navigate("header_detail/${header.id}?title=${header.title}")
                            }
                        )
                    }
                }
            }
            is DetailState.HeaderDetail -> {
                LazyColumn {
                    items(state.subheaders) { subheader ->
                        SubheaderItem(
                            subheader = subheader,
                            onItemClick = { /* Handle if needed */ }
                        )
                    }
                }
            }
            is DetailState.Error -> Text(text = "Error: ${state.exception.message}")
        }
    }
}