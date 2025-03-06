package br.com.bookscapecompose.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "SavedBook")
class SavedBook(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "savedBookId")
    val savedBookId: String,

    @ColumnInfo(name = "userEmail")
    val userEmail: String,

    @ColumnInfo(name = "bookTitle")
    val bookTitle: String,

    @ColumnInfo(name = "bookAuthor")
    val bookAuthor: String,

    @ColumnInfo(name = "bookDescription")
    val bookDescription: String,

    @ColumnInfo(name = "bookImage")
    val bookImage: String,

    @ColumnInfo(name = "bookApiId")
    val bookApiId: String

): Parcelable