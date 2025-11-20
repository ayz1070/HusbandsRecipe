package com.jun.husbandsrecipe.presentation.comment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jun.husbandsrecipe.domain.model.Comment
import com.jun.husbandsrecipe.domain.usecase.AddCommentUseCase
import com.jun.husbandsrecipe.domain.usecase.FetchCommentListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class CommentViewModel
@Inject
constructor(
        private val fetchCommentListUseCase: FetchCommentListUseCase,
        private val addCommentUseCase: AddCommentUseCase
) : ViewModel() {

    private val _comments = MutableStateFlow<List<Comment>>(emptyList())
    val comments: StateFlow<List<Comment>> = _comments.asStateFlow()

    fun fetchComments(recipeId: String) {
        viewModelScope.launch { fetchCommentListUseCase(recipeId).collect { _comments.value = it } }
    }

    fun addComment(recipeId: String, content: String) {
        viewModelScope.launch {
            val newComment =
                    Comment(
                            id = UUID.randomUUID().toString(),
                            recipeId = recipeId,
                            authorId = "current_user", // TODO: Real user ID
                            authorName = "나", // TODO: Real user name
                            content = content,
                            createdAt = System.currentTimeMillis()
                    )
            addCommentUseCase(newComment)
        }
    }
}
