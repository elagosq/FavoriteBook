package com.cursokotlin.favoritebook.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.cursokotlin.favoritebook.model.BookItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

//Inicialización de DataStore
val Context.dataStore  by preferencesDataStore(name = "item_favoritos")

class FavoriteBooksDataStore(private val context: Context){

   private val favoriteBookListKey = stringPreferencesKey("favorite_book_list")
   private val gson = Gson()

    //Obtener el flujo de favoritos
    val favoriteBookListFlow: Flow<List<BookItem>> = context.dataStore.data
        .map { preferences ->
            val jsonString = preferences[favoriteBookListKey] ?: ""
            if (jsonString.isNotEmpty()) {
                //Deserializa la cadena Json a una lista de objetos item
                val listType = object : TypeToken<List<BookItem>>() {}.type
                gson.fromJson(jsonString, listType)
            } else {
                emptyList()
            }
        }

    //Agregar un item a favoritos
    suspend fun addFavoriteBook(favorite: BookItem){
        context.dataStore.edit { preferences ->
            val currentFavorites = preferences[favoriteBookListKey]?.let { jsonString ->
                val listType = object : TypeToken<List<BookItem>>() {}.type
                gson.fromJson<List<BookItem>>(jsonString, listType)
            } ?: emptyList()

            val updatedFavorites = currentFavorites + favorite
            preferences[favoriteBookListKey] = gson.toJson(updatedFavorites)
        }
    }

    // Eliminar un Item de favoritos
    suspend fun removeFavoriteItem(favorite: BookItem) {
        context.dataStore.edit { preferences ->
            val currentFavorites = preferences[favoriteBookListKey]?.let { jsonString ->
                val listType = object : TypeToken<List<BookItem>>() {}.type
                gson.fromJson<List<BookItem>>(jsonString, listType)
            } ?: emptyList()

            val updatedFavorites = currentFavorites.filter { it.id != favorite.id }
            preferences[favoriteBookListKey] = gson.toJson(updatedFavorites)
        }
    }


}