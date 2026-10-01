package com.mohit.machinebooks.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.mohit.machinebooks.presentation.viewmodel.BookViewModel

@Composable
fun BookListScreen(onAdd: () -> Unit, onEdit: (Int) -> Unit) {

    val viewmodel: BookViewModel = hiltViewModel()
    val query = viewmodel.searchQuery.collectAsState().value
    val category = viewmodel.selectedCategory.collectAsState().value
    val books = viewmodel.books.collectAsState().value
    val isLoading = viewmodel.isLoading.collectAsState().value
    val isError = viewmodel.isError.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        OutlinedTextField(
            value = query,
            onValueChange = viewmodel::searchQuery
        )

        Spacer(Modifier.heightIn(10.dp))

        Row() {
            Button(onClick = {viewmodel.bookCategory("All")}) {  Text("All")}
            Button(onClick = {viewmodel.bookCategory("Food")}) {  Text("Food")}
            Button(onClick = {viewmodel.bookCategory("Pet")}) {  Text("Pet")}
            Button(onClick = {viewmodel.bookCategory("Animals")}) {  Text("Animals")}
        }

        Spacer(Modifier.heightIn(10.dp))

        Button(onClick = { onAdd() }) { Text("Add +") }
        Spacer(Modifier.heightIn(10.dp))

        if (isLoading) {
            CircularProgressIndicator()
        } else if (isError.isNotEmpty()) {
            Text("Error Due To $isError")
        } else {
            LazyColumn {
                items(books) { book ->
                    Text(book.title)
                    Text(book.author)
                    Text(book.category)
                    Text(book.price.toString())
                    Button(onClick = { onEdit(book.id.toInt()) }) { Text("Edit") }
                    Button(onClick = { viewmodel.deleteBook(book) }) { Text("Delete") }
                }
            }

        }


    }
}





















