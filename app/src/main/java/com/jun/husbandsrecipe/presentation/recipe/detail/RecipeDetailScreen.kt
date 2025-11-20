package com.jun.husbandsrecipe.presentation.recipe.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.jun.husbandsrecipe.domain.model.Recipe
import com.jun.husbandsrecipe.presentation.comment.CommentBottomSheet
import com.jun.husbandsrecipe.presentation.common.UiState
import com.jun.husbandsrecipe.presentation.recipe.RecipeViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun RecipeDetailScreen(
        recipeId: String,
        viewModel: RecipeViewModel = hiltViewModel(),
        onBackClick: () -> Unit,
        onNavigateHome: () -> Unit = onBackClick
) {
    LaunchedEffect(recipeId) { viewModel.fetchRecipeDetail(recipeId) }

    val recipeDetailState by viewModel.recipeDetailState.collectAsState()
    val isLiked by viewModel.isLiked.collectAsState()
    var showComments by remember { mutableStateOf(false) }

    if (showComments) {
        CommentBottomSheet(
                recipeId = recipeId,
                onDismissRequest = { showComments = false }
        )
    }

    Scaffold { paddingValues ->
        when (val state = recipeDetailState) {
            is UiState.Loading -> {
                Box(
                        modifier = Modifier.fillMaxSize().padding(paddingValues),
                        contentAlignment = Alignment.Center
                ) { CircularProgressIndicator() }
            }
            is UiState.Success -> {
                val recipe = state.data
                RecipeDetailContent(
                        recipe = recipe,
                        isLiked = isLiked,
                        onBackClick = onBackClick,
                        onHomeClick = onNavigateHome,
                        onToggleLike = { viewModel.toggleLike() },
                        onCommentClick = { showComments = true },
                        modifier =
                                Modifier.fillMaxSize()
                                        .padding(paddingValues)
                                        .verticalScroll(rememberScrollState())
                )
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

@Composable
private fun RecipeDetailContent(
        recipe: Recipe,
        isLiked: Boolean,
        onBackClick: () -> Unit,
        onHomeClick: () -> Unit,
        onToggleLike: () -> Unit,
        onCommentClick: () -> Unit,
        modifier: Modifier = Modifier
) {
    val uriHandler = LocalUriHandler.current
    val publishedDate =
            remember(recipe.publishedAt) {
                if (recipe.publishedAt > 0) {
                    SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
                            .format(Date(recipe.publishedAt))
                } else null
            }

    Column(modifier = modifier) {
        RecipeHero(
                recipe = recipe,
                onBackClick = onBackClick,
                onHomeClick = onHomeClick
        )

        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 24.dp)) {
            publishedDate?.let {
                Text(
                        text = it,
                        style = MaterialTheme.typography.labelLarge.copy(color = Color(0xFFB0B0B0))
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
            Text(
                    text = recipe.summary,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                    text = recipe.content,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                    text = "재료",
                    style =
                            MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
            )
            Spacer(modifier = Modifier.height(12.dp))
            recipe.ingredients.forEach { ingredient ->
                Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = ingredient.name, style = MaterialTheme.typography.bodyMedium)
                    Text(text = ingredient.quantity, style = MaterialTheme.typography.bodyMedium)
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                    onClick = {
                        if (recipe.youtubeUrl.isNotBlank()) uriHandler.openUri(recipe.youtubeUrl)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors =
                            ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                        text = "신청하기",
                        style = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.onPrimary)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            InteractionBar(
                    likeCount = recipe.likeCount,
                    commentCount = recipe.commentCount,
                    isLiked = isLiked,
                    onLikeClick = onToggleLike,
                    onCommentClick = onCommentClick
            )
        }
    }
}

@Composable
private fun RecipeHero(
        recipe: Recipe,
        onBackClick: () -> Unit,
        onHomeClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth().height(420.dp)) {
        AsyncImage(
                model = recipe.thumbnailUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
        )
        Box(
                modifier =
                        Modifier.matchParentSize()
                                .background(
                                        Brush.verticalGradient(
                                                colors =
                                                        listOf(
                                                                Color.Black.copy(alpha = 0.6f),
                                                                Color.Transparent,
                                                                Color.Black.copy(alpha = 0.8f)
                                                        ),
                                                startY = 0f,
                                                endY = Float.POSITIVE_INFINITY
                                        )
                                )
        )
        Row(
                modifier =
                        Modifier.fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                                .statusBarsPadding(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
        ) {
            CircularIconButton(icon = Icons.AutoMirrored.Filled.ArrowBack, onClick = onBackClick)
            CircularIconButton(icon = Icons.Default.Home, onClick = onHomeClick)
        }
        Column(
                modifier =
                        Modifier.align(Alignment.BottomStart)
                                .fillMaxWidth()
                                .padding(24.dp)
        ) {
            AssistChip(
                    onClick = {},
                    label = { Text(recipe.category.ifBlank { "레시피" }) },
                    colors =
                            AssistChipDefaults.assistChipColors(
                                    containerColor = Color.White.copy(alpha = 0.9f),
                                    labelColor = Color.Black
                            )
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                    text = recipe.title,
                    style =
                            MaterialTheme.typography.headlineMedium.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                            )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                    text = recipe.summary,
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                    maxLines = 2
            )
        }
    }
}

@Composable
private fun CircularIconButton(icon: ImageVector, onClick: () -> Unit) {
    IconButton(
            onClick = onClick,
            modifier =
                    Modifier.size(44.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.7f))
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = Color.Black)
    }
}

@Composable
private fun InteractionBar(
        likeCount: Int,
        commentCount: Int,
        isLiked: Boolean,
        onLikeClick: () -> Unit,
        onCommentClick: () -> Unit
) {
    Row(
            modifier =
                    Modifier.fillMaxWidth()
                            .wrapContentHeight()
                            .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
            CountButton(
                    icon = if (isLiked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    label = likeCount.toString(),
                    tint = if (isLiked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                    onClick = onLikeClick
            )
            CountButton(
                    icon = Icons.Default.Email,
                    label = commentCount.toString(),
                    tint = MaterialTheme.colorScheme.onSurface,
                    onClick = onCommentClick
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            IconButton(onClick = { /* TODO share */ }) {
                Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share",
                        tint = MaterialTheme.colorScheme.onSurface
                )
            }
            IconButton(onClick = { /* TODO bookmark */ }) {
                Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Bookmark",
                        tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
private fun CountButton(
        icon: ImageVector,
        label: String,
        tint: Color,
        onClick: () -> Unit
) {
    Row(
            modifier =
                    Modifier.clip(RoundedCornerShape(50))
                            .clickable(onClick = onClick)
                            .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = tint)
        Text(text = label, style = MaterialTheme.typography.bodyMedium, color = tint)
    }
}
