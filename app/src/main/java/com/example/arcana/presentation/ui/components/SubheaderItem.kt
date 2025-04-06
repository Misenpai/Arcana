package com.example.arcana.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.arcana.domain.model.SubheaderDomainModel

@Composable
fun SubheaderItem(
    subheader: SubheaderDomainModel,
    onItemClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onItemClick() }
            .padding(8.dp)
    ) {
        Text(text = subheader.title, style = MaterialTheme.typography.titleMedium)
        Text(text = subheader.content, style = MaterialTheme.typography.bodyMedium)
        // Optional: Improve code display
        Box(
            modifier = Modifier
                .background(Color.LightGray)
                .padding(4.dp)
        ) {
            Text(text = subheader.code, style = MaterialTheme.typography.bodySmall)
        }
    }
}