package com.example.arcana.domain.model

data class SectionDomainModel(
    val id: Int,
    val title: String,
    val headers: List<HeaderDomainModel>? = null
)