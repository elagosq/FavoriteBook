package com.cursokotlin.favoritebook.state

data class BookState(
    val title: String = "",
    val subtitle: String = "",
    val publisher: String = "",
    val publishedDate: String = "",
    val description: String = "",
    val pageCount: Int = 0,
    val imageLinks: String = "",
    val previewLink: String = "",
    val canonicalVolumeLink : String = ""
)
