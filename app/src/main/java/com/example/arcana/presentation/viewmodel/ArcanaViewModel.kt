package com.example.arcana.presentation.viewmodel

import android.util.Log
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

    private val _headersState = MutableStateFlow<List<HeaderDomainModel>>(emptyList())
    private val _isDarkTheme = MutableStateFlow(false) // State for theme
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()

    fun setErrorState(exception: Exception) {
        _detailState.value = DetailState.Error(exception)
    }

    init {
        fetchLanguages()
    }

    fun fetchLanguages() {
        viewModelScope.launch {
            getArcanaUseCase.getLanguages().collect { resource ->
                _languagesState.value = resource
                if (resource is Resource.Success) {
                    Log.d("ArcanaViewModel", "Fetched ${resource.data.size} languages")
                }
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
                    is Resource.Success -> {
                        _headersState.value = resource.data
                        Log.d("ArcanaViewModel", "Fetched headers: ${resource.data.size}, first header subheaders: ${resource.data.firstOrNull()?.subheaders?.size}")
                        _detailState.value = DetailState.SectionDetail(resource.data)
                    }
                    is Resource.Error -> _detailState.value = DetailState.Error(resource.exception)
                }
            }
        }
    }

    fun selectHeader(headerId: Int) {
        val header = _headersState.value.find { it.id == headerId }
        if (header != null) {
            Log.d("ArcanaViewModel", "Selected header: ${header.title}, subheaders: ${header.subheaders.size}")
            _detailState.value = DetailState.HeaderDetail(header.subheaders)
        } else {
            Log.e("ArcanaViewModel", "Header not found for id: $headerId")
            _detailState.value = DetailState.Error(Exception("Header not found"))
        }
    }

    fun toggleTheme() {
        _isDarkTheme.value = !_isDarkTheme.value
        Log.d("ArcanaViewModel", "Theme toggled to ${_isDarkTheme.value}")
    }
}