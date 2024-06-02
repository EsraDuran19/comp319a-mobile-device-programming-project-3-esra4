package com.dbtechprojects.photonotes.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dbtechprojects.photonotes.model.Note
import com.dbtechprojects.photonotes.model.getDay
import com.dbtechprojects.photonotes.ui.theme.PhotoNotesTheme
import com.dbtechprojects.photonotes.ui.theme.Purple80


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainPage(navController: NavController, viewModel: TaskView) {
    val shouldShowDialog = remember { mutableStateOf(false) }
    val deleteText = remember { mutableStateOf("") }
    val search_text = remember { mutableStateOf("") }
    val notesToDelete = remember { mutableStateOf(listOf<Note>()) }
    val notes = viewModel.notes.observeAsState()
    PhotoNotesTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = colors.primary) {
            Scaffold(
                topBar = {

                    TopAppBar(
                        title = { Text(text = "WELCOME TO NOTEPAD APP !") },
                        navigationIcon = {
                            IconButton(onClick = {navController.navigate("newNote")}){
                                Icon(Icons.Filled.Add, contentDescription = "New")
                            }


                        }
                    )

                }


            ) {
                DeleteAlert(
                    shouldShowDialog = shouldShowDialog,
                    note_del = notesToDelete,
                    action = {
                        notesToDelete.value.forEach {
                            viewModel.delTask(it)
                        }
                    })
                Column() {
                    //Query i değiştiriyor
                    Column(Modifier.padding(top = 15.dp, start = 15.dp, end = 15.dp, bottom = 0.dp)) {
                        TextField(
                            value = search_text.value,
                            placeholder = { Text("Search..") },
                            maxLines = 1,
                            onValueChange = { search_text.value = it },
                            modifier = Modifier
                                .background(Color.White)
                                .fillMaxWidth(),
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.Black,
                            ),
                        )

                    }
                    // O query i kullanarak var olanaları gösteriyor.
                    FullList(
                        tasks = notes.value.orEmpty(),
                        text = search_text,
                        dialog = shouldShowDialog,
                        deleteText = deleteText,
                        navController = navController,
                        notesToDelete = notesToDelete
                    )
                }


            }

        }
    }
}

@Composable
fun DeleteAlert(
    shouldShowDialog: MutableState<Boolean>,
    action: () -> Unit,
    note_del: MutableState<List<Note>>
) {
    if (shouldShowDialog.value) {
        AlertDialog(
            onDismissRequest = {
                shouldShowDialog.value = false
            },
            title = {
                Text(text = "Delete Task")
            },
            text = {

                Text("Dou you want to delete this Task?")

            },
            buttons = {
                Row(
                    modifier = Modifier.padding(all = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Blue,
                            contentColor = Color.White
                        ),
                        onClick = {
                            action.invoke()
                            shouldShowDialog.value = false
                            note_del.value = mutableListOf()
                        }
                    ) {
                        Text("Confirm")
                    }
                }
                    Row(
                        modifier = Modifier.padding(all = 8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.Blue,
                                contentColor = Color.White
                            ),
                            onClick = {
                                shouldShowDialog.value = false
                                note_del.value = mutableListOf()
                            }
                        ) {
                            Text("Decline")
                        }
                    }

                }

        )
    }
}


@Composable
fun FullList(
    tasks: List<Note>,
    dialog: MutableState<Boolean>,
    text: MutableState<String>,
    deleteText: MutableState<String>,
    navController: NavController,
    notesToDelete: MutableState<List<Note>>,
) {
    var updatedate = ""
    LazyColumn(
        contentPadding = PaddingValues(12.dp),
        modifier = Modifier.background(colors.primary)
    ) {
        val queriedNotes = if (text.value.isEmpty()){
            tasks
        } else {
            tasks.filter { it.title.contains(text.value) }
        }

        // Gelen noteları göstereceğiz

        itemsIndexed(queriedNotes) { index, note ->
            //Boş degilse date, göster
            // prevous header a gerek yok silebilirm.
            if (note.getDay() != updatedate) {
                Column(
                    modifier = Modifier
                        .padding(6.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = note.getDay(), color = Color.Black)
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                )
                updatedate =  note.getDay()
            }


            Noteblock(
                task=  note,
               dialog= dialog,
                deleteText = deleteText ,
                navController,
                notesToDelete = notesToDelete,
                noteBackGround = Purple80

            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
            )
        }
    }
}


fun List<Note>?.orEmpty(): List<Note> {
    return if (this != null && this.isNotEmpty()){
        this
    } else Empty()
}
fun Empty(): List<Note> {
    return listOf(Note(id = 0, title = "Please create one", note = "Please create a note.", dateUpdated = "", deadline =""))
}