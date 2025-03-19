package com.example.arcana.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arcana.domain.model.ArcanaDomainModel
import com.example.arcana.domain.model.Resource
import com.example.arcana.domain.usecase.GetArcanaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArcanaViewModel @Inject constructor(
    private val getArcanaUseCase: GetArcanaUseCase
) : ViewModel() {

    private val _languagesState = MutableStateFlow<Resource<List<ArcanaDomainModel>>>(Resource.Loading)
    val languagesState: StateFlow<Resource<List<ArcanaDomainModel>>> = _languagesState.asStateFlow()

    private val _detailState = MutableStateFlow<Resource<List<ArcanaDomainModel>>>(Resource.Loading)
    val detailState: StateFlow<Resource<List<ArcanaDomainModel>>> = _detailState.asStateFlow()

    fun fetchLanguages() {
        viewModelScope.launch {
            getArcanaUseCase.getLanguages().collect { resource ->
                _languagesState.value = resource
            }
        }
    }

    fun fetchSections(languageId: Int) {
        viewModelScope.launch {
            getArcanaUseCase.getSections(languageId).collect { resource ->
                _detailState.value = resource
            }
        }
    }

    fun fetchHeaders(sectionId: Int) {
        viewModelScope.launch {
            getArcanaUseCase.getHeaders(sectionId).collect { resource ->
                _detailState.value = resource
            }
        }
    }

    fun fetchSubheaders(headerId: Int) {
        viewModelScope.launch {
            getArcanaUseCase.getSubheaders(headerId).collect { resource ->
                _detailState.value = resource
            }
        }
    }
}