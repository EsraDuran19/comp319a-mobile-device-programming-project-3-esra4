package com.dbtechprojects.photonotes.persistence
import androidx.lifecycle.LiveData
import androidx.room.*
import com.dbtechprojects.photonotes.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Query("SELECT * FROM Notes WHERE notes.id=:id")
    suspend fun getNoteById(id: Int) : Note?

    @Query("SELECT * FROM Notes ORDER BY Date DESC")
    fun getNotes() : LiveData<List<Note>>

    @Query("SELECT * FROM Notes ORDER BY title ASC")
    fun getNotesOrderedTitle(): Flow<List<Note>>
    @Delete
    fun deleteNote(note: Note) : Int

    @Update
    fun updateNote(note: Note) : Int

    @Insert
    fun insertNote(note: Note)

}