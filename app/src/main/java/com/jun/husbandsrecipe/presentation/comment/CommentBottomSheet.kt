package com.jun.husbandsrecipe.presentation.comment

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jun.husbandsrecipe.domain.model.Comment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentBottomSheet(
        recipeId: String,
        viewModel: CommentViewModel = hiltViewModel(),
        onDismissRequest: () -> Unit
) {
    LaunchedEffect(recipeId) { viewModel.fetchComments(recipeId) }

    val comments by viewModel.comments.collectAsState()
    var commentText by remember { mutableStateOf("") }

    ModalBottomSheet(onDismissRequest = onDismissRequest) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Text(
                    text = "댓글 ${comments.size}",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
            ) { items(comments) { comment -> CommentItem(comment) } }

            Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                        value = commentText,
                        onValueChange = { commentText = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("댓글을 입력하세요") }
                )
                IconButton(
                        onClick = {
                            if (commentText.isNotBlank()) {
                                viewModel.addComment(recipeId, commentText)
                                commentText = ""
                            }
                        }
                ) { Icon(Icons.Default.Send, contentDescription = "Send") }
            }
            Spacer(modifier = Modifier.height(32.dp)) // Keyboard space
        }
    }
}

@Composable
fun CommentItem(comment: Comment) {
    Column {
        Text(text = comment.authorName, style = MaterialTheme.typography.labelLarge)
        Text(text = comment.content, style = MaterialTheme.typography.bodyMedium)
    }
}
