package com.example.arcana.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.arcana.domain.model.ArcanaDomainModel
import com.example.arcana.domain.model.Resource
import com.example.arcana.presentation.ui.components.ArcanaItem
import com.example.arcana.presentation.ui.components.LoadingScreen
import com.example.arcana.presentation.viewmodel.ArcanaViewModel

@Composable
fun ArcanaDetailScreen(
    viewModel: ArcanaViewModel = hiltViewModel(),
    itemId: Int,
    onBackClick: () -> Unit
) {
    val detailState = viewModel.detailState.collectAsState()
    var currentLevel by remember { mutableStateOf(0) }
    var currentId by remember { mutableStateOf(itemId) }

    when (val state = detailState.value) {
        is Resource.Loading -> LoadingScreen()
        is Resource.Success -> {
            val items = state.data
            Column(modifier = Modifier.padding(16.dp)) {
                when (currentLevel) {
                    0 -> {
                        val language = items.firstOrNull { it.id == currentId }
                        Text(text = language?.name ?: "Details", style = MaterialTheme.typography.headlineSmall)
                        language?.sections?.let { sections ->
                            LazyColumn {
                                items(sections) { section ->
                                    SectionItem(
                                        section = section,
                                        onItemClick = {
                                            viewModel.fetchHeaders(section.id)
                                            currentLevel = 1
                                            currentId = section.id
                                        }
                                    )
                                }
                            }
                        }
                    }
                    1 -> {
                        val headers = items.filterIsInstance<ArcanaDomainModel.Section.Header>()
                            .filter { it.id == currentId }
                        LazyColumn {
                            items(headers) { header ->
                                HeaderItem(
                                    header = header,
                                    onItemClick = {
                                        viewModel.fetchSubheaders(header.id)
                                        currentLevel = 2
                                        currentId = header.id
                                    }
                                )
                            }
                        }
                    }
                    2 -> {
                        val subheaders = items.filterIsInstance<ArcanaDomainModel.Section.Header.Subheader>()
                            .filter { it.id == currentId }
                        LazyColumn {
                            items(subheaders) { subheader ->
                                SubheaderItem(
                                    subheader = subheader,
                                    onItemClick = { /* Handle subheader click if needed */ }
                                )
                            }
                        }
                    }
                }
            }
        }
        is Resource.Error -> Text(text = "Error: ${state.exception.message}")
    }
}

@Composable
fun SectionItem(
    section: ArcanaDomainModel.Section,
    onItemClick: () -> Unit,
    backgroundColor: Color = MaterialTheme.colorScheme.primaryContainer
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .clickable(onClick = onItemClick)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = android.R.drawable.ic_menu_gallery),
                contentDescription = section.title,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = section.title,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun HeaderItem(
    header: ArcanaDomainModel.Section.Header,
    onItemClick: () -> Unit,
    backgroundColor: Color = MaterialTheme.colorScheme.primaryContainer
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .clickable(onClick = onItemClick)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = android.R.drawable.ic_menu_gallery),
                contentDescription = header.title,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = header.title,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun SubheaderItem(
    subheader: ArcanaDomainModel.Section.Header.Subheader,
    onItemClick: () -> Unit,
    backgroundColor: Color = MaterialTheme.colorScheme.primaryContainer
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .clickable(onClick = onItemClick)
            .padding(16.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_gallery),
                    contentDescription = subheader.title,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = subheader.title,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                text = subheader.content,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = subheader.code,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}