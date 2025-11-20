package com.jun.husbandsrecipe.domain.repository

interface LikeRepository {
    suspend fun toggleLike(
            userId: String,
            targetType: String,
            targetId: String
    ): Boolean // Returns true if liked, false if unliked
    suspend fun isLiked(userId: String, targetType: String, targetId: String): Boolean
    suspend fun getLikeCount(targetType: String, targetId: String): Int
}
