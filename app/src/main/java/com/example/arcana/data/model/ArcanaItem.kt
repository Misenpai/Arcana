package com.example.arcana.data.model

data class ArcanaItem(
    val id: Int,
    val name: String? = null,
    val title: String? = null,
    val description: String? = null,
    val content: String? = null,
    val code: String? = null,
    val sections: List<Section>? = null,
    val headers: List<Header>? = null,
    val subheaders: List<Subheader>? = null
) {
    data class Section(
        val id: Int,
        val title: String,
        val headers: List<Header>? = null
    )

    data class Header(
        val id: Int,
        val title: String,
        val subheaders: List<Subheader>? = null
    )

    data class Subheader(
        val id: Int,
        val title: String,
        val content: String,
        val code: String
    )
}