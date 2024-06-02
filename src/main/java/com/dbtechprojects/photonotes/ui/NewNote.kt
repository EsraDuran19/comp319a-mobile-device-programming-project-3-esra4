package com.dbtechprojects.photonotes.ui
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dbtechprojects.photonotes.ui.theme.PhotoNotesTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NewNote(
    navController: NavController,
    viewModel: TaskView
) {


    val prevTask = remember { mutableStateOf("") }
    val  prev_tit = remember { mutableStateOf("") }
    val  currentdeadline = remember { mutableStateOf("") }



         PhotoNotesTheme {
        // A surface container using the 'background' color from the theme

            Scaffold(
                topBar = {
                    Button(
                            onClick = {  viewModel.newTask(
                        prev_tit.value,
                                prevTask.value,
                        currentdeadline.value
                    )
                        navController.popBackStack() }) {
                        Text("New Note")
                    }

                },
                content = {
                    Column(
                        Modifier
                            .padding(12.dp)
                            .fillMaxSize()
                    ) {


                        TextField(
                            value = prev_tit.value,
                            modifier = Modifier.fillMaxWidth(),
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
                            colors = TextFieldDefaults.textFieldColors(
                                cursorColor = Color.Black,
                                focusedLabelColor = Color.Black,
                            ),
                            modifier = Modifier
                                .fillMaxHeight(0.5f)
                                .fillMaxWidth(),
                            onValueChange = { value ->
                                prevTask.value = value
                            },
                            label = { Text(text = "Body") }
                        )
                        Spacer(modifier = Modifier.padding(12.dp))
                        TextField(
                            value = currentdeadline.value,
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.textFieldColors(
                                cursorColor = Color.Black,
                                focusedLabelColor = Color.Black,
                            ),
                            onValueChange = { value ->
                                currentdeadline.value = value
                            },
                            label = { Text(text = "Deadline") }
                        )

                    }
                }

            )

    }
}