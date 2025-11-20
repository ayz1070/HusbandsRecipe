package com.jun.husbandsrecipe.data.repository

import com.jun.husbandsrecipe.domain.model.Ingredient
import com.jun.husbandsrecipe.domain.model.Recipe
import com.jun.husbandsrecipe.domain.repository.RecipeRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRecipeRepository @Inject constructor() : RecipeRepository {

    private val dummyRecipes =
            listOf(
                    Recipe(
                            id = "1",
                            title = "김치찌개",
                            category = "한식",
                            thumbnailUrl = "https://via.placeholder.com/300",
                            summary = "한국인의 소울푸드 김치찌개입니다.",
                            content =
                                    "김치와 돼지고기를 넣고 푹 끓이면 완성됩니다. \n\n1. 김치를 볶는다.\n2. 물을 넣는다.\n3. 고기를 넣는다.",
                            ingredients =
                                    listOf(
                                            Ingredient("김치", "1포기"),
                                            Ingredient("돼지고기", "300g"),
                                            Ingredient("두부", "1모")
                                    ),
                            likeCount = 10,
                            commentCount = 5
                    ),
                    Recipe(
                            id = "2",
                            title = "계란말이",
                            category = "반찬",
                            thumbnailUrl = "https://via.placeholder.com/300",
                            summary = "간단하고 맛있는 계란말이",
                            content = "계란을 풀어서 말아주세요.",
                            ingredients =
                                    listOf(
                                            Ingredient("계란", "5개"),
                                            Ingredient("소금", "약간"),
                                            Ingredient("파", "조금")
                                    ),
                            likeCount = 25,
                            commentCount = 3
                    ),
                    Recipe(
                            id = "3",
                            title = "라면",
                            category = "분식",
                            thumbnailUrl = "https://via.placeholder.com/300",
                            summary = "누구나 끓일 수 있는 라면",
                            content = "물 550ml를 끓이고 면과 스프를 넣으세요.",
                            ingredients = listOf(Ingredient("라면", "1봉지"), Ingredient("계란", "1개")),
                            likeCount = 100,
                            commentCount = 50
                    )
            )

    override fun getRecipeList(): Flow<List<Recipe>> = flow { emit(dummyRecipes) }

    override suspend fun getRecipeDetail(recipeId: String): Recipe? {
        return dummyRecipes.find { it.id == recipeId }
    }
}
