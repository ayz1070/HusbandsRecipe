package com.jun.husbandsrecipe.domain.model

data class Recipe(
        val id: String = "",
        val title: String = "",
        val category: String = "",
        val thumbnailUrl: String = "",
        val youtubeUrl: String = "",
        val summary: String = "",
        val content: String = "",
        val ingredients: List<Ingredient> = emptyList(),
        val authorId: String = "",
        val likeCount: Int = 0,
        val commentCount: Int = 0,
        val publishedAt: Long = 0,
        val createdAt: Long = 0,
        val updatedAt: Long = 0
)

data class Ingredient(val name: String = "", val quantity: String = "")
