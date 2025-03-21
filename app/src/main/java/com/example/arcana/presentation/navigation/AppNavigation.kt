package com.example.arcana.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.arcana.presentation.ui.screens.ArcanaDetailScreen
import com.example.arcana.presentation.ui.screens.ArcanaListScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController, startDestination = "list") {
        composable("list") {
            ArcanaListScreen(navController = navController)
        }
        composable(
            "language_detail/{languageId}?title={title}",
            arguments = listOf(
                navArgument("languageId") { type = NavType.IntType },
                navArgument("title") { type = NavType.StringType; defaultValue = "Details" }
            )
        ) { backStackEntry ->
            val languageId = backStackEntry.arguments?.getInt("languageId") ?: 0
            val title = backStackEntry.arguments?.getString("title") ?: "Details"
            ArcanaDetailScreen(
                type = "language",
                itemId = languageId,
                title = title,
                navController = navController,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(
            "section_detail/{sectionId}?title={title}",
            arguments = listOf(
                navArgument("sectionId") { type = NavType.IntType },
                navArgument("title") { type = NavType.StringType; defaultValue = "Details" }
            )
        ) { backStackEntry ->
            val sectionId = backStackEntry.arguments?.getInt("sectionId") ?: 0
            val title = backStackEntry.arguments?.getString("title") ?: "Details"
            ArcanaDetailScreen(
                type = "section",
                itemId = sectionId,
                title = title,
                navController = navController,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(
            "header_detail/{headerId}?title={title}",
            arguments = listOf(
                navArgument("headerId") { type = NavType.IntType },
                navArgument("title") { type = NavType.StringType; defaultValue = "Details" }
            )
        ) { backStackEntry ->
            val headerId = backStackEntry.arguments?.getInt("headerId") ?: 0
            val title = backStackEntry.arguments?.getString("title") ?: "Details"
            ArcanaDetailScreen(
                type = "header",
                itemId = headerId,
                title = title,
                navController = navController,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}