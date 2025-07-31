package com.cursokotlin.favoritebook.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cursokotlin.favoritebook.R
import com.cursokotlin.favoritebook.components.MainImage
import com.cursokotlin.favoritebook.components.MainTopBar
import com.cursokotlin.favoritebook.components.MetaWebSite
import com.cursokotlin.favoritebook.viewModel.BooksViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailBookScreen(
    booksViewModel: BooksViewModel = hiltViewModel(),
    id: String,
    goBack: () -> Boolean
) {
    LaunchedEffect(Unit) {
       if(id.isNotEmpty()) booksViewModel.getBookById(id)
    }
    Scaffold(
        topBar = {
            MainTopBar(
                title = booksViewModel.state.title,
                showBackButton = true,
                onClickBackButton = { goBack() }) { }
        }
    ) {
        ContentDetailBook(it, booksViewModel)
    }
}

@Composable
fun ContentDetailBook(pad: PaddingValues, viewModel: BooksViewModel) {
    val state = viewModel.state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(pad)
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        // --- Primera Parte: Imagen e Información Principal (50% de la altura) ---
        Row( // <--- Cambiado a Row para disposición horizontal
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // Ocupa la mitad de la altura disponible
                .background(MaterialTheme.colorScheme.surface)
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically // Centra el contenido verticalmente en la fila
        ) {
            Column(modifier = Modifier.weight(0.7f)) {
                MainImage(image = state.imageLinks)
            }
            Spacer(modifier = Modifier.width(16.dp)) // Espacio entre la imagen y el texto

            // Columna con Título y Autor (a la derecha de la imagen)
            Column(
                modifier = Modifier.weight(1f) // Ocupa el espacio restante en el ancho de la Row
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = stringResource(R.string.detail_screen_text_editorial),
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = state.publisher,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = stringResource(R.string.detail_screen_number_page),
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = state.pageCount.toString(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = stringResource(R.string.detail_screen_text_published_date),
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = state.publishedDate,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
        val scroll = rememberScrollState(0)
        // --- Segunda Parte: Detalles y Descripción (50% de la altura) ---
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // Ocupa la otra mitad de la altura disponible
                .background(MaterialTheme.colorScheme.background)
                .padding(20.dp)
                .verticalScroll(scroll)
        ) {
            // Sinopsis/Descripción del libro
            Text(
                text = state.title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = state.subtitle,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(12.dp)
            )

            Text(
                text = state.description,
                style = MaterialTheme.typography.bodyLarge,
            )
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MetaWebSite(text = stringResource(R.string.detail_screen_btn_text_advance), url = state.previewLink)
                MetaWebSite(text = stringResource(R.string.detail_screen_btn_text_buy), url = state.canonicalVolumeLink)
            }
        }
    }
}

