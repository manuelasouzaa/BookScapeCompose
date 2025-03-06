package br.com.bookscapecompose.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "User")
data class User(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "userEmail")
    val userEmail: String,

    @ColumnInfo(name = "username")
    val username: String,

    @ColumnInfo(name = "password")
    val password: String

) : Parcelable