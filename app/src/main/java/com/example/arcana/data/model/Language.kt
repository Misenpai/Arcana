package com.example.arcana.data.model

data class Language(
    val id: Int,
    val name: String,
    val description: String,
    val sections: List<Section>? = null
)