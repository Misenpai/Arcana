package com.example.arcana.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.arcana.presentation.ui.screens.ArcanaDetailScreen
import com.example.arcana.presentation.ui.screens.ArcanaListScreen
import androidx.navigation.navArgument

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController, startDestination = "list") {
        composable("list") {
            ArcanaListScreen(navController = navController)
        }
        composable("detail/{itemId}") { backStackEntry ->
            val itemId = backStackEntry.arguments?.getInt("itemId") ?: 0
            ArcanaDetailScreen(
                itemId = itemId,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable("headers/{sectionId}") { backStackEntry ->
            val sectionId = backStackEntry.arguments?.getInt("sectionId") ?: 0
            ArcanaDetailScreen(
                itemId = sectionId,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable("subheaders/{headerId}") { backStackEntry ->
            val headerId = backStackEntry.arguments?.getInt("headerId") ?: 0
            ArcanaDetailScreen(
                itemId = headerId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}