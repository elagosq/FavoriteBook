package com.cursokotlin.favoritebook.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.collectAsLazyPagingItems
import com.cursokotlin.favoritebook.R
import com.cursokotlin.favoritebook.components.BookCard
import com.cursokotlin.favoritebook.components.Loader
import com.cursokotlin.favoritebook.components.MainTopBar
import com.cursokotlin.favoritebook.viewModel.BooksViewModel
import com.cursokotlin.favoritebook.viewModel.FavoriteBooksDataViewModel
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    booksViewModel: BooksViewModel = hiltViewModel(),
    favoriteBookViewModel: FavoriteBooksDataViewModel = hiltViewModel(),
    goNavigationSearch: () -> Unit,
    goNavigationFavorite: () -> Unit,
    goNavigationDetailBook: (id: String) -> Unit
) {
    Scaffold(
        topBar = {
            MainTopBar(onClickBackButton = {}) {
                goNavigationSearch()
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary,
                onClick = { goNavigationFavorite() },
                shape = CircleShape
            ) {
                Icon(imageVector = Icons.Filled.Favorite, contentDescription = "Favorite")
            }
        }
    ) {
        ContentHomeView(booksViewModel, favoriteBookViewModel, it, goNavigationDetailBook)
    }
}

@Composable
fun ContentHomeView(
    viewModel: BooksViewModel,
    favoriteBookViewModel: FavoriteBooksDataViewModel,
    pad: PaddingValues,
    goNavigationDetailBook: (String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val controller = LocalSoftwareKeyboardController.current
    val booksPage =
        viewModel.booksPage.collectAsLazyPagingItems() // Obtener los datos de la paginación
    val favoriteBook by favoriteBookViewModel.favoriteBookList.collectAsState()

    Column(
        modifier = Modifier
            .padding(pad)
            .fillMaxSize(), // Usar fillMaxSize en el Column principal
        horizontalAlignment = Alignment.CenterHorizontally // Para centrar el contenido (incluido el Loader si aparece)
    ) {
        // Sección de búsqueda (se mantiene igual)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 30.dp),
            verticalAlignment = Alignment.CenterVertically // Asegura que los elementos de la fila estén alineados verticalmente
        ) {
            TextField(
                value = viewModel.searchBook,
                onValueChange = { viewModel.searchBookItem(it) },
                label = { Text(stringResource(R.string.home_screen_textfield_search), style = MaterialTheme.typography.labelMedium) },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    disabledIndicatorColor = MaterialTheme.colorScheme.primary,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent
                ),
                modifier = Modifier.weight(1f) // TextField toma el espacio restante
            )
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        booksPage.refresh() // Refresca los datos paginados
                        viewModel.cleanSearchBook()
                        controller?.hide()
                    }
                },
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .padding(start = 10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.home_screen_icon_search_description),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        // --- Lógica del Loader Inicial y Contenido ---
        when (booksPage.loadState.refresh) {
            is LoadState.Loading -> {
                // Muestra el Loader en el centro de la pantalla mientras se carga la primera página
                // Este Column solo se muestra cuando la carga inicial está activa
                Column(
                    modifier = Modifier.fillMaxSize(), // Rellena el espacio disponible debajo de la barra de búsqueda
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Loader() // Tu composable Loader
                }
            }

            is LoadState.Error -> {
                // Muestra un mensaje de error si la carga inicial falla
                val error = (booksPage.loadState.refresh as LoadState.Error).error
                Text(
                    text = "Error al cargar libros: ${error.localizedMessage ?: "Error desconocido"}",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleLarge
                )
            }

            else -> { // LoadState.NotLoading
                // Si la carga inicial ha terminado (sin error y no cargando)
                if (booksPage.itemCount == 0 && booksPage.loadState.append.endOfPaginationReached) {
                    // Si no hay ítems Y Paging dice que no hay más datos disponibles
                    Text(
                        text = stringResource(R.string.home_view_not_data_show),
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleLarge
                    )
                } else {
                    // Si hay ítems o la carga inicial ha terminado y podría haber más datos
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp)
                    ) {
                        LazyColumn(modifier = Modifier.fillMaxWidth()) {
                            items(booksPage.itemCount) { index ->
                                val item = booksPage[index]
                                if (item != null) {
                                    BookCard(item, favoriteBookViewModel, favoriteBook) {
                                        goNavigationDetailBook(item.id)
                                    }
                                }
                            }

                            // Loader para la paginación (cuando se hace scroll para cargar más)
                            when (booksPage.loadState.append) {
                                is LoadState.Loading -> {
                                    item {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth() // Usa fillMaxWidth, no fillParentMaxSize en item{}
                                                .padding(16.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            CircularProgressIndicator(
                                                modifier = Modifier.wrapContentSize()
                                            )
                                        }
                                    }
                                }

                                is LoadState.Error -> {
                                    item {
                                        Text(
                                            text = stringResource(R.string.home_screen_error_show_data),
                                            color = MaterialTheme.colorScheme.onBackground,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(16.dp),
                                            style = MaterialTheme.typography.titleLarge
                                        )
                                    }
                                }

                                is LoadState.NotLoading -> {
                                    // Puedes añadir un mensaje si se llega al final y hay ítems
                                    if (booksPage.loadState.append.endOfPaginationReached && booksPage.itemCount > 0) {
                                        item {
                                            Text(
                                                text = stringResource(R.string.home_screen_end_results),
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(16.dp),
                                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}