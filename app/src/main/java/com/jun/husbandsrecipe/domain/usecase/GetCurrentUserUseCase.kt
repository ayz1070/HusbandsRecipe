package com.jun.husbandsrecipe.domain.usecase

import com.jun.husbandsrecipe.domain.model.User
import com.jun.husbandsrecipe.domain.repository.AuthRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetCurrentUserUseCase @Inject constructor(private val repository: AuthRepository) {
    operator fun invoke(): Flow<User?> {
        return repository.currentUser
    }
}
