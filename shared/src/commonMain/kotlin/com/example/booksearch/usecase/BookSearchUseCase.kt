package com.example.booksearch.usecase

import com.example.booksearch.model.Book
import com.example.booksearch.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class BookSearchUseCase(private val repository: BookRepository) {
    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> get() = _books

    suspend fun search(query: String) {
        _books.value = repository.searchBooks(query)
    }
}