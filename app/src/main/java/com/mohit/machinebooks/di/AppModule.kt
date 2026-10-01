package com.mohit.machinebooks.di

import android.content.Context
import androidx.room.Room
import com.mohit.machinebooks.data.local.BookDao
import com.mohit.machinebooks.data.local.BooksDatabase
import com.mohit.machinebooks.data.repository.BookRepositoryImpl
import com.mohit.machinebooks.domain.repository.BookRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(
        @ApplicationContext context : Context
    ): BooksDatabase{
        return Room.databaseBuilder(
            context,
            BooksDatabase::class.java,
            "books"
        ).build()
    }

    @Provides
    @Singleton
    fun provideBookDao(
        database: BooksDatabase
    ): BookDao{
        return database.bookDao()
    }

    @Provides
    @Singleton
    fun provideBookRepository(
        dao: BookDao
    ): BookRepository {
        return BookRepositoryImpl(dao)
    }


}