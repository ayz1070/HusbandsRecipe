package com.jun.husbandsrecipe.domain.repository

import com.jun.husbandsrecipe.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: Flow<User?>
    suspend fun signIn(): Result<User>
    suspend fun signOut()
}
