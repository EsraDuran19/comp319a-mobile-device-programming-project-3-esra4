package com.dbtechprojects.photonotes.ui

import androidx.lifecycle.*
import com.dbtechprojects.photonotes.model.Note
import com.dbtechprojects.photonotes.persistence.NotesDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskView(
    private val db: NotesDao,
) : ViewModel() {

    val notes: LiveData<List<Note>> = db.getNotes()


     fun delTask(note: Note) {
        viewModelScope.launch(Dispatchers.IO){
            db.deleteNote(note)
        }
    }

     fun editTask(note: Note) {
        viewModelScope.launch(Dispatchers.IO){
            db.updateNote(note)
        }
    }

     fun newTask(title: String, note: String, deadline : String) {
        viewModelScope.launch(Dispatchers.IO){
            db.insertNote(Note(title = title, note = note, deadline = deadline))
        }
    }

     suspend fun getTask(noteId : Int) : Note? {
        return db.getNoteById(noteId)
    }

}
