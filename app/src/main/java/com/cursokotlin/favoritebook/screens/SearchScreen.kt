package com.cursokotlin.favoritebook.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cursokotlin.favoritebook.R
import com.cursokotlin.favoritebook.components.MainTopBar
import com.cursokotlin.favoritebook.viewModel.BooksViewModel


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreen(
    viewModel: BooksViewModel = hiltViewModel(),
    goBack: () -> Boolean,
    goNavigationDetailBook: (id:String) -> Unit
) {
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val books  by viewModel.books.collectAsState()
    val controller = LocalSoftwareKeyboardController.current

    Scaffold(topBar = {
        MainTopBar(
            title = stringResource(R.string.search_screen_title_top_bar),
            showBackButton = true,
            onClickBackButton = { goBack() }) {}
    }) { it ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                query = query,
                onQueryChange = { query = it },
                onSearch = {
                    viewModel.fetchBooks(query)
                    controller?.hide()
                },
                active = active,
                onActiveChange = { active = it },
                placeholder = { Text(text = stringResource(R.string.favorite_screen_text_searchbar)) },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "")
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier.clickable { query = "" })
                }
            ) {
               if(query.isNotEmpty()){
                 // Programación funcional
                 val filterBooks = books.filter { it.volumeInfo.title.contains(query, ignoreCase = true) }
                   LazyColumn(modifier = Modifier.fillMaxSize()){
                     items(filterBooks.size) { index ->
                         val item = filterBooks[index]
                         Text(
                             text = item.volumeInfo.title,
                             fontSize = 20.sp,
                             fontWeight = FontWeight.Bold,
                             color = Color.Black,
                             modifier = Modifier
                                 .padding(bottom = 10.dp, start = 10.dp)
                                 .clickable {
                                     goNavigationDetailBook(item.id)
                                 })

                     }
                   }
               }
            }
        }
    }
}