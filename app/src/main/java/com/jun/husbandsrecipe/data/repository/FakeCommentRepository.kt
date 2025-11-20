package com.jun.husbandsrecipe.data.repository

import com.jun.husbandsrecipe.domain.model.Comment
import com.jun.husbandsrecipe.domain.repository.CommentRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakeCommentRepository @Inject constructor() : CommentRepository {

    private val comments =
            MutableStateFlow<List<Comment>>(
                    listOf(
                            Comment(
                                    id = "1",
                                    recipeId = "1",
                                    authorId = "user1",
                                    authorName = "요리왕",
                                    content = "정말 맛있어요!",
                                    likeCount = 5,
                                    createdAt = System.currentTimeMillis()
                            ),
                            Comment(
                                    id = "2",
                                    recipeId = "1",
                                    authorId = "user2",
                                    authorName = "초보남편",
                                    content = "따라하기 쉽네요.",
                                    likeCount = 2,
                                    createdAt = System.currentTimeMillis()
                            )
                    )
            )

    override fun getComments(recipeId: String): Flow<List<Comment>> {
        return comments.map { list -> list.filter { it.recipeId == recipeId } }
    }

    override suspend fun addComment(comment: Comment) {
        val currentList = comments.value.toMutableList()
        currentList.add(comment)
        comments.value = currentList
    }
}
