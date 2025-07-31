package com.cursokotlin.favoritebook.model

data class BooksApiResponse(
    val items: List<BookItem>?
)

data class BookItem(
    val id : String,
    val volumeInfo: VolumeInfo,
    val saleInfo: SaleInfo
)

data class VolumeInfo(
  val title: String,
  val subtitle:String?,
  val authors:List<String>?,
  val publisher: String?,
  val publishedDate: String?,
  val description: String?,
  val pageCount: Int?,
  val categories: List<String>?,
  val imageLinks: ImageLink?,
  val previewLink: String?,
)

data class ImageLink (
   val thumbnail: String?
)

data class SaleInfo(
    val buyLink: String?
)