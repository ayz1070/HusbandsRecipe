package com.jun.husbandsrecipe.presentation.recipe.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.jun.husbandsrecipe.domain.model.Recipe
import com.jun.husbandsrecipe.presentation.recipe.RecipeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeListScreen(
        viewModel: RecipeViewModel = hiltViewModel(),
        onRecipeClick: (String) -> Unit
) {
    val recipeListState by viewModel.recipeListState.collectAsState()

    Scaffold(topBar = { TopAppBar(title = { Text("남편의 레시피") }) }) { paddingValues ->
        when (val state = recipeListState) {
            is com.jun.husbandsrecipe.presentation.common.UiState.Loading -> {
                Box(
                        modifier = Modifier.fillMaxSize().padding(paddingValues),
                        contentAlignment = Alignment.Center
                ) { CircularProgressIndicator() }
            }
            is com.jun.husbandsrecipe.presentation.common.UiState.Success -> {
                val recipes = state.data
                if (recipes.isEmpty()) {
                    Box(
                            modifier = Modifier.fillMaxSize().padding(paddingValues),
                            contentAlignment = Alignment.Center
                    ) { Text("레시피가 없습니다", style = MaterialTheme.typography.bodyLarge) }
                } else {
                    LazyColumn(
                            modifier = Modifier.fillMaxSize().padding(paddingValues),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        item { com.jun.husbandsrecipe.presentation.common.AdBanner() }
                        items(recipes) { recipe ->
                            RecipeCard(recipe = recipe, onClick = { onRecipeClick(recipe.id) })
                        }
                    }
                }
            }
            is com.jun.husbandsrecipe.presentation.common.UiState.Error -> {
                Box(
                        modifier = Modifier.fillMaxSize().padding(paddingValues),
                        contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                                text = state.message,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.fetchRecipes() }) { Text("다시 시도") }
                    }
                }
            }
        }
    }
}

@Composable
fun RecipeCard(recipe: Recipe, onClick: () -> Unit) {
    ElevatedCard(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Column {
            AsyncImage(
                    model = recipe.thumbnailUrl,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().height(200.dp),
                    contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = recipe.title, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = recipe.summary, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
