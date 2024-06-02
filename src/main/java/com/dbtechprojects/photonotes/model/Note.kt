package com.dbtechprojects.photonotes.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.dbtechprojects.photonotes.Constants
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity(tableName = Constants.TABLE_NAME, indices = [Index(value = ["id"], unique = true)])
data class Note (
    @PrimaryKey(autoGenerate = true)    val id: Int? = null,
    @ColumnInfo(name = "Note")          val note: String,
    @ColumnInfo(name = "Title")         val title: String,
    @ColumnInfo(name = "Date")   val dateUpdated: String = getDateCreated(),
    @ColumnInfo(name = "Deadline")     val deadline:  String
)

fun getDateCreated(): String {
    return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
}

fun Note.getDay(): String{
    if (this.dateUpdated.isEmpty()) return ""
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    return LocalDateTime.parse(this.dateUpdated,formatter ).toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
}