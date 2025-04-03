package com.example.arcana.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.arcana.domain.model.*

@Composable
fun ArcanaItem(
    item: Any,
    onItemClick: () -> Unit,
    backgroundColor: Color = Color.Transparent // Default to transparent if not specified
) {
    Text(
        text = when (item) {
            is LanguageDomainModel -> item.name
            is SectionDomainModel -> item.title
            is HeaderDomainModel -> item.title
            is SubheaderDomainModel -> item.title
            else -> "Unknown"
        },
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier
            .background(backgroundColor) // Apply the background color
            .clickable { onItemClick() }
            .padding(8.dp)
    )
}