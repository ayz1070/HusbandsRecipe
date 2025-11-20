package com.jun.husbandsrecipe.domain.usecase

import com.jun.husbandsrecipe.domain.repository.LikeRepository
import javax.inject.Inject

class ToggleLikeUseCase @Inject constructor(private val repository: LikeRepository) {
    suspend operator fun invoke(userId: String, targetType: String, targetId: String): Boolean {
        return repository.toggleLike(userId, targetType, targetId)
    }
}
