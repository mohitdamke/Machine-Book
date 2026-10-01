package com.mohit.machinebooks.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Query("SELECT * FROM books ORDER BY id DESC")
    fun getAllBooks(): Flow<List<BookEntity>>

   @Query(
       """
           select * from books where(
           title like '%' || :query || '%' OR          
            author like '%' || :query || '%'
           )
           and :category = 'All' or category = :category
       order by id DESC
       """
   )
    fun searchBooks(query: String, category: String):
            Flow<List<BookEntity>>


    @Query("select * from books where id = :id")
    fun getBookById(id : Int): Flow<BookEntity>

    @Insert
    suspend fun insertBooks(entity: BookEntity)

    @Update
    suspend fun updateBooks(entity: BookEntity)

    @Delete
    suspend fun deleteBooks(entity: BookEntity)














}