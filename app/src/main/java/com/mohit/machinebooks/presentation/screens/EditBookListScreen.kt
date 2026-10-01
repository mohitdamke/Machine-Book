package com.mohit.machinebooks.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.mohit.machinebooks.presentation.viewmodel.BookViewModel

@Composable
fun EditBookListScreen(
    bookId: Int,
    onBack: () -> Unit
) {
    val viewmodel: BookViewModel = hiltViewModel()
    val selectedBook = viewmodel.selectedBook.collectAsState().value
    val isLoading = viewmodel.isLoading.collectAsState().value
    val isError = viewmodel.isError.collectAsState().value

    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var category by remember { mutableStateOf("All") }
    val categories = listOf(
        "All",
        "Food",
        "Pet",
        "Animals"
    )

    LaunchedEffect(bookId) {
        viewmodel.getBookById(bookId)
    }

    LaunchedEffect(selectedBook) {
        selectedBook?.let {
            title = it.title
            author = it.author
            category = it.category
            price = it.price.toString()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        OutlinedTextField(
            value = title,
            onValueChange = { title = it }
        )
        Spacer(Modifier.heightIn(10.dp))

        OutlinedTextField(
            value = author,
            onValueChange = { author = it }
        )
        Spacer(Modifier.heightIn(10.dp))

        OutlinedTextField(
            value = price,
            onValueChange = { price = it }
        )
        Spacer(Modifier.heightIn(10.dp))

        Button(onClick = {expanded = true}) { Text(category) }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            categories.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) }, onClick = {
                        category = item
                        expanded = false
                    }
                )
            }}
        Spacer(Modifier.heightIn(10.dp))
        Button(onClick = {
            selectedBook?.let {
                viewmodel.updateBook(
                    it.copy(
                        title = title,
                        author = author,
                        category = category,
                        price = price.toDouble()
                    )
                )
            }
            onBack()
        }) {
            Text("Update Book")
        }


    }


}