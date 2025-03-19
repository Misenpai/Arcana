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
import com.example.arcana.domain.model.ArcanaDomainModel

@Composable
fun ArcanaItem(
    item: ArcanaDomainModel,
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
            // Placeholder for icon (replace with actual resource IDs)
            Icon(
                painter = painterResource(id = android.R.drawable.ic_menu_gallery), // Replace with language-specific icons
                contentDescription = item.name,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = item.name ?: item.sections?.firstOrNull()?.title ?: "Unknown",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}