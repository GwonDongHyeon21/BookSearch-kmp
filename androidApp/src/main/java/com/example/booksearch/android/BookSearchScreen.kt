package com.example.booksearch.android

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.booksearch.network.GoogleBooksApi
import com.example.booksearch.repository.BookRepository
import com.example.booksearch.usecase.BookSearchUseCase
import io.ktor.client.HttpClient
import kotlinx.coroutines.launch

@Composable
fun BookSearchScreen(viewModel: BookSearchUseCase) {
    var query by remember { mutableStateOf("") }
    val books by viewModel.books.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row {
            TextField(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text(text = "Search for books...") }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                coroutineScope.launch { viewModel.search(query) }
            }) {
                Text("Search")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        books.forEach { book ->
            Text("${book.title} by ${book.authors.joinToString(", ")}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookSearchPreview() {
    BookSearchScreen(BookSearchUseCase(BookRepository(GoogleBooksApi(HttpClient()))))
}