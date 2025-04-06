package com.example.arcana.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.arcana.domain.model.SubheaderDomainModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.example.arcana.R

@Composable
fun SubheaderItem(
    subheader: SubheaderDomainModel,
    onItemClick: () -> Unit
) {
    val clipboardManager: ClipboardManager = LocalClipboardManager.current

    Column(
        modifier = Modifier
            .clickable { onItemClick() }
            .padding(8.dp)
    ) {
        Text(
            text = subheader.title,
            fontFamily = FontFamily(Font(R.font.poppins_medium)),
            style = MaterialTheme.typography.titleMedium
        )
        Divider(
            modifier = Modifier
                .padding(vertical = 8.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
        )
        Text(
            text = subheader.content,
            fontFamily = FontFamily(Font(R.font.poppins)),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        ElevatedCard(
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .padding(top = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.Top // or Alignment.CenterVertically if you prefer
            ) {
                Text(
                    text = subheader.code,
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.weight(1f), // takes all available space
                    textAlign = TextAlign.Start
                )
                IconButton(onClick = {
                    clipboardManager.setText(AnnotatedString(subheader.code))
                }) {
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "Copy code"
                    )
                }
            }
        }

    }
}