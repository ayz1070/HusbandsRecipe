package com.jun.husbandsrecipe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jun.husbandsrecipe.presentation.recipe.detail.RecipeDetailScreen
import com.jun.husbandsrecipe.presentation.recipe.list.RecipeListScreen
import com.jun.husbandsrecipe.ui.theme.HusbandsRecipeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HusbandsRecipeTheme {
                val navController = rememberNavController()
                NavHost(
                        navController = navController,
                        startDestination = "login",
                        modifier = Modifier.fillMaxSize()
                ) {
                    composable("recipe_list") {
                        RecipeListScreen(
                                onRecipeClick = { recipeId ->
                                    navController.navigate("recipe_detail/$recipeId")
                                }
                        )
                    }
                    composable("recipe_detail/{recipeId}") { backStackEntry ->
                        val recipeId =
                                backStackEntry.arguments?.getString("recipeId") ?: return@composable
                        RecipeDetailScreen(
                                recipeId = recipeId,
                                onBackClick = { navController.popBackStack() }
                        )
                    }
                    composable("login") {
                        com.jun.husbandsrecipe.presentation.login.LoginScreen(
                                onLoginSuccess = {
                                    navController.navigate("recipe_list") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                }
                        )
                    }
                }
            }
        }
    }
}
