package com.jun.husbandsrecipe.domain.model

data class Like(
        val id: String = "",
        val userId: String = "",
        val targetType: String = "", // "recipe" or "comment"
        val targetId: String = "",
        val createdAt: Long = 0
)
