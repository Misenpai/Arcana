package com.misenpai.arcana.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.misenpai.arcana.presentation.ui.screens.ArcanaDetailScreen
import com.misenpai.arcana.presentation.ui.screens.ArcanaListScreen
import com.misenpai.arcana.presentation.ui.screens.SettingsScreen
import com.misenpai.arcana.presentation.viewmodel.ArcanaViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    viewModel: ArcanaViewModel // Shared ViewModel parameter
) {
    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            ArcanaListScreen(
                navController = navController,
                viewModel = viewModel // Pass shared ViewModel
            )
        }
        composable(
            route = "language_detail/{languageId}?title={title}",
            arguments = listOf(
                navArgument("languageId") { type = NavType.IntType },
                navArgument("title") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val languageId = backStackEntry.arguments?.getInt("languageId") ?: 0
            val title = backStackEntry.arguments?.getString("title") ?: "Language"
            ArcanaDetailScreen(
                type = "language",
                itemId = languageId,
                title = title,
                navController = navController,
                viewModel = viewModel, // Pass shared ViewModel
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(
            route = "section_detail/{sectionId}?title={title}",
            arguments = listOf(
                navArgument("sectionId") { type = NavType.IntType },
                navArgument("title") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val sectionId = backStackEntry.arguments?.getInt("sectionId") ?: 0
            val title = backStackEntry.arguments?.getString("title") ?: "Section"
            ArcanaDetailScreen(
                type = "section",
                itemId = sectionId,
                title = title,
                navController = navController,
                viewModel = viewModel, // Pass shared ViewModel
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(
            route = "header_detail/{headerId}?title={title}",
            arguments = listOf(
                navArgument("headerId") { type = NavType.IntType },
                navArgument("title") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val headerId = backStackEntry.arguments?.getInt("headerId") ?: 0
            val title = backStackEntry.arguments?.getString("title") ?: "Header"
            ArcanaDetailScreen(
                type = "header",
                itemId = headerId,
                title = title,
                navController = navController,
                viewModel = viewModel, // Pass shared ViewModel
                onBackClick = { navController.popBackStack() }
            )
        }
        composable("settings") {
            SettingsScreen(
                navController = navController,
                viewModel = viewModel // Pass shared ViewModel
            )
        }
    }
}