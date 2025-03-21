package com.example.arcana.domain.model

data class LanguageDomainModel(
    val id: Int,
    val name: String,
    val description: String,
    val sections: List<SectionDomainModel>? = null
)