package com.jun.husbandsrecipe.presentation.recipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jun.husbandsrecipe.domain.model.Recipe
import com.jun.husbandsrecipe.domain.usecase.FetchRecipeDetailUseCase
import com.jun.husbandsrecipe.domain.usecase.FetchRecipeListUseCase
import com.jun.husbandsrecipe.domain.usecase.ToggleLikeUseCase
import com.jun.husbandsrecipe.presentation.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

@HiltViewModel
class RecipeViewModel
@Inject
constructor(
        private val fetchRecipeListUseCase: FetchRecipeListUseCase,
        private val fetchRecipeDetailUseCase: FetchRecipeDetailUseCase,
        private val toggleLikeUseCase: ToggleLikeUseCase
) : ViewModel() {

    private val _recipeListState = MutableStateFlow<UiState<List<Recipe>>>(UiState.Loading)
    val recipeListState: StateFlow<UiState<List<Recipe>>> = _recipeListState.asStateFlow()

    private val _recipeDetailState = MutableStateFlow<UiState<Recipe>>(UiState.Loading)
    val recipeDetailState: StateFlow<UiState<Recipe>> = _recipeDetailState.asStateFlow()

    private val _isLiked = MutableStateFlow(false)
    val isLiked: StateFlow<Boolean> = _isLiked.asStateFlow()

    init {
        fetchRecipes()
    }

    fun fetchRecipes() {
        viewModelScope.launch {
            _recipeListState.value = UiState.Loading
            delay(500) // Simulate network delay
            fetchRecipeListUseCase()
                    .catch { _ -> _recipeListState.value = UiState.Error("알 수 없는 오류가 발생했습니다") }
                    .collect { recipes -> _recipeListState.value = UiState.Success(recipes) }
        }
    }

    fun fetchRecipeDetail(recipeId: String) {
        viewModelScope.launch {
            _recipeDetailState.value = UiState.Loading
            delay(300) // Simulate network delay
            try {
                val recipe = fetchRecipeDetailUseCase(recipeId)
                if (recipe != null) {
                    _recipeDetailState.value = UiState.Success(recipe)
                    _isLiked.value = false // TODO: Check real like status
                } else {
                    _recipeDetailState.value = UiState.Error("레시피를 찾을 수 없습니다")
                }
            } catch (e: Exception) {
                _recipeDetailState.value = UiState.Error(e.message ?: "알 수 없는 오류가 발생했습니다")
            }
        }
    }

    fun toggleLike() {
        val recipe = (_recipeDetailState.value as? UiState.Success)?.data ?: return
        viewModelScope.launch {
            val newStatus = toggleLikeUseCase("current_user", "recipe", recipe.id)
            _isLiked.value = newStatus
        }
    }
}
