package com.mohit.machinebooks.navigation

object Routes {

    const val BooksList = "book_list"
    const val AddBooksList = "add_book_list"
    const val EditBooksList = "edit_book_list/{id}"
    fun editBook(id : Int) = "edit_book_list/$id"
}