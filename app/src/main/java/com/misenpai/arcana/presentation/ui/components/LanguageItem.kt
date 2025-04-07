package com.misenpai.arcana.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.misenpai.arcana.domain.model.HeaderDomainModel
import com.misenpai.arcana.domain.model.LanguageDomainModel
import com.misenpai.arcana.domain.model.SectionDomainModel
import com.misenpai.arcana.domain.model.SubheaderDomainModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.misenpai.arcana.R

@Composable
fun LanguageItem(
    item: Any,
    onItemClick: () -> Unit,
    backgroundColor: Color = Color.Transparent
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (backgroundColor == Color.Transparent) MaterialTheme.colorScheme.surfaceVariant else backgroundColor
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable { onItemClick() }
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Icon on the left
            val iconResId = when (item) {
                is LanguageDomainModel -> {
                    when (item.name.lowercase()) {
                        "python" -> com.misenpai.arcana.R.drawable.python
                        "javascript" -> com.misenpai.arcana.R.drawable.javascript
                        "swift" -> com.misenpai.arcana.R.drawable.swift
                        "kotlin" -> com.misenpai.arcana.R.drawable.kotlin
                        "c++" -> com.misenpai.arcana.R.drawable.cpp
                        "java" -> com.misenpai.arcana.R.drawable.java
                        "css" -> com.misenpai.arcana.R.drawable.css
                        else -> null
                    }
                }
                else -> null
            }
            if (iconResId != null) {
                Icon(
                    painter = painterResource(id = iconResId),
                    contentDescription = "${(item as? LanguageDomainModel)?.name} icon",
                    modifier = Modifier
                        .size(48.dp)
                        .padding(end = 8.dp)
                )
            }

            // Text in the middle
            Text(
                text = when (item) {
                    is LanguageDomainModel -> item.name
                    is SectionDomainModel -> item.title
                    is HeaderDomainModel -> item.title
                    is SubheaderDomainModel -> item.title
                    else -> "Unknown"
                },
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start,
            )
            // Arrow icon on the right
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Navigate to details",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}