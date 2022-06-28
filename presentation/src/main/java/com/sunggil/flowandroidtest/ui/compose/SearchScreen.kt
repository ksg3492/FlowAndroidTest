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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(scaffoldState : ScaffoldState) {
    val coroutineScope = rememberCoroutineScope()
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
            onClick = {
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
}

@Preview
@Composable
fun SearchScreenPreview() {
    SearchScreen(rememberScaffoldState())
}
