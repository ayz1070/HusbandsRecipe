package com.jun.husbandsrecipe.presentation.recipe.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.jun.husbandsrecipe.presentation.common.UiState
import com.jun.husbandsrecipe.presentation.recipe.RecipeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(
        recipeId: String,
        viewModel: RecipeViewModel = hiltViewModel(),
        onBackClick: () -> Unit
) {
    LaunchedEffect(recipeId) { viewModel.fetchRecipeDetail(recipeId) }

    val recipeDetailState by viewModel.recipeDetailState.collectAsState()
    var showComments by remember { mutableStateOf(false) }

    if (showComments) {
        com.jun.husbandsrecipe.presentation.comment.CommentBottomSheet(
                recipeId = recipeId,
                onDismissRequest = { showComments = false }
        )
    }

    Scaffold(
            topBar = {
                TopAppBar(
                        title = {
                            Text((recipeDetailState as? UiState.Success)?.data?.title ?: "")
                        },
                        navigationIcon = {
                            IconButton(onClick = onBackClick) {
                                Icon(
                                        Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Back"
                                )
                            }
                        },
                        actions = {
                            val isLiked by viewModel.isLiked.collectAsState()
                            IconButton(onClick = { viewModel.toggleLike() }) {
                                Icon(
                                        imageVector =
                                                if (isLiked) Icons.Default.Favorite
                                                else Icons.Default.FavoriteBorder,
                                        contentDescription = "Like",
                                        tint =
                                                if (isLiked) MaterialTheme.colorScheme.primary
                                                else MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                )
            },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                        onClick = { showComments = true },
                        icon = { Icon(Icons.Default.Call, contentDescription = "Comment") },
                        text = { Text("댓글") }
                )
            }
    ) { paddingValues ->
        when (val state = recipeDetailState) {
            is UiState.Loading -> {
                Box(
                        modifier = Modifier.fillMaxSize().padding(paddingValues),
                        contentAlignment = Alignment.Center
                ) { CircularProgressIndicator() }
            }
            is UiState.Success -> {
                val recipe = state.data
                Column(
                        modifier =
                                Modifier.fillMaxSize()
                                        .padding(paddingValues)
                                        .verticalScroll(rememberScrollState())
                ) {
                    AsyncImage(
                            model = recipe.thumbnailUrl,
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth().height(250.dp),
                            contentScale = ContentScale.Crop
                    )
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = recipe.title, style = MaterialTheme.typography.headlineMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = recipe.content, style = MaterialTheme.typography.bodyLarge)

                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "재료", style = MaterialTheme.typography.titleMedium)
                        recipe.ingredients.forEach { ingredient ->
                            Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = ingredient.name)
                                Text(text = ingredient.quantity)
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        com.jun.husbandsrecipe.presentation.common.AdBanner()
                    }
                }
            }
            is UiState.Error -> {
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
                        Button(onClick = { viewModel.fetchRecipeDetail(recipeId) }) {
                            Text("다시 시도")
                        }
                    }
                }
            }
        }
    }
}
