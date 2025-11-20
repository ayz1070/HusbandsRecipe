package com.jun.husbandsrecipe.domain.usecase

import com.jun.husbandsrecipe.domain.model.Recipe
import com.jun.husbandsrecipe.domain.repository.RecipeRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class FetchRecipeListUseCase @Inject constructor(private val repository: RecipeRepository) {
    operator fun invoke(): Flow<List<Recipe>> {
        return repository.getRecipeList()
    }
}
