package com.jun.husbandsrecipe.domain.repository

import com.jun.husbandsrecipe.domain.model.Comment
import kotlinx.coroutines.flow.Flow

interface CommentRepository {
    fun getComments(recipeId: String): Flow<List<Comment>>
    suspend fun addComment(comment: Comment)
}
