package com.jun.husbandsrecipe.data.repository

import com.jun.husbandsrecipe.domain.model.Like
import com.jun.husbandsrecipe.domain.repository.LikeRepository
import java.util.UUID
import javax.inject.Inject

class FakeLikeRepository @Inject constructor() : LikeRepository {

    private val likes = mutableListOf<Like>()

    override suspend fun toggleLike(userId: String, targetType: String, targetId: String): Boolean {
        val existingLike =
                likes.find {
                    it.userId == userId && it.targetType == targetType && it.targetId == targetId
                }

        return if (existingLike != null) {
            likes.remove(existingLike)
            false // Unliked
        } else {
            likes.add(
                    Like(
                            id = UUID.randomUUID().toString(),
                            userId = userId,
                            targetType = targetType,
                            targetId = targetId,
                            createdAt = System.currentTimeMillis()
                    )
            )
            true // Liked
        }
    }

    override suspend fun isLiked(userId: String, targetType: String, targetId: String): Boolean {
        return likes.any {
            it.userId == userId && it.targetType == targetType && it.targetId == targetId
        }
    }

    override suspend fun getLikeCount(targetType: String, targetId: String): Int {
        return likes.count { it.targetType == targetType && it.targetId == targetId }
    }
}
