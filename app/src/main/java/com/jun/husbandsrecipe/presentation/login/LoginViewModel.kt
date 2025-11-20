package com.jun.husbandsrecipe.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jun.husbandsrecipe.domain.model.User
import com.jun.husbandsrecipe.domain.usecase.GetCurrentUserUseCase
import com.jun.husbandsrecipe.domain.usecase.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel
@Inject
constructor(
        private val signInUseCase: SignInUseCase,
        private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    init {
        viewModelScope.launch { getCurrentUserUseCase().collect { _currentUser.value = it } }
    }

    fun signIn() {
        viewModelScope.launch {
            signInUseCase()
                    .onSuccess {
                        // Handle success
                    }
                    .onFailure {
                        // Handle failure
                    }
        }
    }
}
