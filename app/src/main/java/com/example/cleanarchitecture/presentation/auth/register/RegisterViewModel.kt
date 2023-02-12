package com.example.cleanarchitecture.presentation.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanarchitecture.domain.model.User
import com.example.cleanarchitecture.domain.use_case.base.AllUseCase
import com.example.cleanarchitecture.util.ResponseL
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val useCase: AllUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<RegisterState> = MutableStateFlow(RegisterState.Idle)
    val state: StateFlow<RegisterState> get() = _state

    fun register(user: User) {
        viewModelScope.launch {
            useCase.registerUseCase(user).collect { response ->
                when (response) {
                    is ResponseL.Loading -> {
                        _state.update {
                            RegisterState.Loading
                        }
                        delay(1000L)
                    }
                    is ResponseL.Error -> {
                        _state.update {
                            RegisterState.Error(response.message)
                        }
                    }
                    is ResponseL.Success -> {
                        _state.update {
                            RegisterState.Success
                        }
                    }
                }
            }
        }
    }
}