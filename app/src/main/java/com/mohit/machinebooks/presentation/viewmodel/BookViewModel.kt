package com.mohit.machinebooks.presentation.viewmodel

import android.R.attr.author
import android.R.attr.category
import android.util.Log.v
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohit.machinebooks.data.local.BookEntity
import com.mohit.machinebooks.domain.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val repository: BookRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow<String>("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _selectedBook = MutableStateFlow<BookEntity?>(null)
    val selectedBook = _selectedBook.asStateFlow()

    private val _selectedCategory = MutableStateFlow<String>("All")
    val selectedCategory = _selectedCategory.asStateFlow()

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading = _isLoading.asStateFlow()

    private val _isError = MutableStateFlow<String>("")
    val isError = _isError.asStateFlow()

    val books: StateFlow<List<BookEntity>> =
        combine(
            _searchQuery, _selectedCategory
        ) { (query, category) ->
            query to category
        }
            .flatMapLatest { (query, category) ->
                if (query.isNotEmpty() || category != "All") {
                    repository.searchBooks(
                        query = query,
                        category = category
                    )
                } else {
                    repository.getAllBooks()
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000), initialValue = emptyList()
            )

    fun searchQuery(query: String) {
        _searchQuery.value = query
    }

    fun bookCategory(category: String) {
        _selectedCategory.value = category
    }

    fun getBookById(id: Int) {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                repository.getBookById(id).collect {
                    _selectedBook.value = it
                }

                _isLoading.value = false

            } catch (E: Exception) {
                _isError.value = E.message ?: "Something went wrong"
            }
        }
    }

    fun insertBook(
        title: String,
        author: String,
        category: String,
        price: Double
    ) {
        viewModelScope.launch {
            try {
               repository.insertBooks(
                   BookEntity(
                       title = title,
                       author = author,
                       category = category,
                       price = price
                   )
               )

            } catch (E: Exception) {
                _isError.value = E.message ?: "Something went wrong"
            }
        }
    }
   fun updateBook(
        entity: BookEntity
    ) {
        viewModelScope.launch {
            try {
               repository.updateBooks(
                   entity
               )

            } catch (E: Exception) {
                _isError.value = E.message ?: "Something went wrong"
            }
        }
    }

   fun deleteBook(
        entity: BookEntity
    ) {
        viewModelScope.launch {
            try {
               repository.deleteBooks(
                   entity
               )

            } catch (E: Exception) {
                _isError.value = E.message ?: "Something went wrong"
            }
        }
    }


}






















