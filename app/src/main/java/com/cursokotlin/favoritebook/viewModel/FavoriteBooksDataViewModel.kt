package com.cursokotlin.favoritebook.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.cursokotlin.favoritebook.data.FavoriteBooksDataStore
import com.cursokotlin.favoritebook.model.BookItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoriteBooksDataViewModel (application: Application): AndroidViewModel(application){
    private val favoriteBookDataStore = FavoriteBooksDataStore(application)

    private val _favoriteBookList = MutableStateFlow<List<BookItem>>(emptyList())
    val favoriteBookList = _favoriteBookList.asStateFlow()

    init {
        loadFavoriteBookList()
    }

    //Cargar la lista de Item de favoritos desde DataStore
    private fun loadFavoriteBookList() {
       viewModelScope.launch(Dispatchers.IO) {
           favoriteBookDataStore.favoriteBookListFlow.collect { list ->
               _favoriteBookList.value = list
           }
       }
    }

    //Agregar un Item a favoritos
    fun addFavoriteBook(favorite: BookItem){
        viewModelScope.launch {
            favoriteBookDataStore.addFavoriteBook(favorite)
        }
    }

    //Eliminar un item de favoritos
    fun deleteFavoriteBook(favorite: BookItem){
        viewModelScope.launch {
            favoriteBookDataStore.removeFavoriteItem(favorite)
        }
    }
}