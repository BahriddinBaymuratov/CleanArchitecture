package com.example.cleanarchitecture.presentation.main.add_update

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanarchitecture.domain.use_case.base.AllUseCase
import com.example.cleanarchitecture.util.ResponseL
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddUpdateViewModel @Inject constructor(
    private val useCase: AllUseCase
): ViewModel(){
    private val _state: MutableStateFlow<AddUpdateState> = MutableStateFlow(AddUpdateState.Idle)
    val state:StateFlow<AddUpdateState> get() = _state

    fun onEvent(event: AddUpdateEvent){
        when(event){
            is AddUpdateEvent.OnCreateProduct -> {
                viewModelScope.launch {
                    useCase.createProductUseCase(event.product).collect(){response ->
                        when(response){
                            is ResponseL.Loading -> {
                                _state.update {
                                    AddUpdateState.Loading
                                }
                            }
                            is ResponseL.Error -> _state.update {
                                AddUpdateState.Error(response.message)
                            }
                            is ResponseL.Success -> _state.update {
                                AddUpdateState.SuccessCreate
                            }
                        }
                    }
                }
            }
            is AddUpdateEvent.OnUpdateProduct -> {
                viewModelScope.launch {
                    _state.update {
                        AddUpdateState.Loading
                    }
                    delay(1000L)
                    when(val response =
                        useCase.updateProductUseCase.invoke(event.oldProduct, event.newProduct)){

                        is ResponseL.Loading -> Unit
                        is ResponseL.Error -> _state.update {
                            AddUpdateState.Error(response.message)
                        }
                        is ResponseL.Success -> _state.update {
                            AddUpdateState.SuccessUpdate
                        }
                    }
                }
            }
        }
    }
}