package com.sunggil.flowandroidtest.ui.compose

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
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
    val inputText = rememberSaveable { mutableStateOf("") }

    val dataState = viewModel.movieListState.value
    val keyboardController = LocalSoftwareKeyboardController.current


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        //Search Row
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
                onClick = {
                    keyboardController?.hide()

                    viewModel.clear()
                    viewModel.search(inputText.value)
                    coroutineScope.launch {
                        // Display the snackbar on the screen. `showSnackbar` is a function
                        // that suspends until the snackbar disappears from the screen
                        scaffoldState.snackbarHostState.showSnackbar(inputText.value)
                    }
                }
            ) {
                Text(
                    text = "검색",
                    fontSize = 12.sp
                )
            }
        }

        Text(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            text = "List Size : ${dataState?.size}"
        )
    }

}

@Preview
@Composable
fun SearchScreenPreview() {
    SearchScreen(rememberScaffoldState())
}
