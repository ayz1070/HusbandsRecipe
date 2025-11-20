package com.jun.husbandsrecipe.domain.usecase

import com.jun.husbandsrecipe.domain.model.User
import com.jun.husbandsrecipe.domain.repository.AuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(): Result<User> {
        return repository.signIn()
    }
}
