package com.cursokotlin.favoritebook.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cursokotlin.favoritebook.R
import com.cursokotlin.favoritebook.components.BookCard
import com.cursokotlin.favoritebook.components.MainTopBar
import com.cursokotlin.favoritebook.viewModel.FavoriteBooksDataViewModel
import kotlin.String
import kotlin.Unit


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FavoriteScreen(
    viewModel: FavoriteBooksDataViewModel = hiltViewModel(),
    goNavigationDetailBook: (String) -> Unit,
    goBack: () -> Boolean
) {
    Scaffold(
        topBar = {
            MainTopBar(
                showBackButton = true,
                title = stringResource(R.string.favorite_screen_title_topbar),
                onClickBackButton = { goBack() }) { }
        }
    ) {
        ContentFavoriteView(it, viewModel,goNavigationDetailBook)
    }
}

@Composable
fun ContentFavoriteView(
    pad: PaddingValues,
    viewModel: FavoriteBooksDataViewModel,
    goNavigationDetailBook: (String) -> Unit
) {
    val favoriteBookList by viewModel.favoriteBookList.collectAsState()

    Column(
        modifier = Modifier
            .padding(pad)
            .padding(start = 25.dp, top = 15.dp)
            .fillMaxSize()
    ) {
        if (favoriteBookList.isNotEmpty()) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(favoriteBookList.size) { index ->
                    val item = favoriteBookList[index]
                    BookCard(item, viewModel, favoriteBookList) {
                        goNavigationDetailBook(item.id)
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(R.string.favorite_screen_not_item_favorite), style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}