package com.misenpai.arcana.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.misenpai.arcana.R
import com.misenpai.arcana.domain.model.HeaderDomainModel

@Composable
fun HeaderItem(
    header: HeaderDomainModel,
    onItemClick: () -> Unit
) {
    Text(
        text = header.title,
        fontSize = 18.sp,
        fontFamily = FontFamily(Font(R.font.poppins_semibold)),
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick() }
            .padding(16.dp)
    )
}