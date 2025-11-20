package com.jun.husbandsrecipe.domain.repository

import com.jun.husbandsrecipe.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    fun getRecipeList(): Flow<List<Recipe>>
    suspend fun getRecipeDetail(recipeId: String): Recipe?
}
