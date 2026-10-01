package com.mohit.machinebooks.data.repository

import com.mohit.machinebooks.data.local.BookDao
import com.mohit.machinebooks.data.local.BookEntity
import com.mohit.machinebooks.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(private val dao: BookDao) : BookRepository {
    override fun getAllBooks(): Flow<List<BookEntity>> {
        return dao.getAllBooks()
    }

    override fun searchBooks(
        query: String,
        category: String
    ): Flow<List<BookEntity>> {
        return dao.searchBooks(
            query = query,category = category
        )

    }

    override fun getBookById(id: Int): Flow<BookEntity> {
        return dao.getBookById(id)

    }

    override suspend fun insertBooks(entity: BookEntity) {
        return dao.insertBooks(entity)

    }

    override suspend fun updateBooks(entity: BookEntity) {
        return dao.updateBooks(entity)

    }

    override suspend fun deleteBooks(entity: BookEntity) {
        return dao.deleteBooks(entity)

    }
}