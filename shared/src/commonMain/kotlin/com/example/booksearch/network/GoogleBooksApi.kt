package com.example.booksearch.network

import com.example.booksearch.model.Book
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class GoogleBooksApi(private val client: HttpClient) : BookApi {
    override suspend fun searchBooks(query: String): List<Book> {
        val response: String = client.get("https://www.googleapis.com/books/v1/volumes") {
            url {
                parameters.append("q", query)
            }
        }.bodyAsText()

        val json = Json.parseToJsonElement(response).jsonObject
        val items = json["items"]?.jsonArray ?: return emptyList()

        return items.mapNotNull { item ->
            val volumeInfo = item.jsonObject["volumeInfo"]?.jsonObject ?: return@mapNotNull null
            Book(
                id = item.jsonObject["id"]?.jsonPrimitive?.content ?: "",
                title = volumeInfo["title"]?.jsonPrimitive?.content ?: "No Title",
                authors = volumeInfo["authors"]?.jsonArray?.map { it.jsonPrimitive.content }
                    ?: listOf("Unknown Author"),
                thumbnail = volumeInfo["imageLinks"]?.jsonObject?.get("thumbnail")?.jsonPrimitive?.content
            )
        }
    }
}
