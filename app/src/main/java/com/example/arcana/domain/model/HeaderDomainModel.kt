package com.example.arcana.domain.model

data class HeaderDomainModel(
    val id: Int,
    val title: String,
    val subheaders: List<SubheaderDomainModel>? = null
)