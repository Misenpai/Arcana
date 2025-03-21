package com.example.arcana.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.arcana.domain.model.HeaderDomainModel
import com.example.arcana.domain.model.LanguageDomainModel
import com.example.arcana.domain.model.SectionDomainModel
import com.example.arcana.domain.model.SubheaderDomainModel

@Composable
fun ArcanaItem(
    item: Any,
    onItemClick: () -> Unit,
    backgroundColor: Color = MaterialTheme.colorScheme.primaryContainer
) {
    val (text, description) = when (item) {
        is LanguageDomainModel -> item.name to item.description
        is SectionDomainModel -> item.title to null
        is HeaderDomainModel -> item.title to null
        is SubheaderDomainModel -> item.title to item.content
        else -> "Unknown" to null
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .clickable(onClick = onItemClick)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = android.R.drawable.ic_menu_gallery), // Replace with actual icons
                contentDescription = text,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun SubheaderItem(
    subheader: SubheaderDomainModel,
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
                contentDescription = subheader.title,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = subheader.title,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}