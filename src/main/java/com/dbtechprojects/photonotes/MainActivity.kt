package com.dbtechprojects.photonotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dbtechprojects.photonotes.ui.TaskView
import com.dbtechprojects.photonotes.ui.DetailPage
import com.dbtechprojects.photonotes.ui.EditNote
import com.dbtechprojects.photonotes.ui.MainPage
import com.dbtechprojects.photonotes.ui.NewNote
import com.dbtechprojects.photonotes.ui.ViewFactory

class MainActivity : ComponentActivity() {



    private lateinit var taskView : TaskView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // retrieve viewModel
        taskView =  ViewFactory(PhotoNotesApp.getDao()).create(TaskView::class.java)

        val start = "main"
        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = start
            ) {
                // Notes List
                composable(start) { MainPage(navController, taskView) }

                // Notes Detail page
                composable(
                    Constants.NAV_DETAIL,
                    arguments = listOf(navArgument(Constants.NAV_ID) {
                        type = NavType.IntType
                    })
                ) { backStackEntry ->
                    backStackEntry.arguments?.getInt(Constants.NAV_ID)
                        ?.let { DetailPage(noteId = it, navController, taskView) }
                }

                // Notes Edit page
                composable(
                    Constants.NAV_EDIT,
                    arguments = listOf(navArgument(Constants.NAV_ID) {
                        type = NavType.IntType
                    })
                ) { backStackEntry ->
                    backStackEntry.arguments?.getInt(Constants.NAV_ID)
                        ?.let { EditNote(noteId = it, navController, taskView) }
                }

                // Create Note Page
                composable(Constants.NAV_NEW) {NewNote(navController, taskView) }

            }

        }
    }
}
