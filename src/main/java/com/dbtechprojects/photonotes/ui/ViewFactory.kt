package com.dbtechprojects.photonotes.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dbtechprojects.photonotes.persistence.NotesDao

class ViewFactory(
    private val db: NotesDao,
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return  TaskView(
            db = db,
        ) as T
    }

}