package com.mohit.machinebooks.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mohit.machinebooks.presentation.screens.AddBookListScreen
import com.mohit.machinebooks.presentation.screens.BookListScreen
import com.mohit.machinebooks.presentation.screens.EditBookListScreen

@Composable
fun NavigationController(navHostController: NavHostController) {


    NavHost(
        navController = navHostController,
        startDestination = Routes.BooksList
    ) {

        composable(Routes.BooksList){
            BookListScreen(
                onAdd = {navHostController.navigate(Routes.AddBooksList)}
                , onEdit = {
                    navHostController.navigate(Routes.editBook(it))
                }
            )

        }
        composable(Routes.AddBooksList){
            AddBookListScreen(
                onBack = {navHostController.popBackStack()}
            )

        }
        composable(Routes.EditBooksList){ back ->
            val bookId = back.arguments?.getString("id")?.toIntOrNull()
            EditBookListScreen(
                onBack = {navHostController.popBackStack()},
                bookId = bookId ?: 0
            )

        }



    }























}