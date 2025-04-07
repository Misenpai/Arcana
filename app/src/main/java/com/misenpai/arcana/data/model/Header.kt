package com.misenpai.arcana.data.model

data class Header(
    val id: Int,
    val title: String,
    val subheaders: List<Subheader>
)