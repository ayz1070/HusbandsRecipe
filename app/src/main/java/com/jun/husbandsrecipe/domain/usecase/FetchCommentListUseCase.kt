package com.jun.husbandsrecipe.domain.usecase

import com.jun.husbandsrecipe.domain.model.Comment
import com.jun.husbandsrecipe.domain.repository.CommentRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class FetchCommentListUseCase @Inject constructor(private val repository: CommentRepository) {
    operator fun invoke(recipeId: String): Flow<List<Comment>> {
        return repository.getComments(recipeId)
    }
}
