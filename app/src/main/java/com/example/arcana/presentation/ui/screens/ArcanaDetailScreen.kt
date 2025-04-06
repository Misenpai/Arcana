package com.example.arcana.presentation.ui.screens

import android.util.Log
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
import androidx.compose.ui.graphics.Color

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

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = title, style = MaterialTheme.typography.headlineSmall)
        when (val state = detailState.value) {
            is DetailState.Loading -> LoadingScreen()
            is DetailState.LanguageDetail -> {
                Log.d("ArcanaDetailScreen", "Displaying ${state.sections.size} sections")
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
                Log.d("ArcanaDetailScreen", "Displaying ${state.headers.size} headers")
                LazyColumn {
                    items(state.headers.flatMap { header ->
                        listOf(header) + header.subheaders
                    }) { item ->
                        when (item) {
                            is com.example.arcana.domain.model.HeaderDomainModel -> {
                                ArcanaItem(
                                    item = item,
                                    onItemClick = {},
                                    backgroundColor = Color.Transparent
                                )
                            }
                            is com.example.arcana.domain.model.SubheaderDomainModel -> {
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