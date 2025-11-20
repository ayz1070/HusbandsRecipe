package com.jun.husbandsrecipe.data.repository

import com.jun.husbandsrecipe.domain.model.User
import com.jun.husbandsrecipe.domain.repository.AuthRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeAuthRepository @Inject constructor() : AuthRepository {

    private val _currentUser = MutableStateFlow<User?>(null)
    override val currentUser: Flow<User?> = _currentUser.asStateFlow()

    override suspend fun signIn(): Result<User> {
        // Simulate successful login
        val fakeUser =
                User(
                        id = "fake_user_id",
                        name = "김남편",
                        email = "husband@example.com",
                        profileImageUrl = "https://via.placeholder.com/150"
                )
        _currentUser.value = fakeUser
        return Result.success(fakeUser)
    }

    override suspend fun signOut() {
        _currentUser.value = null
    }
}
