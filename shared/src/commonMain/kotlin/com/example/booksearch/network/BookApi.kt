package com.example.booksearch.network

import com.example.booksearch.model.Book

interface BookApi {
    suspend fun searchBooks(query: String): List<Book>
}