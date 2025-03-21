package com.example.arcana.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arcana.domain.model.*
import com.example.arcana.domain.usecase.GetArcanaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class DetailState {
    object Loading : DetailState()
    data class LanguageDetail(val sections: List<SectionDomainModel>) : DetailState()
    data class SectionDetail(val headers: List<HeaderDomainModel>) : DetailState()
    data class HeaderDetail(val subheaders: List<SubheaderDomainModel>) : DetailState()
    data class Error(val exception: Exception) : DetailState()
}

@HiltViewModel
class ArcanaViewModel @Inject constructor(
    private val getArcanaUseCase: GetArcanaUseCase
) : ViewModel() {

    private val _languagesState = MutableStateFlow<Resource<List<LanguageDomainModel>>>(Resource.Loading)
    val languagesState: StateFlow<Resource<List<LanguageDomainModel>>> = _languagesState.asStateFlow()

    private val _detailState = MutableStateFlow<DetailState>(DetailState.Loading)
    val detailState: StateFlow<DetailState> = _detailState.asStateFlow()

    init {
        fetchLanguages() // Automatically fetch languages on initialization
    }

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
                when (resource) {
                    is Resource.Loading -> _detailState.value = DetailState.Loading
                    is Resource.Success -> _detailState.value = DetailState.LanguageDetail(resource.data)
                    is Resource.Error -> _detailState.value = DetailState.Error(resource.exception)
                }
            }
        }
    }

    fun fetchHeaders(sectionId: Int) {
        viewModelScope.launch {
            getArcanaUseCase.getHeaders(sectionId).collect { resource ->
                when (resource) {
                    is Resource.Loading -> _detailState.value = DetailState.Loading
                    is Resource.Success -> _detailState.value = DetailState.SectionDetail(resource.data)
                    is Resource.Error -> _detailState.value = DetailState.Error(resource.exception)
                }
            }
        }
    }

    fun fetchSubheaders(headerId: Int) {
        viewModelScope.launch {
            getArcanaUseCase.getSubheaders(headerId).collect { resource ->
                when (resource) {
                    is Resource.Loading -> _detailState.value = DetailState.Loading
                    is Resource.Success -> _detailState.value = DetailState.HeaderDetail(resource.data)
                    is Resource.Error -> _detailState.value = DetailState.Error(resource.exception)
                }
            }
        }
    }
}