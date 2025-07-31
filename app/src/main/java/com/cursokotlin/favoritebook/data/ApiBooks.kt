package com.cursokotlin.favoritebook.data

import com.cursokotlin.favoritebook.model.BooksApiResponse
import com.cursokotlin.favoritebook.model.SingleBookModel
import com.cursokotlin.favoritebook.util.Constants.Companion.ENDPOINT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiBooks {

    @GET(ENDPOINT)
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("maxResults") maxResults: Int,
        @Query("key") apiKey: String
    ): Response<BooksApiResponse>

    @GET(ENDPOINT)
    suspend fun getGamesPaging(
        @Query("q") query: String,
        @Query("startIndex") startIndex: Int,
        @Query("maxResults") maxResults: Int,
        @Query("key") apiKey: String
    ) : Response<BooksApiResponse>

    //GET https://www.googleapis.com/books/v1/volumes/zyTCAlFPjgYC?key=yourAPIKey
    @GET("$ENDPOINT/{id}")
    suspend fun getBookByID(
        @Path(value = "id") id: String,
        @Query("key") apiKey: String
    ): Response<SingleBookModel>


}