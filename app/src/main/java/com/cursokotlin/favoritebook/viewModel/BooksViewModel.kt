package com.cursokotlin.favoritebook.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.cursokotlin.favoritebook.data.BooksDataSource
import com.cursokotlin.favoritebook.model.BookItem
import com.cursokotlin.favoritebook.repository.BooksRepository
import com.cursokotlin.favoritebook.state.BookState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(private val repo: BooksRepository) : ViewModel() {

    private val _books =
        MutableStateFlow<List<BookItem>>(emptyList()) //Por una variable flow obtengo la lista de libros
    val books = _books.asStateFlow()

    var state by mutableStateOf(BookState())
        private set

    var searchBook by mutableStateOf("")
        private set

    fun searchBookItem(value:String){
        searchBook = value
    }

    //Implementación paginación
    val booksPage = Pager(PagingConfig(pageSize = 40)){
        BooksDataSource(repo,searchBook)
    }.flow.cachedIn(viewModelScope) //cachedIn guarda en cache los resultados que se estan obteniendo

    fun fetchBooks(search:String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val result = repo.searchBook(search)
                _books.value = result
                    ?: emptyList() //Valor opcional ?:(elvis) se asigna un listado vacio si no viene nada en result
            }
        }
    }

    fun getBookById(id: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val result = repo.getBookById(id)
                print(result)
                state = state.copy(
                    title = result?.volumeInfo?.title ?: "",
                    subtitle = result?.volumeInfo?.subtitle ?: "",
                    publisher = result?.volumeInfo?.publisher ?: "",
                    publishedDate = result?.volumeInfo?.publishedDate ?: "",
                    description = result?.volumeInfo?.description ?: "",
                    pageCount = result?.volumeInfo?.pageCount ?: 0,
                    imageLinks = (result?.volumeInfo?.imageLinks?.thumbnail ?: "").toString(),
                    previewLink = result?.volumeInfo?.previewLink ?: "",
                    canonicalVolumeLink = result?.volumeInfo?.canonicalVolumeLink ?: ""
                )
            }
        }
    }

    fun cleanSearchBook(){
        searchBook = ""
    }
}