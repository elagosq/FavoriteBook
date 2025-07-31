package com.cursokotlin.favoritebook.model

data class SingleBookModel(
   val volumeInfo: VolumenInfo,
   val saleInfo: SaleInfo
)

data class VolumenInfo(
   val title: String,
   val subtitle: String,
   val publisher: String,
   val publishedDate: String,
   val description: String,
   val pageCount: Int,
   val imageLinks: ImageLink?,
   val previewLink: String?,
   val canonicalVolumeLink:String?
)
