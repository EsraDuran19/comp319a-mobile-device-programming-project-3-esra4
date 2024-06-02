package com.dbtechprojects.photonotes.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dbtechprojects.photonotes.Constants
import com.dbtechprojects.photonotes.model.Note
import com.dbtechprojects.photonotes.ui.theme.PhotoNotesTheme
import com.dbtechprojects.photonotes.ui.theme.Purple80
import com.dbtechprojects.photonotes.ui.theme.PurpleGrey80
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailPage(noteId: Int, navController: NavController, viewModel: TaskView) {
    val scope = rememberCoroutineScope()
    val note = remember {
        mutableStateOf(Note(note = "", id = 0, title = "Please create a task" , deadline =""))
    }

    val id_str = note.value.id.toString()
    LaunchedEffect(true) {
        scope.launch(Dispatchers.IO) {
            note.value = viewModel.getTask(noteId) ?: Note(note = "", id = 0, title = "Please create a task" , deadline ="")
        }
    }

    PhotoNotesTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = Purple80) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(text = "Note id: $id_str") }
                    )
                },
                content = {
                    Column() {
                        // Details de göster sırayla
                        Text(
                            text = "The date updated",
                            Modifier.padding(12.dp),
                            textDecoration = TextDecoration.Underline,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = note.value.dateUpdated,
                            Modifier
                                .padding(12.dp)
                                .background(PurpleGrey80, RectangleShape),
                            color = Color.Blue
                        )

                        Spacer(modifier = Modifier.padding(20.dp))

                        Text(
                            text = "Title",
                            Modifier.padding(12.dp),
                            textDecoration = TextDecoration.Underline,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = note.value.title,
                            Modifier
                                .padding(12.dp)
                                .background(PurpleGrey80, RectangleShape),
                            color = Color.Black
                        )


                        Spacer(modifier = Modifier.padding(15.dp))

                        Text(
                            text = "Description",
                            Modifier.padding(12.dp),
                            color = Color.Black,
                            textDecoration = TextDecoration.Underline,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = note.value.note,
                            Modifier
                                .padding(12.dp)
                                .background(PurpleGrey80, RectangleShape),
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.padding(15.dp))

                        Text(
                            text = "Deadline of the task",
                            Modifier.padding(12.dp),
                            color = Color.Black,
                            textDecoration = TextDecoration.Underline,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = note.value.deadline,
                            Modifier
                                .padding(12.dp)
                                .background(PurpleGrey80, RectangleShape),
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.padding(40.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Button(
                                onClick = {
                                    navController.navigate(
                                        Constants.noteEditNavigation(
                                            note.value.id ?: 0
                                        )
                                    )
                                },
                                shape = RoundedCornerShape(15.dp),
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = Color.White
                                ),
                                modifier = Modifier
                                    .height(65.dp)
                                    .width(170.dp)

                            ) {
                                Text(
                                    text = "Edit Note",
                                    fontSize = 16.sp,
                                    color = Color.Black
                                )
                            }
                        }
                    }
                }
            )


        }
    }
}