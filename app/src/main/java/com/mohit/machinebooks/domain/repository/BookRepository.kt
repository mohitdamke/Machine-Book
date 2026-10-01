package com.mohit.machinebooks.domain.repository

import com.mohit.machinebooks.data.local.BookEntity
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    fun getAllBooks(): Flow<List<BookEntity>>

    fun searchBooks(query: String, category: String):
            Flow<List<BookEntity>>

    fun getBookById(id : Int): Flow<BookEntity>

    suspend fun insertBooks(entity: BookEntity)

    suspend fun updateBooks(entity: BookEntity)

    suspend fun deleteBooks(entity: BookEntity)



}