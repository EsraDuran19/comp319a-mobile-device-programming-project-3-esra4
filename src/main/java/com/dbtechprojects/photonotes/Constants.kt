package com.dbtechprojects.photonotes

import com.dbtechprojects.photonotes.model.Note

object Constants {
    // const val NAVIGATION_NOTES_LIST = "notesList"
    const val NAV_NEW = "newNote"
    const val NAV_DETAIL = "detail/{noteId}"
    const val NAV_EDIT = "edit/{noteId}"
    const val NAV_ID = "noteId"
    const val TABLE_NAME = "Notes"
    const val DATABASE_NAME = "NotesDatabase"

    fun noteDetailNavigation(noteId: Int) = "detail/$noteId"
    fun noteEditNavigation(noteId: Int) = "edit/$noteId"



}