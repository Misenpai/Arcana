package com.misenpai.arcana.presentation.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.misenpai.arcana.R
import com.misenpai.arcana.presentation.viewmodel.ArcanaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavHostController,
    viewModel: ArcanaViewModel
) {
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Settings",
                        fontFamily = FontFamily(Font(R.font.poppins_bold)),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
        ) {
            // General Section
            SectionHeader(title = "General")

            // Theme Setting
            SettingsItem(
                title = "Theme",
                value = if (isDarkTheme) "Dark" else "Light",
                onClick = { viewModel.toggleTheme() }
            )

            // Language Setting
            SettingsItem(
                title = "Language",
                value = "English",
                onClick = { /* Handle language change */ }
            )

            Divider(
                modifier = Modifier.padding(vertical = 16.dp),
                color = Color.LightGray.copy(alpha = 0.5f)
            )

            // More Options Section
            SectionHeader(title = "More Options")

            // Author
            SettingsItem(
                title = "Author",
                value = "@Misenpai",
                onClick = { /* Handle author click */ }
            )

            // Contribute
            SettingsItem(
                title = "Contribute",
                value = "",
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Misenpai/Arcana/pulls"))
                    context.startActivity(intent)
                }
            )

            // Report an issue
            SettingsItem(
                title = "Report an issue",
                value = "",
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Misenpai/Arcana/issues"))
                    context.startActivity(intent)
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Social Links
// Social Links
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // GitHub Icon - Using clickable instead of IconButton to remove circular background
                Icon(
                    painter = painterResource(id = R.drawable.github),
                    contentDescription = "GitHub",
                    modifier = Modifier
                        .size(64.dp)
                        .clickable {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Misenpai/Arcana"))
                            context.startActivity(intent)
                        }
                        .padding(horizontal = 8.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )

                // Instagram Icon - Using clickable instead of IconButton to remove circular background
                Icon(
                    painter = painterResource(id = R.drawable.instagram),
                    contentDescription = "Instagram",
                    modifier = Modifier
                        .size(64.dp)
                        .clickable {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/misenpai_/"))
                            context.startActivity(intent)
                        }
                        .padding(horizontal = 8.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }

            // Version Info
            Text(
                text = "Arcana v0.1.0",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                textAlign = TextAlign.Center,
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    )
}

@Composable
fun SettingsItem(
    title: String,
    value: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        )

        if (value.isNotEmpty()) {
            Text(
                text = value,
                color = Color.Gray,
                fontSize = 16.sp
            )
        }
    }
}