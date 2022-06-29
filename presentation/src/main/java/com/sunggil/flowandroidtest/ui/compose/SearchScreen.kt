package com.sunggil.flowandroidtest.ui.compose

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sunggil.flowandroidtest.R
import com.sunggil.flowandroidtest.domain.Movie
import com.sunggil.flowandroidtest.ui.compose.glide.loadPicture
import com.sunggil.flowandroidtest.ui.fragment.search.SearchViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    scaffoldState : ScaffoldState,
    viewModel : SearchViewModel = hiltViewModel()
) {
    Log.e("SG2", "SearchScreen()")
    val coroutineScope = rememberCoroutineScope()

    val dataState = viewModel.movieListState
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        SearchTextField { input ->
            keyboardController?.hide()

            viewModel.clear()
            viewModel.searchCompose(input)
            coroutineScope.launch {
                // Display the snackbar on the screen. `showSnackbar` is a function
                // that suspends until the snackbar disappears from the screen
                scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                scaffoldState.snackbarHostState.showSnackbar(input)
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(1.0f),
        ) {
            itemsIndexed(dataState) { index, item ->
                MovieItem(item) {
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                        scaffoldState.snackbarHostState.showSnackbar(item.title)
                    }
                }
            }
        }
    }
}

@Composable
fun SearchTextField(
    searchAction : (String) -> Unit = {}
) {
    val inputText = rememberSaveable { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        /**
         * BasicTextField 는 Non-Padding
         * TextField 는 Default-Padding
         */
        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .fillMaxHeight()
                .background(Color.LightGray)
                .padding(10.dp)
        ) {
            BasicTextField(
                value = inputText.value,
                onValueChange = {
                    inputText.value = it
                    Log.e("SG2", "input text : ${inputText.value}")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                singleLine = true,
            )
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            onClick = { searchAction(inputText.value) }
        ) {
            Text(
                text = "검색",
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun MovieItem(
    movie : Movie,
    onClick : (Movie) -> Unit = { }
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(10.dp)
            .clickable {
                onClick(movie)
            },
    ) {
        Card(
            modifier = Modifier
                .fillMaxHeight(1.0f)
                .aspectRatio(1.0f)
        ) {
            val painter = loadPicture(
                url = movie.image,
                placeholder = painterResource(id = R.drawable.ic_baseline_image_24)
            )

            Image(
                painter = painter,
                contentDescription = movie.image,
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.width(30.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            text = movie.title
        )
    }
}

@Preview
@Composable
fun SearchTextFieldPreview() {
    SearchTextField()
}

@Preview
@Composable
fun MovieItemPreview() {
    MovieItem(Movie("1", "title", "link", "image", "", ""))
}
