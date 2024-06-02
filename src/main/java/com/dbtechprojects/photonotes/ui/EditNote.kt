package com.dbtechprojects.photonotes.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dbtechprojects.photonotes.model.Note
import com.dbtechprojects.photonotes.ui.theme.PhotoNotesTheme
import com.dbtechprojects.photonotes.ui.theme.Purple700
import com.dbtechprojects.photonotes.ui.theme.PurpleGrey80
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun EditNote(noteId: Int, navController: NavController, viewModel: TaskView) {
    val scope = rememberCoroutineScope()
    val note = remember {
        mutableStateOf(Note(note = "", id = 0, title = "" , deadline =""))
    }

    val prevTask = remember { mutableStateOf(note.value.note) }
    val  prev_tit = remember { mutableStateOf(note.value.title) }
    val  currentdeadline = remember { mutableStateOf(note.value.deadline) }

    LaunchedEffect(true) {
        scope.launch(Dispatchers.IO) {
            note.value = viewModel.getTask(noteId) ?: Note(note = "", id = 0, title = "" , deadline ="")
            prevTask.value = note.value.note
            prev_tit.value = note.value.title
            currentdeadline.value= note.value.deadline

        }
    }



    PhotoNotesTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.primary) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(text = "Update Your Note") }
                    )
                },
                content = {

                    Column(
                        Modifier
                            .padding(12.dp)
                            .fillMaxSize()
                    ) {

                        TextField(
                            value = prev_tit.value,
                            modifier = Modifier
                                .background(color= PurpleGrey80)
                                .fillMaxWidth(),
                            colors = TextFieldDefaults.textFieldColors(
                                cursorColor = Color.Black,
                                focusedLabelColor = Color.Black,
                            ),
                            onValueChange = { value ->
                                prev_tit.value = value

                            },
                            label = { Text(text = "Title") }
                        )
                        Spacer(modifier = Modifier.padding(12.dp))
                        TextField(
                            value = prevTask.value,
                            modifier = Modifier
                                .fillMaxHeight(0.61f)
                                .background(color= PurpleGrey80)
                                .fillMaxWidth(),
                            colors = TextFieldDefaults.textFieldColors(
                                cursorColor = Color.Black,
                                focusedLabelColor = Color.Black,
                            ),
                            onValueChange = { value ->
                                prevTask.value = value

                            },
                            label = { Text(text = "Body") }
                        )
                        Spacer(modifier = Modifier.padding(12.dp))
                        TextField(
                            value = currentdeadline.value,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color= PurpleGrey80),
                            colors = TextFieldDefaults.textFieldColors(
                                cursorColor = Color.Black,
                                focusedLabelColor = Color.Black,
                            ),
                            onValueChange = { value ->
                                currentdeadline.value = value

                            },
                            label = { Text(text = "Deadline") }
                        )
                        Spacer(modifier = Modifier.padding(15.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Button(
                                onClick = {
                                    viewModel.editTask(
                                        Note(
                                            id = note.value.id,
                                            note = prevTask.value,
                                            title = prev_tit.value,
                                            deadline = currentdeadline.value
                                        )
                                    )
                                    navController.popBackStack()

                                },
                                shape = RoundedCornerShape(15.dp),
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = Purple700
                                ),
                                modifier = Modifier
                                    .height(65.dp)
                                    .width(150.dp)

                            ) {
                                Text(
                                    text = "Save Note",
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }

            )
        }
    }
}