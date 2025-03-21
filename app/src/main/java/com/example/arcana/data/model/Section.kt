package com.example.arcana.data.model

data class Section(
    val id: Int,
    val title: String,
    val headers: List<Header>? = null
)