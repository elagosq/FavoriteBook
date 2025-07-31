package com.cursokotlin.favoritebook.repository

import com.cursokotlin.favoritebook.data.ApiBooks
import com.cursokotlin.favoritebook.model.BookItem
import com.cursokotlin.favoritebook.model.BooksApiResponse
import com.cursokotlin.favoritebook.model.SingleBookModel
import com.cursokotlin.favoritebook.util.Constants.Companion.API_KEY
import kotlinx.coroutines.delay
import retrofit2.Response
import javax.inject.Inject

class BooksRepository @Inject constructor(private val apiBooks: ApiBooks) {

    suspend fun searchBook(q: String): List<BookItem>? {
        val response = apiBooks.searchBooks(q, 40, API_KEY)

        if (response.isSuccessful) {
            return response.body()?.items
        }

        return null
    }

    suspend fun getBooksPaging(query:String,startIndex: Int, maxResults: Int): Response<BooksApiResponse>? {
        delay(3000L)
        return apiBooks.getGamesPaging(query,startIndex,maxResults, API_KEY)
    }

    suspend fun getBookById(id:String): SingleBookModel? {
        val response = apiBooks.getBookByID(id,API_KEY)

        if(response.isSuccessful){
            return response.body()
        }

        return null
    }
}