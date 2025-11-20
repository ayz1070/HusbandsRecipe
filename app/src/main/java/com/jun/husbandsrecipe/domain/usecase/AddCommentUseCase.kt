package com.jun.husbandsrecipe.domain.usecase

import com.jun.husbandsrecipe.domain.model.Comment
import com.jun.husbandsrecipe.domain.repository.CommentRepository
import javax.inject.Inject

class AddCommentUseCase @Inject constructor(private val repository: CommentRepository) {
    suspend operator fun invoke(comment: Comment) {
        repository.addComment(comment)
    }
}
