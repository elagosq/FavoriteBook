package com.cursokotlin.favoritebook.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cursokotlin.favoritebook.model.BookItem
import com.cursokotlin.favoritebook.repository.BooksRepository

class BooksDataSource(private val repo: BooksRepository,private val query:String) : PagingSource<Int, BookItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BookItem> {
        return try {
            val position = params.key ?: 0
            val limit = 40
            val response = repo.getBooksPaging(query, position, limit)
            val booksApiResponse = response?.body()
            val listOfBooks = booksApiResponse?.items ?: emptyList()
            val nextKey = if (listOfBooks.isEmpty()) null else position + limit
            LoadResult.Page(
                data = listOfBooks,
                prevKey = if (position == 0) null else position - limit,//null porque no hau una paginación a uno
                nextKey = nextKey//Siguiente página
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    // Método obligatorio para que Paging 3 sepa cómo refrescar los datos.
    override fun getRefreshKey(state: PagingState<Int, BookItem>): Int? {
        // Intenta encontrar la clave de la página más cercana a la posición actual del scroll.
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(state.config.pageSize)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(state.config.pageSize)
        }
    }
}
