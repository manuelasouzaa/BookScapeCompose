package br.com.bookscapecompose.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book(
    val id: String,
    val title: String,
    val authors: String?,
    val description: String?,
    val image: String?,
    val link: String
) : Parcelable