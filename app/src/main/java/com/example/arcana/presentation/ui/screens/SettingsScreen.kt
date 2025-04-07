package com.example.arcana.presentation.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.arcana.R
import com.example.arcana.presentation.viewmodel.ArcanaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavHostController,
    viewModel: ArcanaViewModel = hiltViewModel()
) {
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Settings",
                        fontFamily = FontFamily(Font(R.font.poppins_bold)),
                        color = Color.Black
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White,
                    navigationIconContentColor = Color.Black,
                    titleContentColor = Color.Black
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
                onClick = { /* Handle contribute click */ }
            )

            // Report an issue
            SettingsItem(
                title = "Report an issue",
                value = "",
                onClick = { /* Handle report click */ }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Social Links
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // GitHub Icon
                IconButton(onClick = { /* Handle GitHub click */ }) {
                    // You would need to add GitHub icon resource
                    // For now using placeholder text
                    Text(text = "GH", modifier = Modifier.padding(8.dp))
                }

                // Discord Icon
                IconButton(onClick = { /* Handle Discord click */ }) {
                    // You would need to add Discord icon resource
                    // For now using placeholder text
                    Text(text = "DC", modifier = Modifier.padding(8.dp))
                }
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