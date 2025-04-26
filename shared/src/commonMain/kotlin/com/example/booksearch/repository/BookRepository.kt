package com.example.booksearch.repository

import com.example.booksearch.model.Book
import com.example.booksearch.network.BookApi

class BookRepository(private val api: BookApi) {
    suspend fun searchBooks(query: String): List<Book> {
        return api.searchBooks(query)
    }
}