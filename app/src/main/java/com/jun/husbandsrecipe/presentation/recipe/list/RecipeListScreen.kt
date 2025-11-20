package com.jun.husbandsrecipe.presentation.recipe.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.jun.husbandsrecipe.domain.model.Recipe
import com.jun.husbandsrecipe.presentation.common.UiState
import com.jun.husbandsrecipe.presentation.recipe.RecipeViewModel
import com.jun.husbandsrecipe.ui.theme.HighlightBlue
import com.jun.husbandsrecipe.ui.theme.HighlightBlueVariant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeListScreen(
        viewModel: RecipeViewModel = hiltViewModel(),
        onRecipeClick: (String) -> Unit
) {
    val recipeListState by viewModel.recipeListState.collectAsState()
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }
    var selectedBottomItem by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
            topBar = {
                RecipeListTopBar(
                        selectedTab = selectedTab,
                        onTabSelected = { selectedTab = it }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                        onClick = { /* TODO: hook into navigation when grid mode ready */ },
                        containerColor = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                ) { Icon(Icons.Filled.Add, contentDescription = "Grid") }
            },
    ) { paddingValues ->
        when (val state = recipeListState) {
            is UiState.Loading -> {
                Box(
                        modifier = Modifier.fillMaxSize().padding(paddingValues),
                        contentAlignment = Alignment.Center
                ) { CircularProgressIndicator() }
            }
            is UiState.Success -> {
                val recipes = state.data
                if (recipes.isEmpty()) {
                    Box(
                            modifier = Modifier.fillMaxSize().padding(paddingValues),
                            contentAlignment = Alignment.Center
                    ) {
                        Text(
                                text = "레시피가 없습니다",
                                style = MaterialTheme.typography.bodyLarge
                        )
                    }
                } else {
                    LazyColumn(
                            modifier = Modifier.fillMaxSize().padding(paddingValues),
                            contentPadding =
                                    PaddingValues(
                                            start = 20.dp,
                                            end = 20.dp,
                                            top = 16.dp,
                                            bottom = 120.dp // space for bottom navigation
                                    ),
                            verticalArrangement = Arrangement.spacedBy(28.dp)
                    ) {
                        items(recipes, key = { it.id }) { recipe ->
                            HighlightedRecipeCard(recipe = recipe, onClick = {
                                onRecipeClick(recipe.id)
                            })
                        }
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
                        Button(onClick = { viewModel.fetchRecipes() }) { Text("다시 시도") }
                    }
                }
            }
        }
    }
}

@Composable
private fun RecipeListTopBar(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    val tabs = listOf("추천", "최신")
    Column(
            modifier =
                    Modifier.fillMaxWidth()
                            .padding(top = 16.dp)
                            .padding(horizontal = 20.dp)
    ) {
        Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* TODO */ }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            }
            IconButton(onClick = { /* TODO */ }) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = "Filter")
            }
        }

        TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onSurface,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                            color = MaterialTheme.colorScheme.onSurface
                    )
                }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                        selected = selectedTab == index,
                        onClick = { onTabSelected(index) },
                        selectedContentColor = MaterialTheme.colorScheme.onSurface,
                        unselectedContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                        text = { Text(text = title, style = MaterialTheme.typography.titleMedium) }
                )
            }
        }
    }
}

@Composable
private fun HighlightedRecipeCard(recipe: Recipe, onClick: () -> Unit) {
    ElevatedCard(
            shape = RoundedCornerShape(28.dp),
//            border = BorderStroke(3.dp, HighlightBlue),
            colors = CardDefaults.elevatedCardColors(containerColor = HighlightBlueVariant),
            modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.clickable { onClick() }) {
            Box {
                AsyncImage(
                        model = recipe.thumbnailUrl,
                        contentDescription = null,
                        modifier =
                                Modifier.fillMaxWidth()
                                        .height(240.dp)
                                        .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
                        contentScale = ContentScale.Crop
                )
                AssistChip(
                        onClick = {},
                        label = { Text("EVENT") },
                        modifier = Modifier.padding(16.dp),
                        colors =
                                AssistChipDefaults.assistChipColors(
                                        containerColor = Color.White.copy(alpha = 0.9f),
                                        labelColor = HighlightBlue
                                )
                )
                IconButton(
                        onClick = { /* TODO bookmark */ },
                        modifier =
                                Modifier.align(Alignment.TopEnd)
                                        .padding(12.dp)
                                        .background(
                                                color = Color.White.copy(alpha = 0.9f),
                                                shape = CircleShape
                                        )
                ) {
                    Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Bookmark",
                            tint = HighlightBlue
                    )
                }
            }
            Column(modifier = Modifier.fillMaxWidth().padding(20.dp)) {
                Text(
                        text = recipe.title,
                        style =
                                MaterialTheme.typography.headlineSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                        text = recipe.summary,
                        style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                        maxLines = 2
                )
                Spacer(modifier = Modifier.height(16.dp))


                Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                            text = "이벤트 자세히 보기",
                            style = MaterialTheme.typography.bodyMedium.copy(color = Color.White)
                    )
                    Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = Color.White
                    )
                }
            }
        }
    }
}

