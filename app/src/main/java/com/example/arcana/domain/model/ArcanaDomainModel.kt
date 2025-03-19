package com.example.arcana.domain.model

data class ArcanaDomainModel(
    val id: Int,
    val name: String,
    val description: String? = null,
    val sections: List<Section>? = null
) {
    data class Section(
        val id: Int,
        val title: String,
        val headers: List<Header>? = null
    ) {
        data class Header(
            val id: Int,
            val title: String,
            val subheaders: List<Subheader>? = null
        ) {
            data class Subheader(
                val id: Int,
                val title: String,
                val content: String,
                val code: String
            )
        }
    }
}

// Sealed class for handling state (loading, success, error)
sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val exception: Throwable) : Resource<Nothing>()
}