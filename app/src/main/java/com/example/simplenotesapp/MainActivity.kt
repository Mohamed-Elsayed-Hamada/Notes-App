package com.example.simplenotesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.simplenotesapp.ui.theme.SimpleNotesAppTheme
import com.example.simplenotesapp.view.Home
import com.example.simplenotesapp.view.NoteDetailScreen
import com.example.simplenotesapp.view.NoteDetails
import com.example.simplenotesapp.view.NotesScreen
import com.example.simplenotesapp.view.viewmodel.NoteViewModel
import com.example.simplenotesapp.view.viewmodel.NotesScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleNotesAppTheme {
//                var currentScreen by remember { mutableStateOf<String>("home") }
                val viewModel: NotesScreenViewModel = hiltViewModel()
                val noteviewModel: NoteViewModel = hiltViewModel()
                val navController = rememberNavController()
                NavHost(navController, startDestination = Home) {
                    composable<Home> {
                        NotesScreen(
                            viewModel,
                            onOpenNote = { it ->
                                navController.navigate(NoteDetails(it.toString()))
                            })
                    }
                    composable<NoteDetails> { backStackEntry ->
                        val NoteRoute: NoteDetails = backStackEntry.toRoute<NoteDetails>()
                        val noteId = NoteRoute.id
                        NoteDetailScreen(
                            noteId = noteId, onNavigateBack =
                                { navController.popBackStack() },
                            noteviewModel
                        )
                    }
                }
//
//                when (currentScreen) {
//                    "home" -> {
//                        NotesScreen(
//                            viewModel = viewModel,
//                            onOpenNote = { _ -> currentScreen = "detail" }
//                        )
//                    }
//
//                    "detail" -> {
//                        BackHandler {
//                            currentScreen = "home"
//                        }
//                        NoteDetailScreen(
//                            onNavigateBack = { currentScreen = "home" }
//                        )
//                    }
//                }
            }
        }
    }
}

