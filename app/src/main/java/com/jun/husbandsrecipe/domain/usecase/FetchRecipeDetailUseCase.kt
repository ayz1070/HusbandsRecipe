package com.jun.husbandsrecipe.domain.usecase

import com.jun.husbandsrecipe.domain.model.Recipe
import com.jun.husbandsrecipe.domain.repository.RecipeRepository
import javax.inject.Inject

class FetchRecipeDetailUseCase @Inject constructor(private val repository: RecipeRepository) {
    suspend operator fun invoke(recipeId: String): Recipe? {
        return repository.getRecipeDetail(recipeId)
    }
}
