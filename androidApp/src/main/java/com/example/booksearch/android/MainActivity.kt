package com.example.booksearch.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.booksearch.network.GoogleBooksApi
import com.example.booksearch.repository.BookRepository
import com.example.booksearch.usecase.BookSearchUseCase
import io.ktor.client.HttpClient

class MainActivity : ComponentActivity() {
    private val httpClient = HttpClient()
    private val useCase = BookSearchUseCase(BookRepository(GoogleBooksApi(httpClient)))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BookSearchScreen(useCase)
                }
            }
        }
    }
}