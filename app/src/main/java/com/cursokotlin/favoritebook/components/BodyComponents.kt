package com.cursokotlin.favoritebook.components

import android.content.Intent
import com.cursokotlin.favoritebook.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil3.compose.rememberAsyncImagePainter
import com.cursokotlin.favoritebook.model.BookItem
import com.cursokotlin.favoritebook.viewModel.FavoriteBooksDataViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    title: String = "",
    showBackButton: Boolean = false,
    onClickBackButton: () -> Unit,
    onClickAction: () -> Unit
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (!showBackButton) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "App Logo",
                        modifier = Modifier.size(80.dp)
                    )
                } else {
                    Text(
                        text = title,
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = { onClickBackButton() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        },
        actions = {
            if (!showBackButton) {
                IconButton(onClick = {
                    onClickAction()
                }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }

    )
}

@Composable
fun BookCard(
    book: BookItem,
    viewModel: FavoriteBooksDataViewModel,
    favoriteItemList: List<BookItem>,
    onClick: () -> Unit
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .size(width = 310.dp, height = 140.dp)
            .padding(bottom = 10.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                Column(modifier = Modifier.weight(0.4f)) {
                    MainImage(image = book.volumeInfo.imageLinks?.thumbnail.toString())
                }
                Column(
                    modifier = Modifier
                        .weight(0.9f)
                        .padding(start = 10.dp, top = 20.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "${book.volumeInfo.pageCount.toString()} N Pag",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = book.volumeInfo.authors?.firstOrNull()
                                .toString(), //firstOrNull() devuelve el primer elemento de la lista si no está vacía, o null si sí lo está.
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = book.volumeInfo.categories?.firstOrNull().toString(),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .weight(0.3f)
                        .padding(top = 40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IconButton(onClick = {
                        if (favoriteItemList.contains(book)) {
                            viewModel.deleteFavoriteBook(book)
                        } else {
                            viewModel.addFavoriteBook(book)
                        }
                    }) {
                        Icon(
                            imageVector = if (favoriteItemList.contains(book)) {
                                Icons.Default.Favorite
                            } else {
                                Icons.Default.FavoriteBorder
                            },
                            tint = if (favoriteItemList.contains(book)) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary,
                            contentDescription = ""
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MainImage(image: String) {
    val image = rememberAsyncImagePainter(image)
    Image(
        painter = image,
        contentDescription = "Imagen Libro",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
    )
}

@Composable
fun MetaWebSite(url: String, text: String) {
    val context = LocalContext.current
    //Intent permite interactuar con otras aplicaciones(Acceder al sitio web)
    val intent = Intent(Intent.ACTION_VIEW, url.toUri())

    Button(
        onClick = {
            if (url != "") context.startActivity(intent)
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Text(text = text, style = MaterialTheme.typography.labelLarge)
    }
}


@Composable
fun Loader() {
    CircularProgressIndicator(
        modifier = Modifier
            .wrapContentSize()
    )
}
