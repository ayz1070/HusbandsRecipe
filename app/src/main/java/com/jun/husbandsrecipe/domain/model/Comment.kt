package com.jun.husbandsrecipe.domain.model

data class Comment(
        val id: String = "",
        val recipeId: String = "",
        val authorId: String = "",
        val authorName: String = "",
        val content: String = "",
        val likeCount: Int = 0,
        val createdAt: Long = 0,
        val updatedAt: Long = 0
)
